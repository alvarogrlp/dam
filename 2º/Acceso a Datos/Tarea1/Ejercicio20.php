<?php
$altura = 5;

for ($i=1; $i <= $altura; $i++) {
    echo str_repeat(" ", $altura - $i); 
    for ($j=1; $j <= $i; $j++) { 
        echo "**";
    }
    echo "\n";
}
?>