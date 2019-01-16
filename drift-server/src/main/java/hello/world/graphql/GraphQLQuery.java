package hello.world.graphql;

import java.util.Map;

public class GraphQLQuery {
    private String query;
    private Map<String, Object> variables;

    public GraphQLQuery() {
        super();
    }

    public GraphQLQuery(String query) {
        this.query = query;
    }
    
    public String getQuery() { return this.query; }
    public Map<String, Object> getVariables() { return this.variables; }
}