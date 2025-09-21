<?php
$numero = trim(fgets(STDIN));
function esCapicua($num) {
    $strNumero = strval($num);
    $strRevertido = strrev($strNumero);
    return $strNumero === $strRevertido;
}
?>