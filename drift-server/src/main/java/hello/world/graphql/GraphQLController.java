package hello.world.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.ExecutionResult;
import graphql.GraphQL;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Options;
import io.micronaut.http.annotation.Post;

@Controller("/graphql")
public class GraphQLController {

    private static final Logger LOG = LoggerFactory.getLogger(GraphQLController.class);

    private GraphQL graphQL;

    public GraphQLController(GraphQL graphQL) {

        this.graphQL = graphQL;
    }
    

    @Get(value="/")
    public String test() {
    	return "hello";
    }

    @Options(value="/")
    public HttpResponse<?> options() {
    	return HttpResponse.ok().header("Access-Control-Allow-Origin", "*");
    }
    
    @Consumes({MediaType.TEXT_PLAIN})
    @Post(value="/")
    public Map<String, Object> graphqlPlainTextPost(@Size(max=20000) @Body String query) {
    	return graphqlPost(new GraphQLQuery(query));
    }	
    
    @Consumes({MediaType.APPLICATION_JSON})
    @Post(value="/")
    public Map<String, Object> graphqlPost(@Size(max=20000) @Body GraphQLQuery query) {

        // execute the query
        ExecutionResult executionResult = graphQL.execute(query.getQuery());

        // build the resulting response
        Map<String, Object> result = new HashMap<>();
        result.put("data", executionResult.getData());

        // append any errors that may have occurred
        List<?> errors = executionResult.getErrors();
        if (errors != null && !errors.isEmpty()) {
            result.put("errors", errors);
        }

        // add any extension data
        result.put("exentions", executionResult.getExtensions());

        // return the resulting data
        return result;
    }
}