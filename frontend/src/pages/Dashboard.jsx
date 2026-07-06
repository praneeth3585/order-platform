import { useState } from "react";

import Navbar from "../components/Navbar";
import DashboardCards from "../components/DashboardCards";
import OrderForm from "../components/OrderForm";
import OrderTable from "../components/OrderTable";
import NotificationPanel from "../components/NotificationPanel";

function Dashboard() {

    const [refresh, setRefresh] = useState(false);

    const refreshData = () => {
        setRefresh(!refresh);
    };

    return (
        <>
            <Navbar />

            <div style={{ padding: "30px" }}>

                <DashboardCards refresh={refresh} />

                <OrderForm onOrderCreated={refreshData} />

                <OrderTable refresh={refresh} />

                <NotificationPanel refresh={refresh} />

            </div>
        </>
    );
}

export default Dashboard;