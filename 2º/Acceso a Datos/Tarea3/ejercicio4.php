<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}
$arrayNombres = ["Ana", "Luis", "Marta", "Carlos", "Julia"];

// Crear archivo
$file = fopen("Archivos/nombres.txt", "w");
foreach ($arrayNombres as $nombre) {
    fwrite($file, "$nombre \n");
}
fclose($file);

// Lectura
$file = fopen("Archivos/nombres.txt", "r");
echo fread($file, filesize("Archivos/nombres.txt"));
fclose($file);
?>