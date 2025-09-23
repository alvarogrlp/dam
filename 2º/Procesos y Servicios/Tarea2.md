## Preparación

Crea tu área de trabajo y variables útiles:

```bash
mkdir -p ~/dam/{bin,logs,units}
export DAM=~/dam
echo 'export DAM=~/dam' >> ~/.bashrc
```

Comprobar versión de systemd y que el *user manager* está activo:

```bash
systemctl --user --version | head -n1
systemctl --user status --no-pager | head -n5
```
**Pega salida aquí:**

```text

```

**Reflexiona la salida:**

```text

```

---

## Bloque 1 — Conceptos (breve + fuentes)

1) ¿Qué es **systemd** y en qué se diferencia de SysV init?  

**Respuesta:**  Systemd es un sistema de inicio moderno que reemplaza al clásico SysV init. Se basa en un diseño paralelo y modular, lo que permite iniciar múltiples servicios simultáneamente y reducir el tiempo de arranque. Además, systemd permite una gestión dinámica de servicios, lo que significa que se pueden habilitar, deshabilitar, iniciar y detener servicios fácilmente con comandos como systemctl. En comparación con SysV init, systemd ofrece un enfoque más limpio y eficiente para la gestión de servicios y un control más granular sobre los recursos del sistema.

_Fuentes:_ https://www.maxizamorano.com/entrada/19/proceso-de-arranque-en-linux-systemd-vs-sysv-init


2) **Servicio** vs **proceso** (ejemplos).  

**Respuesta:**  

_Fuentes:_

3) ¿Qué son los **cgroups** y para qué sirven?  

**Respuesta:**  Los cgroups (grupos de control) son una función del kernel de Linux que permite organizar los procesos en grupos jerárquicos y limitar, contabilizar y aislar el uso de recursos del sistema (CPU, memoria, E/S de disco, red) para cada grupo de procesos.

_Fuentes:_ https://docs.aws.amazon.com/es_es/linux/al2023/ug/cgroupv2.html#:~:text=Un%20grupo%20de%20control%20(%20cgroup%20),tiempo%20de%20ejecuci%C3%B3n%20de%20contenedores%2C%20y%20mediante

4) ¿Qué es un **unit file** y tipos (`service`, `timer`, `socket`, `target`)?  

**Respuesta:**  Un unit file es un archivo de configuración en systemd que describe una unidad de servicio para el sistema, como un servicio, un temporizador, un punto de montaje o un grupo de servicios.

_Fuentes:_ https://nebul4ck.wordpress.com/2015/02/11/sobre-systemd-mejoras-en-systemd-units-y-targets-uso-de-systemctl-compatibilidad-con-sysv/

5) ¿Qué hace `journalctl` y cómo ver logs **de usuario**?  

**Respuesta:**  journalctl es una utilidad para consultar los registros del sistema (logs) gestionados por systemd en Linux, mostrando información del arranque, servicios, y errores en un formato binario fácil de buscar. 

_Fuentes:_ https://keepcoding.io/blog/que-es-journalctl-y-como-usarlo/

---

## Bloque 2 — Práctica guiada (todo en tu `$DAM`)

> Si un comando pide permisos que no tienes, usa la **versión `--user`** o consulta el **ayuda** con `--help` para alternativas.

### 2.1 — PIDs básicos

**11.** PID de tu shell y su PPID.

```bash
echo "PID=$$  PPID=$PPID"
```
**Salida:**

```text
PID=18182 PPID=18173
```

**Pregunta:** ¿Qué proceso es el padre (PPID) de tu shell ahora?  

**Respuesta:**
    dam        18182   18173  0 17:17 pts/0    00:00:00 bash

---

**12.** PID del `systemd --user` (manager de usuario) y explicación.

```bash
pidof systemd --user || pgrep -u "$USER" -x systemd
```

**Salida:**

```text
    No puede usar el comando anterior, asi que tuve que modificarlo al siguiente:

    pidof systemd || pgrep -u "$USER" -x systemd

    3280
```
**Pregunta:** ¿Qué hace el *user manager* de systemd para tu sesión?  

**Respuesta:** El user manager de systemd para tu sesión inicia y gestiona servicios y procesos a nivel de usuario, permitiendo que los usuarios ejecuten sus propias unidades y servicios sin necesidad de permisos de root.

