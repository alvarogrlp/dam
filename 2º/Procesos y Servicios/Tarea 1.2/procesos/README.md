# Lanzador de Procesos (CLI) - Spring Boot

## Descripción
Aplicación Spring Boot CLI que ejecuta procesos del sistema operativo Linux, captura stdout/stderr en tiempo real y persiste el historial de ejecuciones en ficheros `.txt`.

## Características
- ✅ Ejecución de comandos Linux predefinidos
- ✅ Captura de salida stdout/stderr en tiempo real con prefijos `[OUT]` y `[ERR]`
- ✅ Persistencia de ejecuciones en ficheros `.txt`
- ✅ Validación de comandos permitidos
- ✅ Arquitectura basada en interfaces con Spring components
- ✅ Cobertura de tests >80% con JaCoCo

## Arquitectura

```
es.ies.puerto.procesos/
├── domain/                      # Entidades de dominio
│   └── Job.java                # Representación de un trabajo/proceso
├── repositories/
│   ├── interfaces/             # Interfaces de repositorios
│   │   └── JobRepository.java
│   └── file/                   # Implementaciones
│       └── FileJobRepository.java  # Persistencia en ficheros
├── services/
│   ├── interfaces/             # Interfaces de servicios
│   │   └── CommandService.java
│   └── impl/                   # Implementaciones
│       ├── LsofServiceImpl.java    # Servicio lsof -i
│       ├── TopServiceImpl.java     # Servicio top -b -n1
│       └── PsHeadServiceImpl.java  # Servicio ps aux | head
├── controllers/                # Controladores CLI
│   └── CliController.java      # Controlador principal
└── ProcesosApplication.java    # Clase principal Spring Boot
```

## Comandos Disponibles

1. **lsof -i** - Lista archivos abiertos con conexiones de red
2. **top -b -n1** - Snapshot de procesos del sistema
3. **ps aux | head** - Lista los primeros procesos

## Requisitos

- Java 17+
- Maven 3.6+
- Sistema operativo Linux (para ejecución de comandos)
- Spring Boot 3.5.7

## Instalación y Ejecución

### 1. Clonar o descargar el proyecto

### 2. Compilar y ejecutar
```bash
mvn clean spring-boot:run
```

### 3. Ejecutar tests con cobertura
```bash
mvn clean test
```

### 4. Ver reporte de cobertura JaCoCo
Después de ejecutar los tests, el reporte se encuentra en:
```
target/site/jacoco/index.html
```

## Uso de la Aplicación

### Menú Principal
```
=== Lanzador de Procesos (CLI) Linux ===

================================================
Comandos disponibles:
================================================
1. lsof -i (List open files with network connections)
2. top -b -n1 (System processes snapshot)
3. ps aux | head (List first processes)
4. Ejecutar comando personalizado (validación de errores)
0. Salir
================================================
Seleccione una opción:
```

### Ejemplo de Ejecución

1. Seleccione una opción (1, 2, 3 o 4)
2. El comando se ejecutará y mostrará la salida en tiempo real:
   ```
   >>> Ejecutando: lsof -i
   ------------------------------------------------
   [OUT] COMMAND    PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
   [OUT] systemd      1 root   45u  IPv4  12345      0t0  TCP *:22 (LISTEN)
   ...
   ------------------------------------------------
   >>> Ejecución completada.
   >>> Job ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890
   >>> Exit Code: 0
   >>> Success: ✓
   >>> Salida guardada en: jobs-history/
   ```

3. Los resultados se guardan automáticamente en `jobs-history/`

### Validación de Comandos

Si intenta ejecutar un comando no permitido:
```
Ingrese el comando a ejecutar: ls -la

[ERROR] Comando no permitido: 'ls -la'
[ERROR] Solo se permiten los siguientes comandos:
[ERROR]   - lsof -i
[ERROR]   - top -b -n1
[ERROR]   - ps aux | head
[ERROR] La información del error ha sido almacenada.
[INFO] Error registrado con Job ID: xyz123...
```

## Estructura de Ficheros de Salida

Los ficheros se guardan en `jobs-history/` con el formato:
```
YYYYMMdd_HHmmss_<job-id>_<comando-sanitizado>.txt
```

Contenido del fichero:
```
=== JOB EXECUTION REPORT ===
ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890
Command: lsof -i
Executed At: 2025-10-31T10:30:45.123456
Exit Code: 0
Success: true

=== STDOUT ===
COMMAND    PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
systemd      1 root   45u  IPv4  12345      0t0  TCP *:22 (LISTEN)

=== STDERR ===
(empty)
```

## Tests

El proyecto incluye tests unitarios completos para:

- ✅ Domain (Job.java) - 100%
- ✅ Repositories (FileJobRepository.java) - >90%
- ✅ Services (LsofServiceImpl, TopServiceImpl, PsHeadServiceImpl) - >85%
- ✅ Controllers (CliController.java) - >80%

### Ejecutar tests
```bash
mvn clean test
```

### Ver reporte de cobertura
```bash
mvn clean test
# Abrir: target/site/jacoco/index.html
```

## Tecnologías

- **Spring Boot 3.5.7** - Framework principal
- **Java 17** - Lenguaje de programación
- **JUnit 5** - Framework de testing
- **Mockito** - Mocking para tests
- **JaCoCo** - Cobertura de código
- **Maven** - Gestión de dependencias

## Componentes Spring

- `@SpringBootApplication` - Clase principal
- `@Component` - CliController (CommandLineRunner)
- `@Service` - Servicios de comandos
- `@Repository` - Repositorio de persistencia

## Salir de la Aplicación

Para cerrar la aplicación:
1. Seleccione la opción `0` en el menú
2. Pulse `Ctrl+C` para finalizar definitivamente

## Autor

Proyecto desarrollado para DAM 2º - Programación de Servicios y Procesos

## Notas

- **Importante**: Los comandos Linux solo funcionarán en sistemas Linux/Unix
- En sistemas Windows, los comandos fallarán pero se registrará el error
- Todos los errores se persisten en el sistema de ficheros
- La aplicación valida que solo se ejecuten comandos de la lista permitida
