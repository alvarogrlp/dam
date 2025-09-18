<?php
echo "Introduce un número \n";
$numero = trim(fgets(STDIN));

for ($i=1; $i <= 10; $i++) { 
    $resultado = $numero * $i;

    echo "$numero * $i = $resultado\n";
}
?>