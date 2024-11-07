package com.ejercicio.inventario.castores.Entities;

public class Usuario {
   
    private Long id;
    private String nombre;
    private String contraseña;
    private String correo;
    private int status;
    private Rol rol;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String password) {
        this.contraseña = password;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }   
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Usuario() {
    }
    public Usuario(String nombre, String contraseña, String correo, int status, Rol rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.status = status;
        this.rol = rol;
    }
    
}
