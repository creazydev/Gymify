import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useTranslation} from "react-i18next";
import "../../translations/i18n";
import SignUpForm from './SignUpForm'
import SignInForm from "./SignInForm";
import {useState} from "react";
import Image from '../../images/gymify-background.jpg';
import {makeStyles} from "@mui/styles";
import {Hidden} from "@mui/material";


const theme = createTheme();
const useStyles = makeStyles({
    dark: {
        backgroundColor: '#424242',
        color: "#ffffff"
    },

    box: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },

    typography: {
        display: 'flex',
        color: 'white',
        fontWeight: '400',
    },

    leftGrid: {
        backgroundImage: `url(${Image})`,
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    }

});

export default function SignIn() {
    const classes = useStyles();
    const [displaySignInForm, setDisplaySignInForm] = useState(true);
    const { t } = useTranslation();

    const toggleDisplaySignInForm = () => {
        setDisplaySignInForm(!displaySignInForm);
    }

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
                <Grid container component="main" sx={{height: '100vh'}}>
                    <Hidden mdDown>
                        <Grid item xs={false} sm={4} md={7} className={classes.leftGrid}>
                            <Typography component='h1' variant="h1" sx={{my: 6, mx: 12,}} className={classes.typography}>
                                Gymify
                            </Typography>
                            <Typography component='h1' variant="h3" sx={{my: 4, mx: 12,}} className={classes.typography}>
                                Personal workout planner
                            </Typography>
                        </Grid>
                    </Hidden>
                    <Grid item xs={12} sm={12} md={5} className={classes.dark}>
                        <Box sx={{ my:8, mx:4,}} className={classes.box}>
                            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                                <LockOutlinedIcon/>
                            </Avatar>
                            <Typography component="h1" variant="h4">
                                {t(displaySignInForm ? "sign_in" : "sign_up")}
                            </Typography>
                            <Typography component="h1" variant="h6">
                                Welcome to Gymify App
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