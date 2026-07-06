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

function Register() {

    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const register = async () => {

        try {

            await api.post("/auth/register", {
                name,
                email,
                password
            });

            navigate("/login");

        } catch (error) {

            alert("Registration Failed");

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
                    Register
                </Typography>

                <Stack spacing={3}>

                    <TextField
                        label="Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />

                    <TextField
                        label="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <Button
                        variant="contained"
                        onClick={register}
                    >
                        Register
                    </Button>

                </Stack>

            </Paper>

        </Container>

    );

}

export default Register;