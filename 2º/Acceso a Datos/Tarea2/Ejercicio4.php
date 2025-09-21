<?php
declare(strict_types=1);
//Función que devuelve los múltiplos de 3 y 5 que sean menores al número indicado
function multiplosTresOConcio(int $n) {
    $resultado = [];

    for ($i=0; $i < $n; $i++) { 
        if ($i % 3 === 0 || $i % 5 === 0) {
            $resultado[] = $i;
        }
    }
    return $resultado;
}

echo implode(" ,", multiplosTresOConcio(10));

echo "\n" . array_sum(multiplosTresOConcio(10));
?>