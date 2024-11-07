package com.ejercicio.inventario.castores.Entities;

import java.util.Date;

public class Historico {
    private Long id;
    private String ubicacion;
    private int stockAntes;
    private int stockDespues;
    private Date fechaMovimiento;
    private int tipoMovimiento;
    private int movimiento;
    
    private Usuario usuario;
    
    private Producto producto;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public int getStockAntes() {
        return stockAntes;
    }
    public void setStockAntes(int stockAntes) {
        this.stockAntes = stockAntes;
    }
    public int getStockDespues() {
        return stockDespues;
    }
    public void setStockDespues(int stockDespues) {
        this.stockDespues = stockDespues;
    }
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }
    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    public int getTipoMovimiento() {
        return tipoMovimiento;
    }
    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    public int getMovimiento() {
        return movimiento;
    }
    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Historico() {
    }
    public Historico(String ubicacion, int stockAntes, int stockDespues, Date fechaMovimiento,
            int tipoMovimiento, int movimiento, Usuario usuario, Producto producto) {
        this.ubicacion = ubicacion;
        this.stockAntes = stockAntes;
        this.stockDespues = stockDespues;
        this.fechaMovimiento = fechaMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.movimiento = movimiento;
        this.usuario = usuario;
        this.producto = producto;
    }  
}
