<?php
$archivo = fopen("ops.csv", "r");

fread($archivo, filesize("ops.csv"));
$contenido = file_get_contents("ops.csv");
fclose($archivo);

$arrayOriginal = explode("\n", $contenido);

$archivo = fopen("resultado.csv", "w");
for ($i = 1; $i < sizeof($arrayOriginal); $i++) {
    $fila = trim($arrayOriginal[$i]);
    $n1 = 0;
    $n2 = 0;
    $arrayTemporal = explode(",", $fila);
    foreach ($arrayTemporal as $dato) {
        fwrite($archivo, "$dato,");
        if (!ctype_digit($dato)) {
            $resultado = detectarOperacion($dato, $n1, $n2);
            fwrite($archivo, $resultado . "\n");
        }
        $n1 = $n2;
        $n2 = $dato;
    }
}
fclose($archivo);

//Funcion que detecta el tipo de operador en el documento;
function detectarOperacion ($operador, $numero1, $numero2) {
    $resultado = 0;
     switch ($operador) {
        case 'resta':
            $resultado = $numero1 - $numero2;
            break;
        case 'producto':
            $resultado = $numero1 * $numero2;
            break;
        case 'division':
            if ($numero2 == 0) {
                $resultado = "ERROR";
                break;
            }
            $resultado = $numero1 / $numero2;
            break;
        default:
            $resultado = $numero1 + $numero2;
            break;
     }
    return $resultado;
}
?>