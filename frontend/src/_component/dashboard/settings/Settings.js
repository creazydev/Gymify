import React from "react";
import {Tab, Tabs, Typography} from "@mui/material";
import Box from "@mui/material/Box";
import * as PropTypes from "prop-types";
import {useTranslation} from "react-i18next";
import {WorkoutConfigurationForm} from "./WorkoutConfigurationForm";
import {NotificationConfigurationForm} from "./NotificationConfigurationForm";
import {ProfileForm} from "./ProfileForm";
import {SecurityForm} from "./SecurityForm";

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function Settings() {
    const { t, i18n } = useTranslation();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab label={t('profile')} {...a11yProps(0)} />
                    <Tab label={t('workout')} {...a11yProps(1)} />
                    <Tab label={t('notification')} {...a11yProps(2)} />
                    <Tab label={t('security')} {...a11yProps(3)} />
                </Tabs>
            </Box>
            <TabPanel value={value} index={0}>
                <ProfileForm />
            </TabPanel>
            <TabPanel value={value} index={1}>
                <WorkoutConfigurationForm />
            </TabPanel>
            <TabPanel value={value} index={2}>
                <NotificationConfigurationForm />
            </TabPanel>
            <TabPanel value={value} index={3}>
                <SecurityForm />
            </TabPanel>
        </Box>
    );
}
