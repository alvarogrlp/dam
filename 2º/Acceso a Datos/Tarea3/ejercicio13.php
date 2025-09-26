<?php
echo "Título de la canción: ";
$titulo = trim(fgets(STDIN));

// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivo = "Archivos/canciones.txt";

// Crear archivo
$file = fopen($archivo, "a");
fwrite($file, $titulo . PHP_EOL);
fclose($file);

// Lectura
$canciones = file($archivo, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

// Elegir al azar
if (count($canciones) > 0) {
    $aleatoria = $canciones[array_rand($canciones)];
    echo PHP_EOL . "🎶 Canción aleatoria: $aleatoria" . PHP_EOL;
} else {
    echo "No hay canciones en la lista." . PHP_EOL;
}
?>
