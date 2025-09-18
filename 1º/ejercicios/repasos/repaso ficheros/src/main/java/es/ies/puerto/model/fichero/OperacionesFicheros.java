package es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.OperacionesInterfaces;

public class OperacionesFicheros implements OperacionesInterfaces {

    File fichero;
    String path = "C:\\Users\\alvar\\Desktop\\Programación\\Repositorios\\programacion\\ejercicios\\repaso\\src\\main\\resources\\archivo.txt";

    public OperacionesFicheros() {
        fichero = new File(path);
        if (!fichero.exists() || !fichero.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero" + path);
        }
    }

    /**
     * Metodo para crear un fichero
     *
     * @param data de lo que se quiere crear
     * @param file donde se quiere crear
     * @return boolean
     */
    private boolean create(String data, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para leer un archivo entero
     *
     * @param file que se quiere leer
     * @return Set del archivo
     */
    private Set<Empleado> read(File file) {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayLine = line.split(",");
                Empleado empleado = new Empleado(arrayLine[0], arrayLine[1], arrayLine[2],
                        arrayLine[3], Double.valueOf(arrayLine[4]));
                empleados.add(empleado);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return empleados;
    }

    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        if (empleados.contains(empleado)) {
            return false;
        }
        return create(empleado.toString(), fichero);
    }

    @Override
    public Empleado read(String identificador) {
        if (identificador == null) {
            return null;
        }
        Empleado empleadoBuscado = new Empleado(identificador, "", "", "", 1.0);
        List<Empleado> empleados = new ArrayList<>(read(fichero));
        if (empleados.contains(empleadoBuscado)) {
            int posicion = empleados.indexOf(empleadoBuscado);
            return empleados.get(posicion);
        }
        return null;
    }

    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return null;
        }
        Set<Empleado> empleados = read(fichero);
        if (empleados.contains(empleado)) {
            return empleado;
        }
        return null;
    }

    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        if (!empleados.contains(empleado)) {
            return false;
        }

        return false;
    }

    private boolean updateFile(Set<Empleado> empleados, File file) {
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        for (Empleado empleado : empleados) {
            create(empleado);
        }
        return true;
    }

    @Override
    public boolean delete(String identificador) {
        if (identificador == null) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        for (Empleado empleadoBuscado : empleados) {
            if (empleadoBuscado.getIdentificador().equals(identificador)) {
                empleados.remove(empleadoBuscado);
                return updateFile(empleados, fichero);
            }
        }

        return true;
    }

    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        Set<Empleado> empleados = read(fichero);
        Iterator<Empleado> iterator = empleados.iterator();
        while (iterator.hasNext()) {
            Empleado empleadoIterador = iterator.next();
            if (!empleadoIterador.getPuesto().equals(puesto)) {
                iterator.remove();
            }
        }
        return empleados;
    }

    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'empleadosPorEdad'");
    }

}
