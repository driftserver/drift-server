import React from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { Query } from 'react-apollo';

const GET_ACTORS = gql`
  {
    actors {
      id,
      firstName,
      lastName
    }
  }
`;

function Actors() {
    return (
        <Query query={GET_ACTORS}>
            {
                ({ data, loading }) => {
                    const { actors } = data
                    if (loading || !actors) {
                        return <div>Loading ...</div>
                    }
                    const item = actors.map((actor)=> {
                        return (<div key={actor.id}>actor: {actor.firstName} {actor.lastName}</div>)
                    })
                    return <div>{item}</div>
               }
            }
        </Query>
    )
}

export default Actors

