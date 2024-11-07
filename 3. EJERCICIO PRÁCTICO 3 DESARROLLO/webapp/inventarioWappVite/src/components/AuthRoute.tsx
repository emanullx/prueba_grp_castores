import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { UserContext } from '../contexts/UserContext';

const AuthRoute = () => {
   
    const { handleLoggedIn } = useContext(UserContext);

    const isLoggedIn = handleLoggedIn();

    return !isLoggedIn ? <Outlet /> : <Navigate to="/Inicio" />;
}

export default AuthRoute;