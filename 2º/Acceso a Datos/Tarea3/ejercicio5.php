<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo Origen
$fileOrigen = fopen("Archivos/origen.txt", "w");
fwrite($fileOrigen, "Este es el archivo original");
fclose($fileOrigen);

// Crear archivo Copia
$fileCopia = fopen("Archivos/copia.txt", "w");
copy("Archivos/origen.txt", "Archivos/copia.txt");
fclose($fileCopia);

// Lectura
$file = fopen("Archivos/copia.txt", "r");
echo fread($file, filesize("Archivos/copia.txt"));
fclose($file);
?>