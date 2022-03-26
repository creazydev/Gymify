import './App.css';

import SignIn from './component/SignIn';
import {ApolloProvider} from "@apollo/client";
import {client} from "./graphql/ApolloClient";

const App = () => {
    return (
        <div>
            <ApolloProvider client={client}>
                <SignIn/>
            </ApolloProvider>
        </div>
    );
}

export default App;
