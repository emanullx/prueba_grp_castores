package com.ejercicio.inventario.castores.Entities;

import java.util.List;

public class Rol {
    
    private Long id;
    private String nombre;
    private List<PermisoXRol> permisos;
    private Usuario usuario;
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
    public List<PermisoXRol> getPermisos() {
        return permisos;
    }
    public void setPermisos(List<PermisoXRol> permisos) {
        this.permisos = permisos;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Rol() {
    }
    public Rol(String nombre, List<PermisoXRol> permisos, Usuario usuario) {
        this.nombre = nombre;
        this.permisos = permisos;
        this.usuario = usuario;
    }
    
}
