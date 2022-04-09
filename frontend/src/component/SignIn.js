import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Alert from '@mui/material/Alert';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {gql, useMutation} from "@apollo/client";
import { useTranslation } from "react-i18next";
import "../translations/i18n";
import {IconButton} from "@mui/material";
import SignUpForm from './SignUpForm'
import SignInForm from "./SignInForm";
import {useState} from "react";

const theme = createTheme();

const LOGIN_MUTATION = gql`
    mutation LoginMutation($email: String!$password: String!) {
        login(email: $email, password: $password) {
            email
            authenticationToken
        }
    }
`;

export default class SignIn extends Component {
    const [state, setState] = useState({
        displaySignInForm: false
    });

    const { t, i18n } = useTranslation();
    const [signIn, { data, loading, error, reset }] = useMutation(LOGIN_MUTATION);
    const onSave = () => {
        const newState = setState((prevState) => {
            console.log(prevState);
            return {displaySignInForm: true};
        });

        console.log(newState);
    };
    if (data) {
        return data.login.email;
    }

    if (error) {
        // return `Submission error! ${error.message}`;
    }

    return (

        <ThemeProvider theme={theme}>
            <Grid container component="main" sx={{height: '100vh'}}>
                <CssBaseline/>
                <Grid
                    item
                    xs={false}
                    sm={4}
                    md={7}
                    sx={{
                        backgroundImage: 'url(https://source.unsplash.com/random)',
                        backgroundRepeat: 'no-repeat',
                        backgroundColor: (t) =>
                            t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}
                />
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                    <Box
                        sx={{
                            my: 8,
                            mx: 4,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                            <LockOutlinedIcon/>
                        </Avatar>
                        <Typography component="h1" variant="h5">
                            {state.displaySignInForm}
                            {state.displaySignInForm ? t("sign_in") : t("sign_up")}
                        </Typography>
                        {state.displaySignInForm
                            ? <SignInForm onClose={setState({displaySignInForm: false})}/>
                            : <SignUpForm onClose={onSave}/>
                        }
                    </Box>
                </Grid>
            </Grid>
        </ThemeProvider>
    );
}
