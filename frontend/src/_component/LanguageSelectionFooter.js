import "../translations/i18n";
import '../css/language-selection-footer.css';

import {FormControl, IconButton, InputLabel, MenuItem, Select} from "@mui/material";
import * as React from "react";
import {useTranslation} from "react-i18next";
import { MDBIcon } from 'mdb-react-ui-kit';

export const LanguageSelectionFooter = () => {
    const {t, i18n} = useTranslation();
    const [language, setLanguage] = React.useState('en');

    const handleChange = (event) => {
        const lang = event.target.value;
        setLanguage(lang);
        void i18n.changeLanguage(lang);
    }

    return (
        <footer className="footer">
            <FormControl sx={{ m: 1, minWidth: 80 }}>
                <InputLabel id="demo-simple-select-autowidth-label">{t('language')}</InputLabel>
                <Select
                    labelId="demo-simple-select-autowidth-label"
                    id="demo-simple-select-autowidth"
                    value={language}
                    onChange={handleChange}
                    autoWidth
                >
                    <MenuItem value={'en'}>
                        <MDBIcon className={'clickable'} flag='united-states'/>
                    </MenuItem>
                    <MenuItem value={'pl'}>
                        <MDBIcon className={'clickable'} flag='poland'/>
                    </MenuItem>
                </Select>
            </FormControl>
        </footer>
    );
}
