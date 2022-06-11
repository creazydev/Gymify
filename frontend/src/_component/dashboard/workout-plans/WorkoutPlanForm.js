import React from "react";
import {useTranslation} from "react-i18next";
import {gql, useQuery} from "@apollo/client";
import {useParams} from "react-router";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";


const WorkoutPlanForm = () => {
    const { t, i18n } = useTranslation();
    const { id } = useParams();

    const GET_WORKOUT_NAME = gql`
      query GetWorkoutName($id: Int!) {
        getWorkoutPlanById(id: $id) {
            name
        }
      }
    `

    const {loading, error, data} = useQuery(GET_WORKOUT_NAME, {
        variables: {
            id: id
        }
    })

    return (
        <div>
            <Typography variant='h4'> {data?.getWorkoutPlanById.name} </Typography>
            <Typography variant='h4'> Workout id: {id} </Typography>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
            >
                <Grid item>

                </Grid>
            </Grid>
        </div>
    );
}

export default WorkoutPlanForm;
