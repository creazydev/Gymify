import './App.css';

import SignIn from './component/SignIn';
import {ApolloProvider} from "@apollo/client";
import {client} from "./graphql/ApolloClient";
import { render } from "react-dom";
import {LanguageSelection} from "./component/LanguageSelectionFooter";



const App = () => {
    return (
        <div>
            <ApolloProvider client={client}>

                <SignIn/>
                <footer style={{position:"fixed",bottom:"0"}}/>
            </ApolloProvider>
        </div>
    );
}

export default App;
