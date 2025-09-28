<?php

$entrada = "texto.txt";
$salida  = "estadisticas.csv";

if (!file_exists($entrada)) {
    fwrite(STDERR, "No se encontró el archivo $entrada\n");
    exit(1);
}

$texto = file_get_contents($entrada);

$texto = strtolower($texto);

$texto = preg_replace('/[^\p{L}\p{N}\s]+/u', ' ', $texto);

$palabras = preg_split('/\s+/', $texto, -1, PREG_SPLIT_NO_EMPTY);

$frecuencias = array_count_values($palabras);

ksort($frecuencias, SORT_STRING);

$fp_out = fopen($salida, "w");

$sep = ",";
$enc = '"';
$esc = "\\";

fputcsv($fp_out, ["palabra", "frecuencia"], $sep, $enc, $esc);

foreach ($frecuencias as $palabra => $count) {
    fputcsv($fp_out, [$palabra, $count], $sep, $enc, $esc);
}

fclose($fp_out);

echo "Archivo $salida generado correctamente.\n";
