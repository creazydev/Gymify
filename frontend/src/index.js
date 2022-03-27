import './index.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import 'mdb-react-ui-kit/dist/css/mdb.min.css';

import React from 'react';
import ReactDOM, {render} from 'react-dom';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {LanguageSelectionFooter} from "./component/LanguageSelectionFooter";

ReactDOM.render(
    <React.StrictMode>
        <div className="content">
            <App key="1"/>
            <LanguageSelectionFooter key="2"/>
        </div>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
