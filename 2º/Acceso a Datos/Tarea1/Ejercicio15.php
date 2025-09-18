<?php

$numeroRandom = random_int(1, 20);

$intentos = 5;

for ($i=0; $i < $intentos; $i++) { 
    echo "Tienes $intentos intentos para adivinar el número (entre 1 y 20): \n";
    $intentos--;
    $numero = trim(fgets(STDIN));

    if ($numero == $numeroRandom) {
        echo "¡Has acertado!\n";
        break;
    }
    if ($numero > $numeroRandom) {
        echo "El número es menor\n";
        break;
    }
    echo "El número es mayor\n";
}
?>