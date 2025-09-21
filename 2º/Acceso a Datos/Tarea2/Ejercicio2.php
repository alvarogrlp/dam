<?php
declare(strict_types=1);
//Función que imprime una montaña de asteriscos repetida la cantidad de veces deseadas
function asteriscos(int $altura, int $repeticiones) {
    $array1 = [];
    $resta = ($altura * 2) - 2;

    for ($i = 1; $i <= $altura; $i++) {
    
        for ($j=1; $j <= $repeticiones; $j++) { 
            if ($j % 2== 0) {
                array_push($array1, str_repeat(" ", $resta));
            }
            array_push($array1, str_repeat("*", $i));
        }
    
        $resta = $resta - 2;
        if($resta < 0) {
            $resta = 0;
        }
    
        array_push($array1, "\n");
    }
    return $array1;
}

echo implode (asteriscos(4,3));
?>