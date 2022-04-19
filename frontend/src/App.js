import './App.css';

import SignIn from './_component/login/SignIn';

import Dashboard from "./_component/dashboard/Dashboard";
import React, {useState} from "react";
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import {PrivateRoute} from "./_component/PrivateRoute";
import {RecoilRoot} from "recoil";
import DashboardDefaultContent from "./_component/dashboard/DashboardDefaultContent";
import WorkoutPlans from "./_component/dashboard/workout-plans/WorkoutPlans";
import Home from "./_component/dashboard/home/Home";
import Settings from "./_component/dashboard/settings/Settings";


const App = () => {
    return (
        <div>
            <RecoilRoot>
                <BrowserRouter>
                    <Routes>
                        <Route element={<PrivateRoute/>}>
                            <Route element={<Dashboard/>}>
                                <Route path='/' element={<Home/>}/>
                                <Route path='workout-plans' element={<WorkoutPlans/>}/>
                                <Route path='settings' element={<Settings/>}/>
                            </Route>
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
