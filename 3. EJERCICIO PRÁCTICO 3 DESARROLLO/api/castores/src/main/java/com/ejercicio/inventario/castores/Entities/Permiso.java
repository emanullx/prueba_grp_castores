package com.ejercicio.inventario.castores.Entities;

import java.util.List;

public class Permiso {
    
    private Long id;
    private String clave;
    private String descripcion;
    private List<PermisoXRol> roles;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<PermisoXRol> getRoles() {
        return roles;
    }
    public void setRoles(List<PermisoXRol> roles) {
        this.roles = roles;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public Permiso() {
    }
    public Permiso(String clave, String descripcion, List<PermisoXRol> roles) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.roles = roles;
    }
}
