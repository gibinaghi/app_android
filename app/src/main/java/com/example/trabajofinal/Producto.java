package com.example.trabajofinal;

public class Producto {

    public Producto(){}
    public Producto(String nombre, String descripcion, String talle, double precio, int stockMinimo, int stock) {
        Nombre = nombre;
        Descripcion = descripcion;
        Talle = talle;
        Precio = precio;
        StockMinimo = stockMinimo;
        Stock = stock;
    }

    private int Id;
    private String Nombre;
    private String Descripcion;
    private String Talle;
    private double Precio;
    private int StockMinimo;
    private int Stock;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTalle() {
        return Talle;
    }

    public void setTalle(String talle) {
        Talle = talle;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getStockMinimo() {
        return StockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        StockMinimo = stockMinimo;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
