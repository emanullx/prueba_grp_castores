package com.ejercicio.inventario.castores.Entities;

import java.util.Optional;

public class Filtro<T> {
    private int maxRegPagina;
    private int total;
    private int next;
    private int offSet;
    private int paginas;
    private Optional<T> datos;

    public int getMaxRegPagina() {
        return maxRegPagina;
    }
    public void setMaxRegPagina(int maxRegPagina) {
        this.maxRegPagina = maxRegPagina;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getNext() {
        return next;
    }
    public void setNext(int next) {
        this.next = next;
    }
    public int getOffSet() {
        return offSet;
    }
    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }
    public Optional<T> getDatos() {
        return datos;
    }
    public void setDatos(Optional<T> datos) {
        this.datos = datos;
    }

    public Filtro() {
        this.datos = Optional.empty();
    }
    public int getPaginas() {
        return paginas;
    }
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
    public Filtro(int maxRegPagina, int total, int next, int offSet, int paginas, Optional<T> datos) {
        this.maxRegPagina = maxRegPagina;
        this.total = total;
        this.next = next;
        this.offSet = offSet;
        this.paginas = paginas;
        this.datos = datos;
    }
    
}
