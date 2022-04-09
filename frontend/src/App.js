import './App.css';

import SignIn from './_component/login/SignIn';

import Dashboard from "./_component/dashboard/Dashboard";
import React, {useState} from "react";
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import {PrivateRoute} from "./_component/PrivateRoute";
import {RecoilRoot} from "recoil";


const App = () => {
    return (
        <div>
            <RecoilRoot>
                <BrowserRouter>
                    <Routes>
                        <Route exact path='/' element={<PrivateRoute/>}>
                            <Route exact path='/' element={<Dashboard/>}/>
                        </Route>
                        <Route exact path='/login' element={<SignIn/>}/>
                    </Routes>
                </BrowserRouter>
                <footer style={{position: "fixed", bottom: "0"}}/>
            </RecoilRoot>
        </div>
    );
}

export default App;
