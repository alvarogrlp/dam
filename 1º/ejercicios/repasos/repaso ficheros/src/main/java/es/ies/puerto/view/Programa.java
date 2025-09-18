package es.ies.puerto.view;

import java.io.File;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.fichero.OperacionesFicheros;

public class Programa {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\alvar\\Desktop\\Programación\\Repositorios\\programacion\\ejercicios\\repaso\\src\\main\\resources\\archivo.txt");
        Empleado empleado = new Empleado("23223", "Algo", "12/04/2002", "Gerente", 20.000);
        OperacionesFicheros operaciones = new OperacionesFicheros();
        operaciones.create(empleado);
        System.out.println(operaciones.read(empleado.getIdentificador()));
    }
}
