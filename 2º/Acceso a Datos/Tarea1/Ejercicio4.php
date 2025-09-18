<?php
echo "¿Cuál es tu nota? ";
$nota = trim(fgets(STDIN));
$nota = floatval($nota);

if ($nota >= 5 && $nota <= 6) {
    echo "Aprobado\n";
} elseif ($nota >= 7 && $nota <= 8) {
    echo "Notable\n";
} elseif ($nota >= 9 && $nota <= 10) {
    echo "Sobresaliente\n";
} else {
    echo "Suspenso\n";
}
?>