import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Stack
} from "@mui/material";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async () => {

        try {

            const response = await api.post("/auth/login", {
                email,
                password
            });

            localStorage.setItem("token", response.data.token);

            navigate("/");

        } catch (error) {

            alert("Invalid Credentials");

        }

    };

    return (

        <Container maxWidth="sm">

            <Paper sx={{ mt: 10, p: 4 }}>

                <Typography
                    variant="h4"
                    gutterBottom
                    align="center"
                >
                    Login
                </Typography>

                <Stack spacing={3}>

                    <TextField
                        label="Email"
                        value={email}
                        onChange={(e) =>
                            setEmail(e.target.value)
                        }
                    />

                    <TextField
                        type="password"
                        label="Password"
                        value={password}
                        onChange={(e) =>
                            setPassword(e.target.value)
                        }
                    />

                    <Button
                        variant="contained"
                        onClick={login}
                    >
                        Login
                    </Button>

                </Stack>

            </Paper>

        </Container>

    );

}

export default Login;