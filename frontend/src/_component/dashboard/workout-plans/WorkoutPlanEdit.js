import React from "react";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import {useTranslation} from "react-i18next";
import {useParams} from "react-router";
import {gql, useMutation, useQuery} from "@apollo/client";
import Button from "@mui/material/Button";



const WorkoutPlanEdit = (props) => {
    const { t, i18n } = useTranslation();

    const { id } = useParams();


    const GET_WORKOUT_NAME = gql`
      query GetWorkoutName($id: Int!) {
        getWorkoutPlanById(id: $id) {
            name
        }
      }
    `

    const UPDATE_WORKOUT = gql`
        mutation UpdateWorkoutName($name: String!) {
            addWorkoutPlan(workoutPlan: {
                name: $name
                workoutSession: {
                    dayOfWeek: MONDAY
                    startTime: "15:00:00"
                    duration: {
                        seconds: 60
                    }
                    exercises: {
                        name: "test"
                        plannedRestDuration: 30
                        sets: {
                            repsQuantity: "10"
                            plannedRestDuration: 30
                        }
                    }
                }
            }) {
                name
            }
        }
    `

    const {loading, error, data} = useQuery(GET_WORKOUT_NAME, {
        variables: {
            id: id
        }
    })

    const [updateName, {updateData}] = useMutation(UPDATE_WORKOUT);

    console.log(data)

    return (
        <div>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
            >
                <Grid item>
                    <Typography variant='h5'>Workout Name: {data?.getWorkoutPlanById.name} </Typography>
                    <Box component='form'
                         onSubmit={e => {
                             e.preventDefault();
                             const formData = new FormData(e.currentTarget)
                             updateName({
                                 variables: {
                                     id: id,
                                     name: formData.get("workoutName"),
                                 }
                             }).then()
                         }}
                    >
                        <TextField
                            required
                            fullWidth
                            autoFocus
                            id='workoutName'
                            name='workoutName'
                            label='Workout name'
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2, bgcolor:'#F50057', fontWeight: 700,}}
                        >
                            SUBMIT
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </div>
    );
}

export default WorkoutPlanEdit;
