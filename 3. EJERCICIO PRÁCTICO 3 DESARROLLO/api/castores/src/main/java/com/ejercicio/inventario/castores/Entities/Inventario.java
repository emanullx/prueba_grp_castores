package com.ejercicio.inventario.castores.Entities;

public class Inventario {
    private Long id;
    private String ubicacion;
    private int stock;
    private int status;
    
    private Producto producto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Inventario() {
    }
    public Inventario(String ubicacion, int stock, int status, Producto producto) {
        this.ubicacion = ubicacion;
        this.stock = stock;
        this.status = status;
        this.producto = producto;
    }
}
