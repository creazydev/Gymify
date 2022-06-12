import React from "react";
import {useTranslation} from "react-i18next";
import Grid from "@mui/material/Grid";
import WorkoutPlanEdit from "./WorkoutPlanEdit";


const WorkoutPlanForm = () => {
    const { t, i18n } = useTranslation();


    return (
        <div>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
            >
                <Grid item>
                    <WorkoutPlanEdit />
                </Grid>
            </Grid>
        </div>
    );
}

export default WorkoutPlanForm;
