import {useTranslation} from "react-i18next";
import {gql, useMutation} from "@apollo/client";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Alert from "@mui/material/Alert";
import Button from "@mui/material/Button";
import * as React from "react";
import { authAtom } from '../../_state';
import {useSetRecoilState} from "recoil";
import {Navigate} from "react-router-dom";
import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";

const LOGIN_MUTATION = gql`
    mutation LoginMutation($email: String!$password: String!) {
        login(email: $email, password: $password) {
            email
            authenticationToken
        }
    }
`;

export default function SignInForm({ onClose }) {
    const { t } = useTranslation();
    const [signIn, { data, loading, error }] = useMutation(LOGIN_MUTATION);
    const setAuth = useSetRecoilState(authAtom);

    if (data) {
        setAuth(data.login.authenticationToken);
        return <Navigate to="/" />;
    }

    return (
        <Box component="form"
             onSubmit={e => {
                 e.preventDefault();
                 const formData = new FormData(e.currentTarget)
                 signIn({
                     variables: {
                         email: formData.get("email"),
                         password: formData.get("password")
                     }
                 }).then()
             }}
             noValidate sx={{mt: 1}}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label={t('email_address')}
                    name="email"
                    defaultValue="test@mail.ru"
                    autoComplete="email"
                    autoFocus
                    variant="standard"
                    InputLabelProps={{ shrink: true }}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="password"
                    label={t('password')}
                    name="password"
                    type="password"
                    defaultValue="sample"
                    autoComplete="current-password"
                    autoFocus
                    variant="standard"
                    InputLabelProps={{ shrink: true }}
                />
            {error ? (<Alert severity="error">{error.message}</Alert>) : null}
            <Button
                type="submit"
                fullWidth
                disabled={loading}
                variant="contained"
                sx={{mt: 3, mb: 2, bgcolor:'#F50057', fontWeight: 700,}}
            >
                {t('SUBMIT')}
            </Button>

            <Grid container>
                <Grid item md={12} lg={12} xs={12}>
                        <Typography
                            component='h1'
                            variant="h6"
                            sx={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                marginBottom: 2,
                            }}>
                            <Link>
                                FORGET PASSWORD?
                            </Link>
                        </Typography>
                </Grid>
                <Grid item md={12} lg={12} xs={12}>
                    <Button
                        href=''
                        variant="outlined"
                        color="error"
                        onClick={onClose}
                        fullWidth
                    >
                        {t('dont_have_an_account')}? {t('sign_up')}
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}
