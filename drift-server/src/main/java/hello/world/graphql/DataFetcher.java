package hello.world.graphql;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

/**
 * Specifies that a particular method should be bound as a data fetcher within the schema resolver.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface DataFetcher {

    /**
     * The parent type that defines the particular property.  If not specified, it defaults to the
     * first argument of the data fetcher.
     *
     * @return  The parent type containing the property
     */
    Class<?> parent() default Object.class;

    /**
     * The property within the schema to bind the data fetcher to.  If not specified, it defaults to
     * the bean property name of the method.
     *
     * @return  The property name
     */
    String property() default "";
}
