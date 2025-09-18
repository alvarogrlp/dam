package es.ies.puerto.model;

import java.util.Set;

public interface OperacionesInterfaces {

    /**
     * Metodo para crear empleado en el archivo
     * @param empleado que se quiere crear
     * @return boolean
     */
    public boolean create(Empleado empleado);

    /**
     * Metodo que lee una persona por su identificador
     * @param identificador que se quiere buscar
     * @return empleado buscado
     */
    public Empleado read(String identificador);

    /**
     * Metodo que lee un empleado pasando el empleado completo
     * @param empleado que se quiere leer
     * @return empleado
     */
    public Empleado read(Empleado empleado);

    /**
     * Metodo para actualizar un empleado
     * @param empleado con la nueva informacion
     * @return boolean
     */
    public boolean update(Empleado empleado);

    /**
     * Metodo para eliminar una persona por su identificador
     * @param identificador de la persona que se quiere eliminar
     * @return boolean
     */
    public boolean delete(String identificador);

    /**
     * Metodo para leer empleado de un puesto especifico
     * @param puesto que se quiere leer
     * @return Set de los empleados que coinciden
     */
    public Set<Empleado> empleadosPorPuesto(String puesto);

    /**
     * Metodo para leer los empleados que nacieron entre las fechas indicadas
     * @param fechaInicio la primera referencia
     * @param fechaFin ultima referencia
     * @return Set de los empleados que coinciden
     */
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}
