<?php
echo "Nombre del juego: ";
$juego = trim(fgets(STDIN));

echo "Puntuación: ";
$puntuacion = (int) trim(fgets(STDIN));

// Crear directorio
if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivo = "Archivos/ranking.txt";

// Crear archivo
$file = fopen($archivo, "a");
fwrite($file, "$juego|$puntuacion" . PHP_EOL);
fclose($file);

// Lectura
$datos = [];
$file = fopen($archivo, "r");
while (!feof($file)) {
    $linea = trim(fgets($file));
    if ($linea !== "") {
        list($j, $p) = explode("|", $linea);
        $datos[] = ["juego" => $j, "puntuacion" => (int)$p];
    }
}
fclose($file);

// Ordenar
usort($datos, function($a, $b) {
    return $b["puntuacion"] <=> $a["puntuacion"];
});

echo PHP_EOL . "===== TOP 3 =====" . PHP_EOL;
for ($i = 0; $i < min(3, count($datos)); $i++) {
    echo ($i+1) . ". " . $datos[$i]["juego"] . " - " . $datos[$i]["puntuacion"] . PHP_EOL;
}
?>
