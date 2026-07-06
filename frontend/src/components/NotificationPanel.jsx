import { useEffect, useState } from "react";
import api from "../api/api";

import {
    Paper,
    Typography,
    List,
    ListItem,
    ListItemText,
    Divider,
} from "@mui/material";

function NotificationPanel({ refresh }) {

    const [notifications, setNotifications] = useState([]);

    const fetchNotifications = async () => {
        try {
            const response = await api.get("/notifications");
            setNotifications(response.data);
        } catch (error) {
            console.error("Error fetching notifications:", error);
        }
    };

    useEffect(() => {
        fetchNotifications();
    }, [refresh]);

    return (
        <Paper sx={{ mt: 3, p: 2 }}>
            <Typography variant="h6" gutterBottom>
                🔔 Notifications
            </Typography>

            <List>
                {notifications.map((notification) => (
                    <div key={notification.id}>
                        <ListItem>
                            <ListItemText
                                primary={notification.message}
                                secondary={new Date(
                                    notification.createdAt
                                ).toLocaleString()}
                            />
                        </ListItem>

                        <Divider />
                    </div>
                ))}
            </List>
        </Paper>
    );
}

export default NotificationPanel;