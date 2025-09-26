<?php
echo "Escribe tu tweet: ";
$tweet = trim(fgets(STDIN));

if (!file_exists("Archivos")) {
    mkdir("Archivos");
}

$archivo = "Archivos/tweets.txt";

$fecha = date("Y-m-d H:i:s");
$file = fopen($archivo, "a");
fwrite($file, "[$fecha] $tweet" . PHP_EOL);
fclose($file);

$tweets = [];
if (file_exists($archivo) && filesize($archivo) > 0) {
    $file = fopen($archivo, "r");
    while (!feof($file)) {
        $linea = trim(fgets($file));
        if ($linea !== "") {
            $tweets[] = $linea;
        }
    }
    fclose($file);
}

echo PHP_EOL . "===== ÚLTIMOS 5 TWEETS =====" . PHP_EOL;
$ultimos = array_slice($tweets, -5); 
foreach ($ultimos as $t) {
    echo $t . PHP_EOL;
}
?>
