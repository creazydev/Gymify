import * as React from 'react';
import {styled, createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import MuiDrawer from '@mui/material/Drawer';
import Box from '@mui/material/Box';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Container from '@mui/material/Container';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import {
    MainNavBarItemList,
    SettingsAndToolsNavBarItemList
} from "./navbarItems";
import {Logout} from "@mui/icons-material";
import {useUserActions} from '../../_action';
import {Outlet} from "react-router";
import Button from "@mui/material/Button";

const drawerWidth = 240;

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({theme, open}) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, {shouldForwardProp: (prop) => prop !== 'open'})(
    ({theme, open}) => ({
        '& .MuiDrawer-paper': {
            position: 'relative',
            whiteSpace: 'nowrap',
            width: drawerWidth,
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
            boxSizing: 'border-box',
            ...(!open && {
                overflowX: 'hidden',
                transition: theme.transitions.create('width', {
                    easing: theme.transitions.easing.sharp,
                    duration: theme.transitions.duration.leavingScreen,
                }),
                width: theme.spacing(7),
                [theme.breakpoints.up('sm')]: {
                    width: theme.spacing(9),
                },
            }),
        },
    }),
);

const mdTheme = createTheme();

function DashboardContent() {
    const [open, setOpen] = React.useState(true);
    const toggleDrawer = () => {
        setOpen(!open);
    };

    return (
        <ThemeProvider theme={mdTheme}>
            <Box sx={{display: 'flex'}}>
                <CssBaseline/>
                <MainAppBar position="absolute" open={open}>
                    <Toolbar
                        sx={{
                            pr: '24px', // keep right padding when drawer closed
                        }}
                    >
                        <IconButton
                            edge="start"
                            color="inherit"
                            aria-label="open drawer"
                            onClick={toggleDrawer}
                            sx={{
                                marginRight: '36px',
                                ...(open && {display: 'none'}),
                            }}
                        >
                            <MenuIcon/>
                        </IconButton>
                        <GymifyTitleFirst component="h1" variant="h5">
                            Gymify
                        </GymifyTitleFirst>
                        <GymifyTitleSecond component="h1" variant="h5">
                            Dashboard
                        </GymifyTitleSecond>
                    </Toolbar>
                </MainAppBar>
                <MainDrawer variant="permanent" open={open}>
                    <MainToolbar>
                        <IconButton onClick={toggleDrawer}>
                            <MinDrawer/>
                        </IconButton>
                    </MainToolbar>
                    <NavList component="nav">
                        <MainNavBarItemList open={open}/>
                        <SettingsAndToolsNavBarItemList />
                    </NavList>
                    <LogoutBox>
                        <LogoutButton
                            variant="outlined"
                            color="error"
                            onClick={useUserActions().logout}
                            sx={{
                                ...(!open && {display: 'none'})
                            }}
                        >
                            LOGOUT
                        </LogoutButton>
                    </LogoutBox>
                    <IconButton onClick={useUserActions().logout} sx={{ ...(open && {display: 'none'}) }}>
                        <LogoutIcon/>
                    </IconButton>
                </MainDrawer>

                <MainBox component="main">
                    <Toolbar/>
                    <Container sx={{m: 0, mt: 2, p: 0}} maxWidth={false}>
                        <Outlet />
                    </Container>
                </MainBox>
            </Box>
        </ThemeProvider>
    );
}

const LogoutIcon = styled(Logout, {})`
    color: white;
    display: flex;
    justify-content: center;
    align-items: flex-end;
    margin-bottom: 30%;
`

const LogoutButton = styled(Button, {})`
    width: 80%;
`

const LogoutBox = styled(Box, {})`
    height: 100%;
    margin-bottom: 15%;
    display: flex;
    justify-content: center;
    align-items: flex-end;
`

const MinDrawer = styled(ChevronLeftIcon, {})`
    color: white;
`

const MainAppBar = styled(AppBar, {})`
    background-color: #424242;
    box-shadow: none;
`

const GymifyTitleFirst = styled(Typography, {})`
    flexGrow: 1;
`

const GymifyTitleSecond = styled(Typography, {})`
    flexGrow: 1;
    color: #C23030;
    margin-left: 5px;
`

const NavList = styled(List, {})`
    background-color: #424242;
`

const MainDrawer = styled(Drawer, {})`  
    .MuiPaper-root {
        background-color: #424242;
        border: none;
        box-shadow: none;
    }
`

const MainToolbar = styled(Toolbar, {})`
    background-color: #424242;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    px: 1;
`;

const MainBox = styled(Box, {})`
    background-color: #777777;
    flex-grow: 1;
    height: 100vh;
    overflow: auto;
`;

export default function Dashboard() {
    return <DashboardContent/>;
}
