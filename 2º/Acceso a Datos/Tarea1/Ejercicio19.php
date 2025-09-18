<?php
echo "Introduce el primer número: \n";
$primerNumero = (int)trim(fgets(STDIN));

echo "Introduce el segundo número: \n";
$segundoNumero = (int)trim(fgets(STDIN));

while ($segundoNumero != 0) {
    $resto = $primerNumero % $segundoNumero;
    $primerNumero = $segundoNumero;
    $segundoNumero = $resto;
}

echo "El MCD de los números introducidos es: $primerNumero\n";
?>