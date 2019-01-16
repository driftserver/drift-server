import React, { Component } from 'react';
import { ApolloProvider } from 'react-apollo';
import Actors from './components/Actor';

import ApolloClient from 'apollo-client'
import { InMemoryCache } from 'apollo-cache-inmemory'
import { createHttpLink, HttpLink } from 'apollo-link-http';
import gql from "graphql-tag";

const client = new ApolloClient({
    link: new createHttpLink({
        uri: 'http://localhost:8080/graphql',
        headers: {
         accept: '*/*',
         'content-type': 'application/json',
        },
    }),
    cache: new InMemoryCache()
});


class App extends Component {
    render() {
        return (
            <div>
                <ApolloProvider client={client}>
                    <Actors />
                </ApolloProvider>
            </div>
        );
    }
}

export default App;