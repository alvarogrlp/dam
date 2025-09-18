package es.ies.puerto.model;

public class Empleado extends Persona {

    String puesto;
    Double salario;

    public Empleado() {
    }

    public Empleado(String identificador, String nombre, String fechaNacimiento, String puesto, Double salario) {
        super(identificador, nombre, fechaNacimiento);
        this.puesto = puesto;
        this.salario = salario;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSalario() {
        return this.salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Empleado puesto(String puesto) {
        setPuesto(puesto);
        return this;
    }

    public Empleado salario(Double salario) {
        setSalario(salario);
        return this;
    }

    @Override
    public String toString() {
        return super.toString()
                + "," + getPuesto()
                + "," + getSalario();
    }

}
