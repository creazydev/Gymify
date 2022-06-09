import {ApolloClient, createHttpLink, InMemoryCache} from "@apollo/client";
import {setContext} from "@apollo/client/link/context";
import {authAtom} from "../_state";
import {getRecoil} from "recoil-nexus";

const httpLink = createHttpLink({
    uri: 'http://localhost:8080/graphql',
});

const authLink = setContext((_, { headers }) => {
    const auth = getRecoil(authAtom);
    return {
        headers: {
            ...headers,
            authorization: auth ? `Bearer ${auth.authenticationToken}` : "",
        }
    }
});

export const client = new ApolloClient({
    link: authLink.concat(httpLink),
    cache: new InMemoryCache()
});
