import React from "react";
import WorkoutSessionTable from "./WorkoutSessionTable";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import IconButton from "@mui/material/IconButton";
import {Toolbar, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const WorkoutSessions = () => {
    const { t, i18n } = useTranslation();

    return (
        <div>
            <Toolbar>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    {t('workout_sessions')}
                </Typography>
                <Link to={`/workout-sessions/add`}>
                    <IconButton color="success" aria-label="" component="span" size="large">
                        <AddCircleIcon fontSize="inherit" />
                    </IconButton>
                </Link>
            </Toolbar>
            <WorkoutSessionTable></WorkoutSessionTable>
        </div>
    );
}

export default WorkoutSessions;
