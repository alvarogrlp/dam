<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$archivo = "Archivos/chistes.txt";
if (!file_exists($archivo) || filesize($archivo) === 0) {
    $file = fopen($archivo, "w");
    fwrite($file, "¿Por qué el libro de matemáticas estaba triste? Porque tenía demasiados problemas.\n");
    fwrite($file, "Oye, ¿cuál es tu plato favorito y por qué? Pues el hondo, porque cabe más comida.\n");
    fwrite($file, "¿Qué le dice una impresora a otra? ¿Esa hoja es tuya o es una impresión mía?\n");
    fwrite($file, "¿Por qué estaba feliz la escoba? Porque ba-ría bien.\n");
    fclose($file);
}

// Lectura
$file = fopen($archivo, "r");
$contenido = fread($file, filesize($archivo));
fclose($file);

$chistes = array_filter(array_map("trim", explode("\n", $contenido)));

if (!empty($chistes)) {
    $indice = array_rand($chistes);
    $chiste = $chistes[$indice];
    echo $chiste . PHP_EOL;

    unset($chistes[$indice]);

    $file = fopen($archivo, "w");
    foreach ($chistes as $c) {
        fwrite($file, $c . PHP_EOL);
    }
    fclose($file);
} else {
    echo "No quedan chistes en el archivo." . PHP_EOL;
}
?>
