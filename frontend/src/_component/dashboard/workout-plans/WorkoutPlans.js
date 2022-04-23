import React from "react";
import WorkoutPlanTable from "./WorkoutPlanTable";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import IconButton from "@mui/material/IconButton";
import {Toolbar, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";

const WorkoutPlans = () => {
    const { t, i18n } = useTranslation();

    return (
        <div>
            <Toolbar>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    {t('workout_plans')}
                </Typography>
                <IconButton color="success" aria-label="" component="span" size="large">
                    <AddCircleIcon fontSize="inherit" />
                </IconButton>
            </Toolbar>
            <WorkoutPlanTable></WorkoutPlanTable>
        </div>
    );
}

export default WorkoutPlans;
