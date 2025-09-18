<?php
echo "Por favor, ingresa el primer dato: ";
$input1 = trim(fgets(STDIN));

echo "Por favor, ingresa el segundo dato: ";
$input2 = trim(fgets(STDIN));

if ($input1 > $input2) {
    echo "El primer dato es mayor que el segundo";
} else {
    echo "El segundo dato es mayor que el primero";
}
?>