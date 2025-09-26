<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/tabla5.txt", "w");
for ($i=1; $i <= 10; $i++) { 
    $resultado = 5 * $i;
    fwrite($file, "5 x $i = $resultado \n");
}
fclose($file);

// Lectura
$file = fopen("Archivos/tabla5.txt", "r");
echo fread($file, filesize("Archivos/tabla5.txt"));
fclose($file);
?>