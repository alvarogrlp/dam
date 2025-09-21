<?php
declare(strict_types=1);
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