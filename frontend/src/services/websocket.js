import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let client = null;

export const connectWebSocket = (onNotificationReceived) => {

    client = new Client({

        webSocketFactory: () =>
            new SockJS("http://localhost:8080/ws"),

        reconnectDelay: 5000,

        onConnect: () => {

            console.log("✅ Connected to WebSocket");

            client.subscribe("/topic/notifications", (message) => {

                const notification = JSON.parse(message.body);

                onNotificationReceived(notification);

            });

        },

        onStompError: (frame) => {
            console.error("STOMP Error:", frame);
        }

    });

    client.activate();
};

export const disconnectWebSocket = () => {

    if (client) {
        client.deactivate();
    }

};