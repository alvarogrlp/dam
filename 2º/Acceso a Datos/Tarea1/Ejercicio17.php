<?php
$numero = trim(fgets(STDIN));
$numeros = str_split($numero);
$numerosTamanio = count($numeros);

for ($i=$numerosTamanio; $i >= 0; $i--) { 
    echo "$numeros[$i]\n";
}
?>