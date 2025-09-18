<?php
echo"Introduce un número: \n";
$numero = trim(fgets(STDIN));
$suma = 0;

for ($i=1; $i < $numero; $i++) { 
    if ($numero % $i == 0) {
        $suma = $suma + $i;
    }
}

if ($suma == $numero) {
    echo "El número $numero es un número perfecto\n";
} else {
    echo "El número $numero no es un número perfecto\n";
}

?>