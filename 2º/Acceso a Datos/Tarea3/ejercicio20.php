<?php
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivoPlantilla = "Archivos/plantilla.txt";
$archivoAdj = "Archivos/adjetivos.txt";
$archivoAni = "Archivos/animales.txt";
$archivoLug = "Archivos/lugares.txt";

if (!file_exists($archivoPlantilla) || filesize($archivoPlantilla) === 0) {
    $file = fopen($archivoPlantilla, "w");
    fwrite($file, "Había una vez un {adjetivo} {animal} que vivía en un {lugar}.\n");
    fclose($file);
}

if (!file_exists($archivoAdj) || filesize($archivoAdj) === 0) {
    $file = fopen($archivoAdj, "w");
    fwrite($file, "rápido\nfeliz\nfuerte\ngigante\npequeño\n");
    fclose($file);
}

if (!file_exists($archivoAni) || filesize($archivoAni) === 0) {
    $file = fopen($archivoAni, "w");
    fwrite($file, "león\nperro\ngato\nelefante\nratón\n");
    fclose($file);
}

if (!file_exists($archivoLug) || filesize($archivoLug) === 0) {
    $file = fopen($archivoLug, "w");
    fwrite($file, "bosque\nciudad\ncastillo\nplaya\nmontaña\n");
    fclose($file);
}

function palabraAleatoria($ruta) {
    $lineas = file($ruta, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
    return $lineas[array_rand($lineas)];
}

$plantilla = file_get_contents($archivoPlantilla);

$plantilla = str_replace("{adjetivo}", palabraAleatoria($archivoAdj), $plantilla);
$plantilla = str_replace("{animal}", palabraAleatoria($archivoAni), $plantilla);
$plantilla = str_replace("{lugar}", palabraAleatoria($archivoLug), $plantilla);

echo "Resultado generado:\n$plantilla";
?>
