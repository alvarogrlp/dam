<?php
declare(strict_types=1);
function sumar(int $num) {
    $suma = 0;
    $numeroArray = str_split($num, 1);
    foreach ($numeroArray as $numero) {
        $suma += $numero;
    }
    return $suma;
}

echo sumar(2025);
?>