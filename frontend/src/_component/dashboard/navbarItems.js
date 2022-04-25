import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import PeopleIcon from '@mui/icons-material/People';
import BarChartIcon from '@mui/icons-material/BarChart';
import LayersIcon from '@mui/icons-material/Layers';
import AssignmentIcon from '@mui/icons-material/Assignment';
import {Link} from 'react-router-dom';
import {useTranslation} from "react-i18next";

export const MainNavBarItemList = () => {
    const { t, i18n } = useTranslation();
    return (
        <div>
            <React.Fragment>
                <Link to={"/"}>
                    <ListItemButton>
                        <ListItemIcon>
                            <DashboardIcon/>
                        </ListItemIcon>
                        <ListItemText primary={t('home')}/>
                    </ListItemButton>
                </Link>
                <Link to="/workout-plans">
                    <ListItemButton>
                        <ListItemIcon>
                            <ShoppingCartIcon/>
                        </ListItemIcon>
                        <ListItemText primary={t('workout_plans')}/>
                    </ListItemButton>
                </Link>
                <Link to="/workout-sessions">
                    <ListItemButton>
                        <ListItemIcon>
                            <ShoppingCartIcon/>
                        </ListItemIcon>
                        <ListItemText primary={t('workout_sessions')}/>
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
                <ListSubheader component="div" inset>
                    {t('settings_and_tools')}
                </ListSubheader>
                <Link to={"/settings"}>
                    <ListItemButton>
                        <ListItemIcon>
                            <AssignmentIcon/>
                        </ListItemIcon>
                        <ListItemText primary={t('settings')}/>
                    </ListItemButton>
                </Link>
            </React.Fragment>
        </div>
    );
}
