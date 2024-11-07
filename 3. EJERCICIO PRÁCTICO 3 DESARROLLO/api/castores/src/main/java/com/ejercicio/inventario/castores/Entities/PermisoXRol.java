package com.ejercicio.inventario.castores.Entities;

public class PermisoXRol {
    private Long id;
    
    private Rol rol;

    private Permiso permiso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public PermisoXRol() {
    }

    public PermisoXRol(Rol rol, Permiso permiso) {
        this.rol = rol;
        this.permiso = permiso;
    }
}
