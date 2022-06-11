import './index.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import 'mdb-react-ui-kit/dist/css/mdb.min.css';

import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {LanguageSelectionFooter} from "./_component/LanguageSelectionFooter";
import {client} from "./graphql/ApolloClient";
import {RecoilRoot} from "recoil";
import RecoilNexus from 'recoil-nexus'
import {ApolloProvider} from "@apollo/client";

ReactDOM.render(
    <React.StrictMode>
        <RecoilRoot>
            <RecoilNexus/>
            <ApolloProvider client={client}>
                <div className="content">
                    <App key="1"/>
                    <LanguageSelectionFooter key="2"/>
                </div>
            </ApolloProvider>
        </RecoilRoot>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
