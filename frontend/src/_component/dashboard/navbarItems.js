import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import AssignmentIcon from '@mui/icons-material/Assignment';
import {Link} from 'react-router-dom';
import {useTranslation} from "react-i18next";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';
import Assessment from '@mui/icons-material/Assessment';
import {styled} from "@mui/material/styles";
import Avatar from "@mui/material/Avatar";
import {Divider, ListSubheader} from "@mui/material";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import BuildCircleOutlinedIcon from '@mui/icons-material/BuildCircleOutlined';

import {authAtom} from '../../_state';
import {useRecoilValue} from "recoil";

export const MainNavBarItemList = (props) => {
    const { t, i18n } = useTranslation();
    const auth = useRecoilValue(authAtom);

    return (
        <div>
            <AvatarBox>
                <AvatarPlaceholder sx={{...(!props.open && {display: 'none'})}}>M</AvatarPlaceholder>
                <Username id={'userEmail'} sx={{...(!props.open && {display: 'none'})}}>{auth.email}</Username>
            </AvatarBox>
            <React.Fragment>
                <Divider variant='middle'/>
                <Link to={"/workout-add"}>
                    <ListItemButton>
                        <ListItemIcon>
                            <AddWorkoutIcon color='error'/>
                        </ListItemIcon>
                        <AddWorkoutButton primary={t('Add workout')}/>
                    </ListItemButton>
                </Link>
                <Link to={"/live-workout"}>
                    <ListItemButton>
                        <ListItemIcon>
                            <LiveWorkoutIcon color='error'/>
                        </ListItemIcon>
                        <LiveWorkoutButton primary={t('Live workout')} color='white'/>
                    </ListItemButton>
                </Link>

                <Link to="/workout-plans">
                    <ListItemButton>
                        <ListItemIcon>
                            <WorkoutPlansIcon color='success'/>
                        </ListItemIcon>
                        <WorkoutPlansButton primary={t('Workout plans')}/>
                    </ListItemButton>
                </Link>
                <Link to="/workout-sessions">
                    <ListItemButton>
                        <ListItemIcon>
                            <WorkoutSessionsIcon color='success'/>
                        </ListItemIcon>
                        <WorkoutSessionsButton primary={t('Workout sessions')}/>
                    </ListItemButton>
                </Link>
            </React.Fragment>
        </div>
    );
}

export const SettingsAndToolsNavBarItemList = () => {
    const { t, i18n } = useTranslation();
    return (
        <div>
            <React.Fragment>
                <Link to={"/settings"}>
                    <ListItemButton>
                        <ListItemIcon>
                            <SettingsButton/>
                        </ListItemIcon>
                        <SettingsText primary={t('settings')}/>
                    </ListItemButton>
                </Link>
            </React.Fragment>
        </div>
    );
}

const SettingsText = styled(ListItemText, {})`
    color: white;
`

const SettingsButton = styled(BuildCircleOutlinedIcon, {})`
    font-size: 30px;
`

const AvatarBox = styled(Box, {})`
    display: flex;
    flex-direction: column;
    align-items: center;
`

const Username = styled(Typography, {})`
    color: white;
    font-size: 0.9rem;
    margin-top: 5%;
    margin-bottom: 5%;
`

const AvatarPlaceholder = styled(Avatar, {})`
   width: 80px;
   height: 80px;
`

const AddWorkoutIcon = styled(AddCircleIcon, {})`
    font-size: 30px;
`

const AddWorkoutButton = styled(ListItemText, {})`
    color: white;
`

const LiveWorkoutIcon = styled(PlayCircleIcon, {})`
    font-size: 30px;
`

const LiveWorkoutButton = styled(ListItemText, {})`
    color: white;
`

const WorkoutPlansIcon = styled(AssignmentIcon, {})`
    font-size: 30px;
`

const WorkoutPlansButton = styled(ListItemText, {})`
    color: white;
`

const WorkoutSessionsIcon = styled(Assessment, {})`
    font-size: 30px;
`

const WorkoutSessionsButton = styled(ListItemText, {})`
    color: white;
`
