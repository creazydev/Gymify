import './App.css';

import SignIn from './_component/login/SignIn';

import Dashboard from "./_component/dashboard/Dashboard";
import React from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {PrivateRoute} from "./_component/PrivateRoute";
import WorkoutPlans from "./_component/dashboard/workout-plans/WorkoutPlans";
import Home from "./_component/dashboard/home/Home";
import Settings from "./_component/dashboard/settings/Settings";
import WorkoutPlanForm from "./_component/dashboard/workout-plans/WorkoutPlanForm";
import ProfileForm from "./_component/dashboard/settings/ProfileForm";
import WorkoutSessions from "./_component/dashboard/workout-sessions/WorkoutSessions";
import WorkoutSessionForm from "./_component/dashboard/workout-sessions/WorkoutSessionForm";
import WorkoutAdd from "./_component/dashboard/workout-add/WorkoutAdd";
import LiveWorkout from "./_component/dashboard/live-workout/LiveWorkout";
import WorkoutPlanDeleteForm from "./_component/dashboard/workout-plans/WorkoutPlanDeleteForm";


const App = () => {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route element={<PrivateRoute/>}>
                        <Route element={<Dashboard/>}>
                            <Route path='/' element={<Home/>}/>
                            <Route path='/workout-add' element={<WorkoutAdd/>}/>
                            <Route path='/live-workout' element={<LiveWorkout/>}/>
                            <Route path='workout-plans' element={<WorkoutPlans/>}/>
                            <Route path='workout-plans/:id' element={<WorkoutPlanForm/>}/>
                            <Route path='workout-plans-delete/:id' element={<WorkoutPlanDeleteForm/>}/>
                            <Route path='workout-sessions' element={<WorkoutSessions/>}/>
                            <Route path='workout-sessions/:id' element={<WorkoutSessionForm/>}/>
                            <Route path='settings' element={<Settings/>}/>
                        </Route>
                    </Route>
                    <Route exact path='/login' element={<SignIn/>}/>
                </Routes>
            </BrowserRouter>
            <footer style={{position: "fixed", bottom: "0"}}/>
        </div>
    );
}

export default App;
