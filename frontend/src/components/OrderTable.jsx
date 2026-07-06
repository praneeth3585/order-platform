import { useEffect, useState } from "react";
import api from "../api/api";

import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
    TextField,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Stack,
    Button,
    Pagination,
    Box,
} from "@mui/material";

function OrderTable({ refresh }) {

    const [orders, setOrders] = useState([]);
    const [search, setSearch] = useState("");
    const [status, setStatus] = useState("ALL");

    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    const fetchOrders = async () => {

        try {

            let response;

            // Search
            if (search.trim() !== "") {

                response = await api.get(
                    `/orders/search/product?product=${search}`
                );

                setOrders(response.data);
                setTotalPages(1);

            }

            // Filter
            else if (status !== "ALL") {

                response = await api.get(
                    `/orders/filter/status?status=${status}`
                );

                setOrders(response.data);
                setTotalPages(1);

            }

            // Pagination
            else {

                response = await api.get(
                    `/orders?page=${page - 1}&size=10`
                );

                setOrders(response.data.content);
                setTotalPages(response.data.totalPages);

            }

        } catch (error) {

            console.error(error);

        }

    };

    const updateStatus = async (id, newStatus) => {

        try {

            await api.put(
                `/orders/${id}/status?status=${newStatus}`
            );

            fetchOrders();

        } catch (error) {

            console.error(error);

        }

    };

    useEffect(() => {

        fetchOrders();

    }, [refresh, search, status, page]);

    return (

        <Paper sx={{ mt: 3, p: 3 }}>

            <Typography variant="h6" gutterBottom>
                Orders
            </Typography>

            <Stack
                direction="row"
                spacing={2}
                sx={{ mb: 3 }}
            >

                <TextField
                    label="Search Product"
                    value={search}
                    onChange={(e) => {
                        setSearch(e.target.value);
                        setPage(1);
                    }}
                    fullWidth
                />

                <FormControl sx={{ minWidth: 200 }}>

                    <InputLabel>Status</InputLabel>

                    <Select
                        value={status}
                        label="Status"
                        onChange={(e) => {
                            setStatus(e.target.value);
                            setPage(1);
                        }}
                    >

                        <MenuItem value="ALL">ALL</MenuItem>

                        <MenuItem value="CREATED">CREATED</MenuItem>

                        <MenuItem value="PROCESSING">PROCESSING</MenuItem>

                        <MenuItem value="SHIPPED">SHIPPED</MenuItem>

                        <MenuItem value="DELIVERED">DELIVERED</MenuItem>

                    </Select>

                </FormControl>

            </Stack>

            <TableContainer>

                <Table>

                    <TableHead>

                        <TableRow>

                            <TableCell>ID</TableCell>
                            <TableCell>Customer</TableCell>
                            <TableCell>Product</TableCell>
                            <TableCell>Amount</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center">Action</TableCell>

                        </TableRow>

                    </TableHead>

                    <TableBody>

                        {orders.map((order) => (

                            <TableRow key={order.id}>

                                <TableCell>{order.id}</TableCell>

                                <TableCell>{order.customerId}</TableCell>

                                <TableCell>{order.productName}</TableCell>

                                <TableCell>₹ {order.amount}</TableCell>

                                <TableCell>{order.status}</TableCell>

                                <TableCell align="center">

                                    {order.status === "CREATED" && (

                                        <Button
                                            variant="contained"
                                            size="small"
                                            onClick={() =>
                                                updateStatus(
                                                    order.id,
                                                    "PROCESSING"
                                                )
                                            }
                                        >
                                            Process
                                        </Button>

                                    )}

                                    {order.status === "PROCESSING" && (

                                        <Button
                                            variant="contained"
                                            color="warning"
                                            size="small"
                                            onClick={() =>
                                                updateStatus(
                                                    order.id,
                                                    "SHIPPED"
                                                )
                                            }
                                        >
                                            Ship
                                        </Button>

                                    )}

                                    {order.status === "SHIPPED" && (

                                        <Button
                                            variant="contained"
                                            color="success"
                                            size="small"
                                            onClick={() =>
                                                updateStatus(
                                                    order.id,
                                                    "DELIVERED"
                                                )
                                            }
                                        >
                                            Deliver
                                        </Button>

                                    )}

                                    {order.status === "DELIVERED" && (

                                        <Typography color="success.main">
                                            ✅ Completed
                                        </Typography>

                                    )}

                                </TableCell>

                            </TableRow>

                        ))}

                    </TableBody>

                </Table>

            </TableContainer>

            {search === "" && status === "ALL" && (

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        mt: 3,
                    }}
                >

                    <Pagination
                        count={totalPages}
                        page={page}
                        onChange={(event, value) => setPage(value)}
                        color="primary"
                    />

                </Box>

            )}

        </Paper>

    );

}

export default OrderTable;