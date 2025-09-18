<?php
echo "¿Cuál es tu edad?";
$edad = trim(fgets(STDIN));

if ($edad >= 18) {
    echo "Eres mayor de edad";
} else {
    echo "Eres menor de edad";
}
?>