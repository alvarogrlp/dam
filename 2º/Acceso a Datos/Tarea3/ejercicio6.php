<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo frase
$fileFrase = fopen("Archivos/frase.txt", "w");
fwrite($fileFrase, "Hola PHP");
fclose($fileFrase);

// Crear archivo frase
$fileInvertido = fopen("Archivos/frase_invertida.txt", "w");
$contenidoInvertido = strrev(file_get_contents("Archivos/frase.txt"));
fwrite($fileInvertido, $contenidoInvertido);
fclose($fileInvertido);


// Lectura
$file = fopen("Archivos/frase_invertida.txt", "r");
echo fread($file, filesize("Archivos/frase_invertida.txt"));
fclose($file);
?>