---

### 2.2 — Servicios **de usuario** con systemd

Vamos a crear un servicio sencillo y un timer **en tu carpeta** `~/.config/systemd/user` o en `$DAM/units` (usaremos la primera para que `systemctl --user` lo encuentre).

**13.** Prepara directorios y script de práctica.

```bash
mkdir -p ~/.config/systemd/user "$DAM"/{bin,logs}
cat << 'EOF' > "$DAM/bin/fecha_log.sh"
#!/usr/bin/env bash
mkdir -p "$HOME/dam/logs"
echo "$(date --iso-8601=seconds) :: hello from user timer" >> "$HOME/dam/logs/fecha.log"
EOF
chmod +x "$DAM/bin/fecha_log.sh"
```

**14.** Crea el servicio **de usuario** `fecha-log.service` (**Type=simple**, ejecuta tu script).

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.service
[Unit]
Description=Escribe fecha en $HOME/dam/logs/fecha.log

[Service]
Type=simple
ExecStart=%h/dam/bin/fecha_log.sh
EOF

systemctl --user daemon-reload
systemctl --user start fecha-log.service
systemctl --user status fecha-log.service --no-pager -l | sed -n '1,10p'
```
**Salida (pega un extracto):**

```text
2025-09-23T18:25:49+01:00 :: hello from user timer
```
**Pregunta:** ¿Se creó/actualizó `~/dam/logs/fecha.log`? Muestra las últimas líneas:

```bash
tail -n 5 "$DAM/logs/fecha.log"
```

**Salida:**

```text
2025-09-23T18:25:49+01:00 :: hello from user timer
```

**Reflexiona la salida:**

```text
Con el comando tail, podemo comprobar los logs del archivo que creamos antes.
```

---

**15.** Diferencia **enable** vs **start** (modo usuario). Habilita el **timer**.

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.timer
[Unit]
Description=Timer (usuario): ejecuta fecha-log.service cada minuto

[Timer]
OnCalendar=*:0/1
Unit=fecha-log.service
Persistent=true

[Install]
WantedBy=timers.target
EOF

systemctl --user daemon-reload
systemctl --user enable --now fecha-log.timer
systemctl --user list-timers --all | grep fecha-log || true
```

**Salida (recorta):**

```text
Tue 2025-09-23 18:47:00 WEST  47s -                                       - fecha-log.timer                fecha-log.service
```
**Pregunta:** ¿Qué diferencia hay entre `enable` y `start` cuando usas `systemctl --user`?  

**Respuesta:**
La principal diferencia es que systemctl --user enable configura un servicio para que se inicie automáticamente al arrancar el usuario, mientras que systemctl --user start inicia el servicio de forma inmediata y manual, sin asegurar que se inicie en futuros reinicios.

---

**16.** Logs recientes **del servicio de usuario** con `journalctl --user`.

```bash
journalctl --user -u fecha-log.service -n 10 --no-pager
```

**Salida:**

```text
sep 23 18:25:49 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 18:47:08 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 18:47:09 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 18:48:32 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 18:49:32 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 18:50:32 a108pc07 systemd[3280]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
```
**Pregunta:** ¿Ves ejecuciones activadas por el timer? ¿Cuándo fue la última?  

**Respuesta:**
sep 23 18:50:32

---

### 2.3 — Observación de procesos sin root

**17.** Puertos en escucha (lo que puedas ver como usuario).

```bash
lsof -i -P -n | grep LISTEN || ss -lntp
```
**Salida:**

