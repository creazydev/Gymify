import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import CssBaseline from '@mui/material/CssBaseline';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {Trans, useTranslation, withTranslation} from "react-i18next";
import "../../translations/i18n";
import SignUpForm from './SignUpForm'
import SignInForm from "./SignInForm";
import {useState} from "react";

const theme = createTheme();

export default function SignIn() {
    const [displaySignInForm, setDisplaySignInForm] = useState(false);
    const { t, i18n } = useTranslation();

    const toggleDisplaySignInForm = () => {
        setDisplaySignInForm(!displaySignInForm);
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
                            {t(displaySignInForm ? "sign_in" : "sign_up")}
                        </Typography>
                        {displaySignInForm
                            ? <SignInForm onClose={toggleDisplaySignInForm}/>
                            : <SignUpForm onClose={toggleDisplaySignInForm}/>
                        }
                    </Box>
                </Grid>
            </Grid>
        </ThemeProvider>
    );
}
