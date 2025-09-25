<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/datos_numericos.txt", "w");
fwrite($file, "10,20,30,40");
$numeroArray = explode(",", file_get_contents("Archivos/datos_numericos.txt"));
$suma = array_sum($numeroArray);
fclose($file);

echo $suma;
?>