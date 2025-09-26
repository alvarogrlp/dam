<?php
echo "Escribe tu entrada para el diario:" . PHP_EOL;
$entrada = trim(fgets(STDIN));

// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}
$archivo = "Archivos/diario.txt";

// Crear archivo
$file = fopen($archivo, "a");
$fecha = date("Y-m-d H:i:s");
fwrite($file, "[$fecha] $entrada" . PHP_EOL);
fclose($file);

// Lectura
$file = fopen($archivo, "r");
echo PHP_EOL . "===== Diario =====" . PHP_EOL;
echo fread($file, filesize($archivo));
fclose($file);
?>