```text
State        Recv-Q       Send-Q              Local Address:Port                Peer Address:Port       Process       
LISTEN       0            4096                      0.0.0.0:56641                    0.0.0.0:*                        
LISTEN       0            4096                      0.0.0.0:8000                     0.0.0.0:*                        
LISTEN       0            4096                      0.0.0.0:56141                    0.0.0.0:*                        
LISTEN       0            64                        0.0.0.0:38771                    0.0.0.0:*                        
LISTEN       0            64                        0.0.0.0:2049                     0.0.0.0:*                        
LISTEN       0            4096                    127.0.0.1:631                      0.0.0.0:*                        
LISTEN       0            4096                      0.0.0.0:111                      0.0.0.0:*                        
LISTEN       0            32                  192.168.122.1:53                       0.0.0.0:*                        
LISTEN       0            4096                      0.0.0.0:49143                    0.0.0.0:*                        
LISTEN       0            4096                   127.0.0.54:53                       0.0.0.0:*                        
LISTEN       0            4096                127.0.0.53%lo:53                       0.0.0.0:*                        
LISTEN       0            4096                      0.0.0.0:59503                    0.0.0.0:*                        
LISTEN       0            4096                         [::]:8000                        [::]:*                        
LISTEN       0            4096                         [::]:40691                       [::]:*                        
LISTEN       0            64                           [::]:35163                       [::]:*                        
LISTEN       0            64                           [::]:2049                        [::]:*                        
LISTEN       0            4096                        [::1]:631                         [::]:*                        
LISTEN       0            4096                            *:22                             *:*                        
LISTEN       0            4096                         [::]:111                         [::]:*                        
LISTEN       0            511                             *:80                             *:*                        
LISTEN       0            4096                         [::]:33741                       [::]:*                        
LISTEN       0            4096                         [::]:48471                       [::]:*                        
LISTEN       0            4096                         [::]:45293                       [::]:*                        
LISTEN       0            4096                            *:9100                           *:*  
```
**Pregunta:** ¿Qué procesos *tuyos* están escuchando? (si no hay, explica por qué)  

**Respuesta:**
Todos los tcp, al ejecutar el comando netstat -tuln, puedes comprobarlo.
---

**18.** Ejecuta un proceso bajo **cgroup de usuario** con límite de memoria.

```bash
systemd-run --user --scope -p MemoryMax=50M sleep 200
ps -eo pid,ppid,cmd,stat | grep "[s]leep 200"
```

**Salida:**

```text

```
**Pregunta:** ¿Qué ventaja tiene lanzar con `systemd-run --user` respecto a ejecutarlo “a pelo”?  

**Respuesta:**
Running as unit: run-r1c29c652ee4c4257ab392d32089aebf9.scope; invocation ID: 1f46e19b2e624ceb9f9b6745a3d0ca1d
---

**19.** Observa CPU en tiempo real con `top` (si tienes `htop`, también vale).

```bash
top -b -n 1 | head -n 15
```
**Salida (resumen):**

```text
Tareas: 417 total,   1 ejecutar,  416 hibernar,    0 detener,    0 zombie
%Cpu(s):  0,7 us,  0,7 sy,  0,0 ni, 98,0 id,  0,7 wa,  0,0 hi,  0,0 si,  0,0 st 
MiB Mem :  31453,3 total,  22969,2 libre,   5293,1 usado,   3857,5 búf/caché    
MiB Intercambio:   2048,0 total,   2048,0 libre,      0,0 usado.  26160,2 dispon

```
**Pregunta:** ¿Cuál es tu proceso con mayor `%CPU` en ese momento?  

**Respuesta:** 57998 dam       20   0   17224   5760   3584 R   8,3   0,0   0:00.02 top

---

**20.** Traza syscalls de **tu propio proceso** (p. ej., el `sleep` anterior).
> Nota: Sin root, no podrás adjuntarte a procesos de otros usuarios ni a algunos del sistema.

```bash
pid=$(pgrep -u "$USER" -x sleep | head -n1)
strace -p "$pid" -e trace=nanosleep -tt -c -f 2>&1 | sed -n '1,10p'
```

**Salida (fragmento):**

```text

```
**Pregunta:** Explica brevemente la syscall que observaste.  

**Respuesta:** No pude ejecutarlo por que no tengo permisos

---

### 2.4 — Estados y jerarquía (sin root)

**21.** Árbol de procesos con PIDs.

```bash
pstree -p | head -n 50
```

**Salida (recorta):**

```text
systemd(1)-+-ModemManager(1846)-+-{ModemManager}(1856)
           |                    |-{ModemManager}(1859)
           |                    `-{ModemManager}(1861)
           |-NetworkManager(1814)-+-{NetworkManager}(1851)
           |                      |-{NetworkManager}(1852)
           |                      `-{NetworkManager}(1853)
           |-accounts-daemon(1155)-+-{accounts-daemon}(1179)
```
**Pregunta:** ¿Bajo qué proceso aparece tu `systemd --user`?  

