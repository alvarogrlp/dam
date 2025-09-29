<?php

$archivo = fopen("texto.txt", "r");
fread($archivo, filesize("texto.txt"));
$contenidoTxt = file_get_contents("texto.txt");
$totalPalabras = str_word_count($contenidoTxt);
fclose($archivo);

$arrayEstadistica = ["palabra,frecuencia \n"];
$arrayEstadistica[] = "palabras,total=$totalPalabras \n";

$texto = strtolower($contenidoTxt);
preg_match_all('/\p{L}+/u', $texto, $coincidencias);
$frecuencias = array_count_values($coincidencias[0]);

foreach ($frecuencias as $palabra => $cantidad) {
    $arrayEstadistica[] = "$palabra,$cantidad \n";
}

$archivoCSV = fopen("estadisticas.csv", "w");
foreach ($arrayEstadistica as $fila) {
    fwrite($archivoCSV, $fila);
}
fclose($archivoCSV);

?>