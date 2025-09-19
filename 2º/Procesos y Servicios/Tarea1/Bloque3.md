# Bloque 3

### Ejercicio 21

**Identifica el PID del proceso init/systemd y explica su función**

```bash
$ pidof systemd
1
```

El proceso **systemd** siempre tiene PID `1` y es el **padre de todos los procesos**. Se encarga de inicializar el sistema y adoptar procesos huérfanos.

---

### Ejercicio 22

**Explica qué ocurre con el PPID de un proceso hijo si su padre termina antes**

```bash
$ echo $$
4321
$ sleep 200 &
[1] 6789
$ exit
```

Cuando el **padre (bash con PID 4321)** termina, el hijo (`sleep 200`) es adoptado automáticamente por **systemd (PID 1)**.

```bash
$ ps -f -p 6789
UID   PID  PPID  C STIME TTY      TIME CMD
user 6789     1  0 10:15 ?        00:00:00 sleep 200
```

---

### Ejercicio 23

**Ejecuta un programa que genere varios procesos hijos y observa sus PIDs con ps**

Ejemplo con Firefox, que crea subprocesos para pestañas y extensiones:

```bash
$ firefox &
[1] 6900

$ ps -f --ppid 6900
UID   PID  PPID  C STIME TTY      TIME CMD
user 6900     1  5 10:17 ?        00:00:03 firefox
user 6912  6900  2 10:17 ?        00:00:01 Web Content
user 6915  6900  1 10:17 ?        00:00:00 WebExtensions
```

---

### Ejercicio 24

**Haz que un proceso quede en estado suspendido con `Ctrl+Z` y réanúdalo con `fg`**

```bash
$ spotify
^Z
[1]+  Stopped                 spotify

$ fg 1
spotify
```

---

### Ejercicio 25

**Lanza un proceso en segundo plano con `&` y obsérvalo con `jobs`**

```bash
$ discord &
[2] 7010

$ jobs
[1]+  Running                 spotify
[2]-  Running                 discord &
```

---

### Ejercicio 26

**Explica la diferencia entre los estados de un proceso: Running, Sleeping, Zombie, Stopped**

* **Running (R):** ejecutándose en CPU.
* **Sleeping (S):** esperando un evento o recurso (ej. Spotify minimizado).
* **Stopped (T):** detenido por el usuario (ej. `Ctrl+Z`).
* **Zombie (Z):** proceso terminado cuyo estado no fue recogido por su padre.

---

### Ejercicio 27

**Usa `ps -eo pid,ppid,stat,cmd` para mostrar los estados de varios procesos**

```bash
$ ps -eo pid,ppid,stat,cmd | head -10
  PID  PPID STAT CMD
    1     0 Ss   /sbin/systemd
 4100     1 Sl   gnome-terminal-
 4321  4100 S    bash
 5234     1 Sl   spotify
 5301     1 Sl   discord
 5478     1 Sl   firefox
 6912  5478 S    Web Content
 6915  5478 S    WebExtensions
 7010     1 Sl   discord
 7021  4321 R+   ps -eo pid,ppid,stat,cmd
```

---

### Ejercicio 28

**Ejecuta `watch -n 1 ps -e` y observa cómo cambian los procesos en tiempo real**

```bash
$ watch -n 1 ps -e
Every 1.0s: ps -e

  PID TTY          TIME CMD
    1 ?        00:00:03 systemd
 5234 ?        00:00:06 spotify
 5301 ?        00:00:03 discord
 5478 ?        00:00:09 firefox
 7025 pts/0    00:00:00 ps
 7030 pts/0    00:00:00 sleep   ← aparece y desaparece
```

Aquí vemos cómo procesos temporales como `sleep` aparecen y desaparecen en vivo.

---

### Ejercicio 29

**Explica la diferencia entre ejecutar un proceso con `&` y con `nohup`**

```bash
$ firefox &
[1] 7100
```

Con `&`, el proceso se ejecuta en segundo plano **pero se cerrará si cierro la terminal**.

```bash
$ nohup firefox &
[2] 7110
nohup: ignoring input and appending output to 'nohup.out'
```

Con `nohup`, el proceso **sobrevive al cierre de la terminal**.

---

### Ejercicio 30

**Usa `ulimit -a` para ver los límites de recursos de procesos en tu sistema**

```bash
$ ulimit -a
core file size          (blocks, -c) 0
data seg size           (kbytes, -d) unlimited
scheduling priority             (-e) 0
file size               (blocks, -f) unlimited
pending signals                 (-i) 15528
max locked memory       (kbytes, -l) 64
max memory size         (kbytes, -m) unlimited
open files                      (-n) 1024
pipe size            (512 bytes, -p) 8
stack size              (kbytes, -s) 8192
cpu time               (seconds, -t) unlimited
max user processes              (-u) 15528
virtual memory          (kbytes, -v) unlimited
file locks                      (-x) unlimited
```

Esto muestra los **límites de recursos por proceso** (memoria, archivos abiertos, procesos máximos, etc.).
