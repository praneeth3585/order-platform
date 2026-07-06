import { useEffect, useState } from "react";
import api from "../api/api";

import {
    Grid,
    Card,
    CardContent,
    Typography,
} from "@mui/material";

function DashboardCards({ refresh }) {

    const [stats, setStats] = useState({
        totalOrders: 0,
        totalRevenue: 0,
        totalNotifications: 0,
        averageOrderValue: 0
    });

    useEffect(() => {

        const fetchStats = async () => {

            try {

                const response = await api.get("/dashboard/stats");

                setStats(response.data);

            } catch (error) {
                console.error("Error fetching dashboard stats:", error);
            }

        };

        fetchStats();

    }, [refresh]);

    return (

        <Grid container spacing={3} sx={{ mb: 4 }}>

            <Grid size={{ xs: 12, md: 3 }}>
                <Card>
                    <CardContent>
                        <Typography variant="h6">📦 Orders</Typography>
                        <Typography variant="h4">
                            {stats.totalOrders}
                        </Typography>
                    </CardContent>
                </Card>
            </Grid>

            <Grid size={{ xs: 12, md: 3 }}>
                <Card>
                    <CardContent>
                        <Typography variant="h6">💰 Revenue</Typography>
                        <Typography variant="h4">
                            ₹{stats.totalRevenue}
                        </Typography>
                    </CardContent>
                </Card>
            </Grid>

            <Grid size={{ xs: 12, md: 3 }}>
                <Card>
                    <CardContent>
                        <Typography variant="h6">🔔 Notifications</Typography>
                        <Typography variant="h4">
                            {stats.totalNotifications}
                        </Typography>
                    </CardContent>
                </Card>
            </Grid>

            <Grid size={{ xs: 12, md: 3 }}>
                <Card>
                    <CardContent>
                        <Typography variant="h6">📈 Avg Order</Typography>
                        <Typography variant="h4">
                            ₹{Number(stats.averageOrderValue).toFixed(2)}
                        </Typography>
                    </CardContent>
                </Card>
            </Grid>

        </Grid>

    );
}

export default DashboardCards;