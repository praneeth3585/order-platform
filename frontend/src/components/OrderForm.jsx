import { useState } from "react";
import {
    Paper,
    TextField,
    Button,
    Typography,
    Stack,
} from "@mui/material";
import api from "../api/api";

function OrderForm({ onOrderCreated }) {
    const [customerId, setCustomerId] = useState("");
    const [productName, setProductName] = useState("");
    const [amount, setAmount] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await api.post("/orders", {
                customerId: Number(customerId),
                productName,
                amount: Number(amount),
            });

            alert("Order Created Successfully!");

            setCustomerId("");
            setProductName("");
            setAmount("");

            if (onOrderCreated) {
                onOrderCreated();
            }
        } catch (error) {
            console.error(error);

            if (error.response?.data) {
                alert(JSON.stringify(error.response.data, null, 2));
            } else {
                alert("Something went wrong.");
            }
        }
    };

    return (
        <Paper sx={{ p: 3, mb: 3 }}>
            <Typography variant="h6" gutterBottom>
                Create Order
            </Typography>

            <form onSubmit={handleSubmit}>
                <Stack spacing={2}>
                    <TextField
                        label="Customer ID"
                        value={customerId}
                        onChange={(e) => setCustomerId(e.target.value)}
                        required
                    />

                    <TextField
                        label="Product Name"
                        value={productName}
                        onChange={(e) => setProductName(e.target.value)}
                        required
                    />

                    <TextField
                        label="Amount"
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />

                    <Button variant="contained" type="submit">
                        Create Order
                    </Button>
                </Stack>
            </form>
        </Paper>
    );
}

export default OrderForm;