<?php
// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

// Crear archivo
$archivo = "Archivos/palabras.txt";
if (!file_exists($archivo) || filesize($archivo) === 0) {
    $file = fopen($archivo, "w");
    fwrite($file, "programacion\n");
    fwrite($file, "computadora\n");
    fwrite($file, "teclado\n");
    fwrite($file, "internet\n");
    fwrite($file, "pantalla\n");
    fclose($file);
}

// Lectura
$file = fopen($archivo, "r");
$contenido = fread($file, filesize($archivo));
fclose($file);

$palabras = array_filter(array_map("trim", explode("\n", $contenido)));

$palabra = $palabras[array_rand($palabras)];
$longitud = strlen($palabra);

$pos1 = rand(0, $longitud - 1);
do {
    $pos2 = rand(0, $longitud - 1);
} while ($pos2 === $pos1);

$pista = "";
for ($i = 0; $i < $longitud; $i++) {
    if ($i === $pos1 || $i === $pos2) {
        $pista .= $palabra[$i];
    } else {
        $pista .= "_";
    }
}

echo "Adivina la palabra: $pista" . PHP_EOL;
echo "Tu respuesta: ";
$respuesta = trim(fgets(STDIN));

if (strtolower($respuesta) === strtolower($palabra)) {
    echo "Correcto, La palabra era: $palabra" . PHP_EOL;
} else {
    echo "Incorrecto, La palabra era: $palabra" . PHP_EOL;
}
?>
