<?php
$texto = trim(fgets(STDIN));
$textos = str_split($texto);
$textosTamanio = count($textos);

$palindromo = true;

for ($i=0; $i < $textosTamanio; $i++) { 

    for ($j= $textosTamanio - 1; $j >= 0; $j--) {

        if ($textos[$j] != $texto[$i]) {
            $palindromo = false;
            break;
        }

    }

    if (!$palindromo) {
        break;
    }
}

if ($palindromo) {
    echo "Es un palíndromo\n";
} else {
    echo "No es un palíndromo\n";
}
?>