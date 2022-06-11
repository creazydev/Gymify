import React from "react";
import {useTranslation} from "react-i18next";
import WorkoutAddForm from "./WorkoutAddForm";
import Grid from "@mui/material/Grid";
import WorkoutPlanTable from "../workout-plans/WorkoutPlanTable";

const WorkoutAdd = () => {
    const { t, i18n } = useTranslation();

    return (
        <div>
            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
            >
                <Grid item sx={3}>
                    <WorkoutAddForm />
                </Grid>
            </Grid>
        </div>
    )
};

export default WorkoutAdd;