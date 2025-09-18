package es.ies.puerto.model;

import java.util.Objects;

public class Persona {

    String identificador;
    String nombre;
    String fechaNacimiento;

    public Persona() {
    }

    public Persona(String identificador) {
        this.identificador = identificador;
    }

    public Persona(String identificador, String nombre, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona identificador(String identificador) {
        setIdentificador(identificador);
        return this;
    }

    public Persona nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Persona fechaNacimiento(String fechaNacimiento) {
        setFechaNacimiento(fechaNacimiento);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        Persona persona = (Persona) o;
        return Objects.equals(identificador, persona.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return getIdentificador()
                + "," + getNombre()
                + "," + getFechaNacimiento();
    }

}
