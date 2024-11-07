import { useContext } from "react";
import Inventario from "../components/Inventario";
import Layout from "../components/Layout";
import { UserContext } from "../contexts/UserContext";
import { Navigate } from "react-router-dom";

const InventarioPage = () => {
    const { getUser } = useContext(UserContext);
    const user = getUser();

    if (!user || !user.rol || !user.rol.permisos || !user.rol.permisos.find((p) => p.permiso.clave === "INV01")) {
        return <Navigate to="/Inicio" />;
    }

    return (
         <Layout user={user}>
            <Inventario user={user}/>
        </Layout>     
    );
};


export default InventarioPage;