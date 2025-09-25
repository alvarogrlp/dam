<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/numeros.txt", "w");
for ($i=1; $i <= 10; $i++) { 
    fwrite($file, "$i \n");
}
fclose($file);

// Lectura
$file = fopen("Archivos/numeros.txt", "r");
echo fread($file, filesize("Archivos/numeros.txt"));
fclose($file);
?>