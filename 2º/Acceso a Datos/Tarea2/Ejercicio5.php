<?php
declare(strict_types=1);
//función que devuelve la secuencia collatz de un número indicado
function secuenciaCollatz(int $n){
    $secuencia = [];

    while ($n > 1) {
        if ($n % 2 === 0) {
            $n /= 2;
            $secuencia[] = $n;
        } else {
            $n = $n * 3 + 1;
            $secuencia[] = $n;
        }
    }

    return $secuencia;
}

echo implode(' → ', secuenciaCollatz(6));
?>