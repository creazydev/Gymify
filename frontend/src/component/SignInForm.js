import {Trans, useTranslation} from "react-i18next";
import {gql, useMutation} from "@apollo/client";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Alert from "@mui/material/Alert";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import * as React from "react";

const LOGIN_MUTATION = gql`
    mutation LoginMutation($email: String!$password: String!) {
        login(email: $email, password: $password) {
            email
            authenticationToken
        }
    }
`;

export default function SignInForm({ onClose }) {
    const { t, i18n } = useTranslation();
    const [signIn, { data, loading, error, reset }] = useMutation(LOGIN_MUTATION);

    if (data) {
        return data.login.email;
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
                value={"test@mail.ruu"}
                autoComplete="email"
                autoFocus
            />
            <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label={t('password')}
                type="password"
                value={"sample"}
                id="password"
                autoComplete="current-password"
            />
            {error ? (<Alert severity="error">{error.message}</Alert>) : null}
            <Button
                type="submit"
                fullWidth
                disabled={loading}
                variant="contained"
                sx={{mt: 3, mb: 2}}
            >
                {t('sign_in')}
            </Button>
            <Grid container>
                <Grid item xs>
                    <Link href="#" variant="body2">
                        {t('forget_password')}?
                    </Link>
                </Grid>
                <Grid item>
                    <Link href="#" variant="body2" onClick={onClose}>
                        {t('dont_have_an_account')}? {t('sign_up')}
                    </Link>
                </Grid>
            </Grid>
        </Box>
    );
}
