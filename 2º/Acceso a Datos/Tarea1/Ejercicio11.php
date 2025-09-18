<?php

for ($i = 2; $i <= 50; $i++) {
    $esPrimo = true;

    for ($j = 2; $j < $i; $j++) {
        if ($i % $j == 0) {
            $esPrimo = false;
            break;
        }
    }

    if ($esPrimo) {
        echo "$i \n";
    }
}
?>