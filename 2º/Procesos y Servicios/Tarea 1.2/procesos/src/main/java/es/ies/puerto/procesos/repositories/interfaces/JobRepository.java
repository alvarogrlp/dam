package es.ies.puerto.procesos.repositories.interfaces;

import es.ies.puerto.procesos.domain.Job;
import java.io.IOException;
import java.util.List;

/**
 * Interface para el repositorio de Jobs
 */
public interface JobRepository {
    /**
     * Guarda un Job en el sistema de persistencia
     * @param job El job a guardar
     * @throws IOException Si hay un error de I/O
     */
    void save(Job job) throws IOException;

    /**
     * Obtiene todos los jobs almacenados
     * @return Lista de jobs
     */
    List<Job> findAll();

    /**
     * Busca un job por su ID
     * @param id El ID del job
     * @return El job encontrado o null
     */
    Job findById(String id);
}
