<?php
echo "¿Cómo te llamas?";
$nombre = trim(fgets(STDIN));

// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/usuarios.txt", "a");
fwrite($file, "$nombre \n");
fclose($file);

// Lectura
$file = fopen("Archivos/usuarios.txt", "r");
echo fread($file, filesize("Archivos/usuarios.txt"));
fclose($file);
?>