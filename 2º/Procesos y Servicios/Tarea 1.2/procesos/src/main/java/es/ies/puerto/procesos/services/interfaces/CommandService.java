package es.ies.puerto.procesos.services.interfaces;

import es.ies.puerto.procesos.domain.Job;

/**
 * Interface para los servicios de comandos
 */
public interface CommandService {
    /**
     * Ejecuta el comando asociado al servicio
     * @return El Job con el resultado de la ejecución
     * @throws Exception Si hay un error durante la ejecución
     */
    Job execute() throws Exception;

    /**
     * Obtiene el comando que ejecuta este servicio
     * @return El comando como String
     */
    String getCommand();

    /**
     * Obtiene el nombre descriptivo del servicio
     * @return El nombre del servicio
     */
    String getName();
}
