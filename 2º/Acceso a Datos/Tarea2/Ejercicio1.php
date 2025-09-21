<?php
declare(strict_types=1);
$numero = trim(fgets(STDIN));
//Función para comprobar si un número es capicúa
function esCapicua($num) {
    $strNumero = strval($num);
    $strRevertido = strrev($strNumero);
    return $strNumero === $strRevertido;
}
?>