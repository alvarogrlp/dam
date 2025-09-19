# Bloque 2

### Ejercicio 11

**Usa `echo $$` para mostrar el PID del proceso actual**

```bash
$ echo $$
3245
```

---

### Ejercicio 12

**Usa `echo $PPID` para mostrar el PID del proceso padre**

```bash
$ echo $PPID
2891
```

---

### Ejercicio 13

**Ejecuta `pidof systemd` y explica el resultado**

```bash
$ pidof systemd
1
```

(`systemd` es el primer proceso del sistema, siempre con PID 1).

---

### Ejercicio 14

**Abre un programa gráfico (ejemplo: `gedit`) y usa `pidof` para obtener sus PID**

```bash
$ gedit &
[1] 4521

$ pidof gedit
4521
```

---

### Ejercicio 15

**Ejecuta `ps -e` y explica qué significan sus columnas**

```bash
$ ps -e
  PID TTY          TIME CMD
    1 ?        00:00:03 systemd
 4100 ?        00:00:00 gnome-terminal-
 4321 pts/0    00:00:00 bash
 5234 ?        00:00:04 spotify
 5301 ?        00:00:02 discord
 5478 ?        00:00:06 firefox
 6002 ?        00:00:00 ps
```

* `PID`: identificador del proceso
* `TTY`: terminal asociado
* `TIME`: tiempo de CPU usado
* `CMD`: comando ejecutado

---

### Ejercicio 16

**Ejecuta `ps -f` y observa la relación entre procesos padre e hijo**

```bash
$ ps -f
UID   PID  PPID  C STIME TTY      TIME CMD
user 4100     1  0 09:12 ?        00:00:00 gnome-terminal-
user 4321  4100  0 09:14 pts/0    00:00:00 bash
user 5234     1  2 09:15 ?        00:00:04 spotify
user 5301     1  1 09:16 ?        00:00:02 discord
user 5478     1  5 09:17 ?        00:00:06 firefox
user 6002  4321  0 09:20 pts/0    00:00:00 ps -f
```

Aquí se ve que la **shell (bash)** depende de `gnome-terminal`, mientras que apps como Spotify, Discord o Firefox dependen directamente de `systemd`.

---

### Ejercicio 17

**Usa `ps -axf` o `pstree` para mostrar el árbol de procesos y dibújalo**

```bash
$ pstree
systemd─┬─NetworkManager
        ├─gnome-terminal─┬─bash───pstree
        │
        ├─spotify─┬─spotify
        │
        ├─discord─┬─discord
        │
        └─firefox─┬─firefox───Web Content───WebExtensions
```

Se aprecia cómo cada aplicación gráfica (Spotify, Discord, Firefox) cuelga directamente de `systemd`, mientras que la shell cuelga de `gnome-terminal`.

---

### Ejercicio 18

**Ejecuta `top` o `htop` y localiza el proceso con mayor uso de CPU**

```bash
$ top
top - 09:25:31 up  1:42,  2 users,  load average: 0.89, 0.67, 0.45
Tasks: 212 total,   1 running, 211 sleeping,   0 stopped,   0 zombie
%Cpu(s): 32.0 us,  5.0 sy,  0.0 ni, 63.0 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
MiB Mem :   15942 total,   5271 free,   7540 used,   3131 buff/cache
MiB Swap:   4095 total,   4095 free,      0 used.   7042 avail Mem 

  PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND
 5478 user      20   0 2701840 420000 122000 S  48.3  2.6   3:12.14 firefox
 5234 user      20   0 1912456 215000  82300 S  27.1  1.3   2:05.11 spotify
 5301 user      20   0 1892200 180500  69000 S  12.5  1.1   1:43.25 discord
```

Aquí, el proceso con más uso de CPU es **Firefox**.

---

### Ejercicio 19

**Ejecuta `sleep 100` en segundo plano y busca su PID con `ps`**

```bash
$ sleep 100 &
[1] 6123

$ ps -e | grep sleep
 6123 ?        00:00:00 sleep
```

---

### Ejercicio 20

**Finaliza un proceso con `kill` y comprueba con `ps` que ya no está**

```bash
$ kill 6123

$ ps -e | grep sleep
```