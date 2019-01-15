package hello.world.graphql;

import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Factory used to configure the GraphQL schema and wire up the data fetchers.
 */
@Factory
public class GraphQLFactory {

	// wire up all marked data fetchers
	@Inject
	private List<DataFetchers> dataFetchers;

	public GraphQLFactory() {
		super();
	}

	@Bean
	@Singleton
	public GraphQL graphQL() {

		// build the schema from the graphql.schema resource file
		SchemaParser schemaParser = new SchemaParser();
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		InputStream input = GraphQLFactory.class.getResourceAsStream("/graphql.schema");
		TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(input));

		// wire up every discovered data fetcher
		RuntimeWiring.Builder wiring = RuntimeWiring.newRuntimeWiring();
		dataFetchers.forEach(dataFetcher -> {
			for (Method method : dataFetcher.getClass().getMethods()) {

				// check if the method is a data fetcher
				DataFetcher annotation = method.getAnnotation(DataFetcher.class);
				if (annotation == null) {
					continue;
				}

				// lookup the parent type based on either the annotation or the first argument
				// if no parent, then assume root query
				String parentType = annotation.parent().getSimpleName();
				if (Object.class.getSimpleName().equals(parentType)) {

					if (method.getParameters().length == 0) {
						parentType = "Query";
					} else {
						parentType = method.getParameterTypes()[0].getSimpleName();
					}
				}

				// lookup the name of the property on the parent type or default to the name of
				// the method
				// as a bean property name
				String property = annotation.property();
				if (property == null || property.isEmpty()) {
					if (method.getName().startsWith("get")) {
						property = Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4);
					} else {
						property = method.getName();
					}
				}

				// wire up the actual data fetcher to call the data fetcher method
				String _property = property;
				wiring.type(parentType, typeWiring -> {
					typeWiring.dataFetcher(_property, (env) -> {

						// map the param types to either the parent type or the environment
						Class<?>[] paramTypes = method.getParameterTypes();
						Object[] args = new Object[paramTypes.length];

						for (int i = 0; i < paramTypes.length; i++) {
							if (DataFetchingEnvironment.class.isAssignableFrom(paramTypes[i])) {
								args[i] = env;
							} else {
								args[i] = env.getSource();
							}
						}

						// convert the reactive types to a completable future
						try {
							return convert(method.invoke(dataFetcher, args));
						} catch (Exception exception) {
							throw new RuntimeException(exception);
						}
					});

					return typeWiring;
				});
			}
		});

		// return the resulting schema
		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring.build());
		return GraphQL.newGraphQL(graphQLSchema).build();
	}

	public static Object convert(Object value) {
		if (value instanceof Future) {
			return value;
		} else if (value instanceof Single) {
			return fromSingle((Single) value);
		} else {
			return value;
		}
	}

	public static <T> CompletableFuture<T> fromSingle(Single<T> single) {
		return new RxJavaCompletableFuture<>(single);
	}

	public static class RxJavaCompletableFuture<T> extends CompletableFuture<T> {
		private final Disposable subscription;

		public RxJavaCompletableFuture(Single<T> single) {
			subscription = single.subscribe(this::complete, this::completeExceptionally);
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			subscription.dispose();
			return super.cancel(mayInterruptIfRunning);
		}
	}
}