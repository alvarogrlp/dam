<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$archivo = "Archivos/excusas.txt";
if (!file_exists($archivo) || filesize($archivo) === 0) {
    $file = fopen($archivo, "w");
    fwrite($file, "No hice la tarea porque se me olvidó.\n");
    fwrite($file, "Llegué tarde porque había tráfico.\n");
    fwrite($file, "No fui porque estaba malo.\n");
    fwrite($file, "Se me apagó el ordenador justo antes.\n");
    fwrite($file, "Profe ponme un 10.\n");
    fclose($file);
}

// Lectura
$file = fopen($archivo, "r");
$contenido = fread($file, filesize($archivo));
fclose($file);

$excusas = array_filter(array_map("trim", explode("\n", $contenido)));

if (!empty($excusas)) {
    $aleatoria = $excusas[array_rand($excusas)];
    echo "Excusa: $aleatoria" . PHP_EOL;
} else {
    echo "No hay excusas en el archivo." . PHP_EOL;
}
?>
