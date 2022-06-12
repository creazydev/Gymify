import React from "react";
import {useTranslation} from "react-i18next";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import {gql, useMutation} from "@apollo/client";
import Alert from "@mui/material/Alert";
import {Navigate} from "react-router-dom";

const ADD_WORKOUT_MUTATION = gql`
    mutation AddWorkoutPlan($workoutName: String!) {
        addWorkoutPlan(workoutPlan: {
            name: $workoutName
        })
         {
            name
            id
        }
    }
`;

const WorkoutAddForm = () => {
    const { t, i18n } = useTranslation();

    const [addWorkout, { error, data }] = useMutation(ADD_WORKOUT_MUTATION);

    if (data) {
        return <Navigate to="/workout-plans" />;
    }

    return (
        <FormBox component='form'
                 onSubmit={e => {
                     e.preventDefault();
                     const formData = new FormData(e.currentTarget)
                     console.log(formData)
                     addWorkout({
                         variables: {
                             workoutName: formData.get("workoutName"),
                         }
                     }).then()
                 }}
        >
            <Typography variant='h5'>Add workout</Typography>
            <TextField
                required
                fullWidth
                id='workoutName'
                name='workoutName'
                label='Workout name'
                variant='standard'
            />
            {error ? (<Alert severity="error">{error.message}</Alert>) : null}
            <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{mt: 3, mb: 2, bgcolor:'#F50057', fontWeight: 700,}}
            >
                SUBMIT
            </Button>
        </FormBox>
    )
};

export default WorkoutAddForm;

const FormBox = styled(Box, {})`
    width: 800px;
    border: 2px solid black;
    padding: 20px;
`
