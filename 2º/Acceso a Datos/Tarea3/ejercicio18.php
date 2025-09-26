<?php
echo "Nombre de usuario: ";
$usuario = trim(fgets(STDIN));

echo "Comida elegida: ";
$comida = trim(fgets(STDIN));

if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivo = "Archivos/comidas.txt";
$file = fopen($archivo, "a");
fwrite($file, $usuario . "|" . $comida . PHP_EOL);
fclose($file);

$respuestas = [];
if (file_exists($archivo) && filesize($archivo) > 0) {
    $file = fopen($archivo, "r");
    while (!feof($file)) {
        $linea = trim(fgets($file));
        if ($linea === "") continue;

        $partes = explode("|", $linea);
        if (count($partes) === 2) {
            list($u, $c) = $partes;
            $respuestas[] = ["usuario" => $u, "comida" => $c];
        }
    }
    fclose($file);
}

$ranking = [];
foreach ($respuestas as $r) {
    $c = strtolower(trim($r["comida"]));
    if ($c === "") continue;
    if (!isset($ranking[$c])) $ranking[$c] = 0;
    $ranking[$c]++;
}

arsort($ranking);

echo PHP_EOL . "===== RANKING DE COMIDAS =====" . PHP_EOL;
$pos = 1;
foreach ($ranking as $comidaNom => $votos) {
    echo "{$pos}. " . ucfirst($comidaNom) . " - {$votos} voto" . ($votos !== 1 ? "s" : "") . PHP_EOL;
    $pos++;
}

echo PHP_EOL . "===== RESPUESTAS_"
?>