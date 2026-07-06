import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const logout = () => {

        localStorage.removeItem("token");

        navigate("/login");

    };

    return (

        <AppBar position="static">

            <Toolbar>

                <Typography
                    variant="h6"
                    sx={{ flexGrow: 1 }}
                >
                    Order Platform
                </Typography>

                <Button
                    color="inherit"
                    onClick={logout}
                >
                    Logout
                </Button>

            </Toolbar>

        </AppBar>

    );

}

export default Navbar;