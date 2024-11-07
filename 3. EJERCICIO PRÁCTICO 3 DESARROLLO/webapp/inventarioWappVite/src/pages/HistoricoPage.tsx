import { useContext } from "react";
import Historico from "../components/Historico";
import Layout from "../components/Layout";
import { UserContext } from "../contexts/UserContext";
import { Navigate } from "react-router-dom";

const HistoricoPage = () => {
    const { getUser } = useContext(UserContext);
    const user = getUser();

    if (!user || !user.rol || !user.rol.permisos || !user.rol.permisos.find((p) => p.permiso.clave === "HIS01")) {
        return <Navigate to="/Inicio" />;
    }

    return (
         <Layout user={user}>
            <Historico user={user}/>
        </Layout>     
    );
};


export default HistoricoPage;