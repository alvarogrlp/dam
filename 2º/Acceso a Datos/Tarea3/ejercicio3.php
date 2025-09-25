<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$file = fopen("Archivos/texto.txt", "w");
fwrite($file, "PHP es muy divertido y potente.");
fclose($file);

// Lectura
$file = fopen("Archivos/texto.txt", "r");
$contenido = file_get_contents("Archivos/texto.txt");
$contadorDePalabras = str_word_count($contenido);
fclose($file);

echo $contadorDePalabras;
?>