// src/context/UserContext.jsx
import React, { createContext, useState } from 'react';

// Crear el contexto
const UserContext = createContext({});

// Proveedor del contexto
function  UserProvider ({ children }) {
  const [user, setUser] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // useEffect(() => {
  //   // Recuperar el usuario del localStorage si está presente
  //   const storedUser = localStorage.getItem('user');
  //   if (storedUser) {
  //     setUser(JSON.parse(storedUser));
  //   }
  // }, []);

  const login = (userData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
    setIsLoggedIn(true);
    window.location.reload();
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
    setIsLoggedIn(false);
    window.location.reload();
  };
  const handleLoggedIn = () => {
    const usuario = localStorage.getItem('user');
    
    return usuario ? true : false;
  };
   const getUser = () => {
    const storedUser = localStorage.getItem('user');
    const usuario = storedUser ? JSON.parse(storedUser) : null;
    
    return usuario;
  };
  return (
    <UserContext.Provider value={{ user, isLoggedIn, handleLoggedIn, login, logout, getUser }}>
      {children}
    </UserContext.Provider>
  );
};


export { UserContext, UserProvider };