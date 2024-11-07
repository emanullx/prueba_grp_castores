import { useContext } from "react";
import Inicio from "../components/Inicio";
import Layout from "../components/Layout";
import { UserContext } from "../contexts/UserContext";

const HomePage = () => {
    const { getUser } = useContext(UserContext);
    const user = getUser();
    return (
         <Layout user={user}>
            <Inicio user={user}/>
        </Layout>     
    );
};


export default HomePage;