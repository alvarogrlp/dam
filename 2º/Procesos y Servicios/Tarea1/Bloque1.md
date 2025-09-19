# Bloque 1

**1. Define qué es un proceso y en qué se diferencia de un programa**

Un programa es un conjunto estático de instrucciones guardadas en disco, mientras que un proceso es la instancia activa de ese programa en ejecución, con sus propios recursos de memoria, estado y estado de ejecución en un momento dado. La diferencia clave es que un programa es el código inactivo y un proceso es ese código cobrando vida, manejado por el sistema operativo.
[(Página de referencia)](https://www.profesionalreview.com/2020/06/20/cual-es-la-diferencia-entre-un-programa-y-un-proceso/#:~:text=B%C3%A1sicamente%2C%20el%20programa%20y%20el,diferencia%20entre%20proceso%20y%20programa.)

---

**2. Explica qué es el kernel y su papel en la gestión de procesos**

El kernel, o núcleo, es el componente central de un sistema operativo que actúa como el intermediario entre el hardware y el software, gestionando los recursos del sistema. Su papel en la gestión de procesos incluye la creación y finalización de procesos, la asignación y coordinación de la memoria y la CPU entre ellos, y la programación de su ejecución para asegurar un funcionamiento eficiente y seguro.
[(Página de referencia)](https://www.eaeprogramas.es/blog/negocio/tecnologia/que-es-el-kernel-cual-es-su-trabajo-y-como-funciona#:~:text=El%20Kernel%2C%20tambi%C3%A9n%20conocido%20como%20n%C3%BAcleo%2C%20es,aplicaciones%20y%20el%20procesamiento%20de%20datos%20f%C3%ADsicos.)

---

**3. ¿Qué son PID y PPID? Explica con un ejemplo**

* **PID (Process ID):** número único que identifica a un proceso en ejecución.
* **PPID (Parent Process ID):** número que identifica al proceso padre que lo creó.

Por ejemplo, si ejecutamos `gedit` desde la terminal, el proceso `gedit` tendrá un **PID único** y su **PPID** será el de la terminal (`bash`) desde la que fue lanzado.
[(Página de referencia)](https://www.ionos.es/digitalguide/servidores/know-how/que-es-el-pid/#:~:text=El%20PID%20es%20un%20n%C3%BAmero,la%20finalizaci%C3%B3n%20de%20un%20proceso.)

---

**4. Describe qué es un cambio de contexto y por qué es costoso**

El cambio de contexto ocurre cuando la CPU interrumpe la ejecución de un proceso para dar paso a otro. En este proceso, el sistema operativo debe guardar el estado del proceso actual (registros, contador de programa, pila, etc.) y cargar el estado del siguiente. Es costoso porque implica operaciones de almacenamiento y recuperación de datos que consumen tiempo y recursos, reduciendo el rendimiento general.
[(Página de referencia)](https://runebook.dev/es/docs/linux/scheduler/context-switching)

---

**5. Explica qué es un PCB (Process Control Block) y qué información almacena**

El **PCB** (Bloque de Control de Proceso) es una estructura de datos que utiliza el sistema operativo para almacenar toda la información necesaria sobre un proceso. Incluye:

* Identificación del proceso (PID).
* Estado del proceso.
* Contador de programa.
* Registros de CPU.
* Información de planificación.
* Información de memoria.
  [(Página de referencia)](https://www.scaler.com/topics/operating-system/process-control-block-in-os/)

---

**6. Diferencia entre proceso padre y proceso hijo**

* **Proceso padre:** es el proceso que crea a otro mediante llamadas como `fork()` en Linux.
* **Proceso hijo:** es el proceso resultante de esa creación, que hereda recursos del padre pero tiene su propio PID.
  [(Página de referencia)](https://linux.die.net/man/2/fork)

---

**7. Explica qué ocurre cuando un proceso queda huérfano en Linux**

Un proceso queda **huérfano** cuando su proceso padre finaliza antes que él. En Linux, estos procesos son automáticamente adoptados por el proceso `init` (PID 1), que se encarga de supervisarlos y liberar sus recursos cuando terminan.
[(Página de referencia)](https://www.baeldung.com/linux/orphan-process)

---

**8. ¿Qué es un proceso zombie? Da un ejemplo de cómo puede ocurrir**

Un proceso zombie es un proceso que ya terminó su ejecución, pero cuyo padre no ha leído aún su estado de salida. Esto mantiene una entrada en la tabla de procesos.
Ejemplo: si un proceso hijo termina y el proceso padre no ejecuta `wait()`, el hijo permanece como zombie hasta que el padre recoja su estado.
[(Página de referencia)](https://www.redhat.com/sysadmin/zombie-processes)

---

**9. Diferencia entre concurrencia y paralelismo**

* **Concurrencia:** varios procesos/hilos progresan de manera alternada compartiendo el mismo procesador.
* **Paralelismo:** varios procesos/hilos se ejecutan exactamente al mismo tiempo en procesadores diferentes.
  [(Página de referencia)](https://www.freecodecamp.org/news/concurrency-vs-parallelism-whats-the-difference/)

---

**10. Explica qué es un hilo (thread) y en qué se diferencia de un proceso**

Un **hilo** es la unidad mínima de ejecución dentro de un proceso. Los hilos comparten el mismo espacio de memoria y recursos del proceso que los contiene, lo que los hace más ligeros que un proceso completo.
Diferencia: los procesos tienen memoria y recursos independientes, mientras que los hilos comparten los del mismo proceso, permitiendo comunicación más rápida.
[(Página de referencia)](https://www.guru99.com/difference-between-process-and-thread.html)

