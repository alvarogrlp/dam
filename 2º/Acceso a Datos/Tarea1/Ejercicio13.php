<?php

echo "Introduce un número: \n";
$numero = trim(fgets(STDIN));

for ($i=1; $i < 100; $i++) { 
    $multiplo = $numero * $i;
    echo "$multiplo\n";
}
?>