import { useContext } from "react";
import Layout from "../components/Layout";
import { UserContext } from "../contexts/UserContext";
import Salidas from "../components/Salidas";
import { Navigate } from "react-router-dom";

const SalidasPage = () => {
    const { getUser } = useContext(UserContext);
    const user = getUser();

    if (!user || !user.rol || !user.rol.permisos || !user.rol.permisos.find((p) => p.permiso.clave === "SAL01")) {
        return <Navigate to="/Inicio" />;
    }

    return (
         <Layout user={user}>
            <Salidas user={user}/>
        </Layout>     
    );
};


export default SalidasPage;