**Respuesta:** Bajo ninguno es el primero.

---

**22.** Estados y relación PID/PPID.

```bash
ps -eo pid,ppid,stat,cmd | head -n 20
```
**Salida:**

```text
    PID    PPID STAT CMD
      1       0 Ss   /sbin/init splash
      2       0 S    [kthreadd]
      3       2 S    [pool_workqueue_release]
      4       2 I<   [kworker/R-rcu_g]
      5       2 I<   [kworker/R-rcu_p]
      6       2 I<   [kworker/R-slub_]
      7       2 I<   [kworker/R-netns]
     10       2 I<   [kworker/0:0H-events_highpri]
     12       2 I<   [kworker/R-mm_pe]
     13       2 I    [rcu_tasks_kthread]
     14       2 I    [rcu_tasks_rude_kthread]
     15       2 I    [rcu_tasks_trace_kthread]
     16       2 S    [ksoftirqd/0]
     17       2 I    [rcu_preempt]
     18       2 S    [migration/0]
     19       2 S    [idle_inject/0]
     20       2 S    [cpuhp/0]
     21       2 S    [cpuhp/1]
     22       2 S    [idle_inject/1]

```
**Pregunta:** Explica 3 flags de `STAT` que veas (ej.: `R`, `S`, `T`, `Z`, `+`).  

**Respuesta:** R (Running): El proceso se está ejecutando activamente o está listo para ejecutarse en la CPU. 
S (Interruptible Sleep): El proceso está esperando un evento o señal para poder continuar. 
D (Uninterruptible Sleep): El proceso está en espera por una operación de entrada/salida (E/S) y no puede ser interrumpido. 
T (Stopped/Traced): El proceso ha sido detenido o está siendo rastreado por un depurador.  

---

**23.** Suspende y reanuda **uno de tus procesos** (no crítico).

```bash
# Crea un proceso propio en segundo plano
sleep 120 &
pid=$!
# Suspende
kill -STOP "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
# Reanuda
kill -CONT "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
```
**Pega los dos estados (antes/después):**

```text
[1]+  Detenido                sleep 120
    PID STAT CMD
  60507 T    bash
    PID STAT CMD
  60507 S    sleep 120
```
**Pregunta:** ¿Qué flag indicó la suspensión?  

**Respuesta:** La S.

---

**24. (Opcional)** Genera un **zombie** controlado (no requiere root).

```bash
cat << 'EOF' > "$DAM/bin/zombie.c"
#include <stdlib.h>
#include <unistd.h>
int main() {
  if (fork() == 0) { exit(0); } // hijo termina
  sleep(60); // padre no hace wait(), hijo queda zombie hasta que padre termine
  return 0;
}
EOF
gcc "$DAM/bin/zombie.c" -o "$DAM/bin/zombie" && "$DAM/bin/zombie" &
ps -el | grep ' Z '
```
**Salida (recorta):**

```text
[2] 61057
```
**Pregunta:** ¿Por qué el estado `Z` y qué lo limpia finalmente?  

**Respuesta:**
Z (Zombie): Un proceso que ha terminado, pero su proceso padre no ha recogido su estado, por lo que sigue ocupando un espacio en la tabla de procesos.

---

### 2.5 — Limpieza (solo tu usuario)

Detén y deshabilita tu **timer/servicio de usuario** y borra artefactos si quieres.

```bash
systemctl --user disable --now fecha-log.timer
systemctl --user stop fecha-log.service
rm -f ~/.config/systemd/user/fecha-log.{service,timer}
systemctl --user daemon-reload
rm -rf "$DAM/bin" "$DAM/logs" "$DAM/units"
```

---

## ¿Qué estás prácticando?
- [ ] Pegaste **salidas reales**.  
- [ ] Explicaste **qué significan**.  
- [ ] Usaste **systemd --user** y **journalctl --user**.  
- [ ] Probaste `systemd-run --user` con límites de memoria.  
- [ ] Practicaste señales (`STOP`/`CONT`), `pstree`, `ps` y `strace` **sobre tus procesos**.

---

## Licencia 📄
Licencia **Apache 2.0** — ver [LICENSE.md](https://github.com/jpexposito/code-learn-practice/blob/main/LICENSE).
