<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/datos.txt", "w");
fwrite($file, "Hola mundo desde PHP");
fclose($file);

// Lectura
$file = fopen("Archivos/datos.txt", "r");
echo fread($file, filesize("Archivos/datos.txt"));
fclose($file);
?>