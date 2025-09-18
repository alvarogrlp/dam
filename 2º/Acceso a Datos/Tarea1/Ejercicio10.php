<?php

echo "Introduce un número: \n";
$numero = trim(fgets(STDIN));

$factorial = 1;

for ($i = $numero; $i > 0; $i--) {
    $factorial = $factorial * $i;
}

echo "$factorial\n";
?>