
import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { UserContext } from '../contexts/UserContext';

const PrivateRoute = () => {

    const { handleLoggedIn } = useContext(UserContext);
    const isLoggedIn = handleLoggedIn();

    return isLoggedIn ? <Outlet /> : <Navigate to="/Login" />;
}

export default PrivateRoute;