package com.UdeA.ciclo3.Modelos;

import javax.persistence.*;

@Entity
@Table(name="Empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String nombre;
    private String dirreccion;
    private String telefono;
    private String NIF;

    public Empresa() {
    }

    public Empresa(String nombre, String dirreccion, String telefono, String NIF) {
        this.nombre = nombre;
        this.dirreccion = dirreccion;
        this.telefono = telefono;
        this.NIF = NIF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirreccion() {
        return dirreccion;
    }

    public void setDirreccion(String dirreccion) {
        this.dirreccion = dirreccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }
}

