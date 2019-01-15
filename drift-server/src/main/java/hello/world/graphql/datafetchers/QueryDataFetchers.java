package hello.world.graphql.datafetchers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import hello.world.graphql.DataFetcher;
import hello.world.graphql.DataFetchers;
import hello.world.model.Actor;

/**
 * Data Fetcher responsible for fetching the root queries.
 */
@Singleton
public class QueryDataFetchers implements DataFetchers {


    @DataFetcher(property = "actors")
    public List<Actor> getActors() {
    	
    	ArrayList<Actor> actors = new ArrayList<>();
    	actors.add(new Actor("id1", "fn1", "name1"));
    	actors.add(new Actor("id2", "fn2", "name2"));
    	
        return actors;
    }
}
