import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './App.css';
import { Routes, Route, Navigate  } from 'react-router-dom';
import { UserProvider } from './contexts/UserContext';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import InventarioPage from './pages/InventarioPage';
import PrivateRoute from './components/PrivateRoute';
import AuthRoute from './components/AuthRoute';
import HistoricoPage from './pages/HistoricoPage';
import SalidasPage from './pages/SalidasPage';
import NoMatchPage from './pages/NoMatchPage';

const App = () => {
  return (
    <div>
       <UserProvider>
          <Routes>
            <Route path='/' element={<Navigate replace to="/Inicio" />} />
            <Route path='/Login' element={
                <AuthRoute/>
            }> 
                <Route path='/Login' element={<LoginPage />} />   
            </Route>
            <Route path='/Inicio' element={<PrivateRoute />}>
              <Route path='/Inicio' element={
                  <HomePage />
              } />
            </Route>   
            <Route path='/Inventario' element={<PrivateRoute />}>
              <Route path='/Inventario' element={
                  <InventarioPage />
              } />
            </Route>   
             <Route path='/Historico' element={<PrivateRoute />}>
              <Route path='/Historico' element={
                  <HistoricoPage />
              } />
            </Route>   
             <Route path='/Salidas' element={<PrivateRoute />}>
              <Route path='/Salidas' element={
                  <SalidasPage />
              } />
            </Route>   
            <Route path="*" element={<NoMatchPage />} />
          </Routes>
       </UserProvider>   
    </div>
  );
};

export default App;
