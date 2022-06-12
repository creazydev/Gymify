import React from "react";
import {useTranslation} from "react-i18next";
import {gql, useLazyQuery, useMutation, useQuery} from "@apollo/client";
import {useParams} from "react-router";
import { useNavigate } from 'react-router-dom';
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Alert from "@mui/material/Alert";
import Button from "@mui/material/Button";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";


const WorkoutPlanDeleteForm = () => {
    const { t, i18n } = useTranslation();
    const { id } = useParams();

    const REMOVE_WORKOUT = gql`
    mutation REMOVE_WORKOUT($id: Int!) {
        deleteWorkoutPlan(id: $id)
    }
`
    const [removeWorkout, {loading, error, data}] = useMutation(REMOVE_WORKOUT, {
        variables: {
            id: id
        }
    })

    const navigate = useNavigate();

    const redirect = () => {
        navigate('/workout-plans');
    }

    return (
        <div>
            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
            >
                <Grid item sx={3}>
                    <FormBox component='form'
                             onSubmit={e => {
                                 e.preventDefault();
                                 const formData = new FormData(e.currentTarget)
                                 removeWorkout().then()
                                 redirect()
                             }}
                    >
                        <Typography variant='h5'>You want to delete workout?</Typography>
                        {error ? (<Alert severity="error">{error.message}</Alert>) : null}
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2, bgcolor:'#F50057', fontWeight: 700,}}
                        >
                            YES
                        </Button>
                    </FormBox>
                    <FormBox sx={{ mt: 3}}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2, bgcolor:'#F50057', fontWeight: 700,}}
                            onClick={redirect}
                        >
                            NO
                        </Button>
                    </FormBox>
                </Grid>
            </Grid>
        </div>
    );
}

export default WorkoutPlanDeleteForm;

const FormBox = styled(Box, {})`
    width: 800px;
    border: 2px solid black;
    padding: 20px;
`
