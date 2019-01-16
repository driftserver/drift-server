import ApolloClient from 'apollo-client'
import { InMemoryCache } from 'apollo-cache-inmemory'
import { createHttpLink, HttpLink } from 'apollo-link-http';
import gql from "graphql-tag";

/*
const client = new ApolloClient({
    link: new createHttpLink({
        uri: 'http://localhost:8080/graphql',
        headers: {
         accept: '* / *',
         'content-type': 'application/json',
        },
    }),
    cache: new InMemoryCache()
});
*/
