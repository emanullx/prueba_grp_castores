package com.ejercicio.inventario.castores.Entities;

import java.util.List;

public class Producto{
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private List<Inventario> inventarios;
    private List<Historico> historicos;
    private String urlImagen;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return this.precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public List<Inventario> getInventarios() {
        return inventarios;
    }
    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }
    public List<Historico> getHistoricos() {
        return historicos;
    }
    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }
    
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    } 
    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    public Producto() {
    }
    public Producto(String nombre, String descripcion, double precio, List<Inventario> inventarios,
            List<Historico> historicos, String urlImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.inventarios = inventarios;
        this.historicos = historicos;
        this.urlImagen = urlImagen;
    }
}