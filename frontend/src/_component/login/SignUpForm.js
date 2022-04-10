import {useTranslation} from "react-i18next";
import {gql, useMutation} from "@apollo/client";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Alert from "@mui/material/Alert";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import * as React from "react";

const REGISTER_MUTATION = gql`
    mutation RegisterMutation($email: String!$password: String!) {
        register(email: $email, password: $password) {
            email
            authenticationToken
        }
    }
`;

export default function SignUpForm({ onClose }) {
    const { t, i18n } = useTranslation();
    const [signUp, { data, loading, error, reset }] = useMutation(REGISTER_MUTATION);

    if (data) {
        return data.login.email;
    }

    return (
        <Box component="form"
             onSubmit={e => {
                 e.preventDefault();
                 const formData = new FormData(e.currentTarget)
                 signUp({
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
                {t('sign_up')}
            </Button>
            <Grid container>
                <Grid item xs>
                    <Link href="#" variant="body2" onClick={onClose}>
                        {t('already_have_account')}? {t('sign_in')}
                    </Link>
                </Grid>
            </Grid>
        </Box>
    );
}
