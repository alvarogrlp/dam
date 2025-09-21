<?php
$altura = 5;
$repeticiones = 3;

$array1 = [];
$resta = ($altura * 2) - 2;

for ($i = 1; $i <= $altura; $i++) {

    for ($j=1; $j <= $repeticiones; $j++) { 
        if ($j % 2== 0) {
            array_push($array1, str_repeat(" ", $resta));
            array_push($array1, str_repeat("*", $i));
        }
        else {
            array_push($array1, str_repeat("*", $i));
        }
    }

    $resta = $resta - 2;
    if($resta < 0) {
        $resta = 0;
    }

    array_push($array1, "\n");
}

echo implode ($array1);
?>