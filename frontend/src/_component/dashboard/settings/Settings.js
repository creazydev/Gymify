import React from "react";
import {useTranslation} from "react-i18next";

const Settings = () => {
    const { t, i18n } = useTranslation();

    return (
        <div>
            <h1>Settings!</h1>
        </div>
    );
}

export default Settings;