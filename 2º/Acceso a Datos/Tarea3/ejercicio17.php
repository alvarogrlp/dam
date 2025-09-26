<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivoAdj = "Archivos/adjetivos.txt";
$archivoAni = "Archivos/animales.txt";

// Crear archivo
if (!file_exists($archivoAdj) || filesize($archivoAdj) === 0) {
    $file = fopen($archivoAdj, "w");
    fwrite($file, "rápido\n");
    fwrite($file, "feliz\n");
    fwrite($file, "fuerte\n");
    fwrite($file, "pequeño\n");
    fwrite($file, "gigante\n");
    fclose($file);
}

if (!file_exists($archivoAni) || filesize($archivoAni) === 0) {
    $file = fopen($archivoAni, "w");
    fwrite($file, "león\n");
    fwrite($file, "perro\n");
    fwrite($file, "gato\n");
    fwrite($file, "elefante\n");
    fwrite($file, "ratón\n");
    fclose($file);
}

//Lectura
$file = fopen($archivoAdj, "r");
$contenidoAdj = fread($file, filesize($archivoAdj));
fclose($file);
$adjetivos = array_filter(array_map("trim", explode("\n", $contenidoAdj)));

$file = fopen($archivoAni, "r");
$contenidoAni = fread($file, filesize($archivoAni));
fclose($file);
$animales = array_filter(array_map("trim", explode("\n", $contenidoAni)));

$adj = $adjetivos[array_rand($adjetivos)];
$ani = $animales[array_rand($animales)];

echo "🐾 Resultado: $adj $ani" . PHP_EOL;
?>
