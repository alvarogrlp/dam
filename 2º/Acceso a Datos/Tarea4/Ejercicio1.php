<?php
$entrada = "ops.csv";
$salida  = "resultado.csv";

if (!file_exists($entrada)) {
    fwrite(STDERR, "No se encontró el archivo $entrada\n");
    exit(1);
}

$fp_in  = fopen($entrada, "r");
$fp_out = fopen($salida, "w");

$sep = ",";   
$enc = '"';  
$esc = "\\";   

$cabecera = fgetcsv($fp_in, 0, $sep, $enc, $esc);
if ($cabecera === false) {
    fwrite(STDERR, "El CSV está vacío.\n");
    exit(1);
}
$cabecera[0] = ltrim($cabecera[0], "\xEF\xBB\xBF"); 
$cabecera[] = "resultado";
fputcsv($fp_out, $cabecera, $sep, $enc, $esc);

while (($fila = fgetcsv($fp_in, 0, $sep, $enc, $esc)) !== false) {
    if (count($fila) < 3) { 
        continue;
    }

    [$z1, $z2, $op] = $fila;
    $z1 = is_numeric($z1) ? $z1 + 0 : 0; 
    $z2 = is_numeric($z2) ? $z2 + 0 : 0;

    $op = trim($op);
    $resultado = "";

    switch ($op) {
        case "suma":
            $resultado = $z1 + $z2;
            break;
        case "resta":
            $resultado = $z1 - $z2;
            break;
        case "producto":
            $resultado = $z1 * $z2;
            break;
        case "division":
            $resultado = ($z2 == 0) ? "ERROR" : ($z1 / $z2);
            break;
        default:
            $resultado = "OP_NO_VALIDA";
    }

    $fila[] = $resultado;
    fputcsv($fp_out, $fila, $sep, $enc, $esc);
}

fclose($fp_in);
fclose($fp_out);

echo "Archivo $salida generado correctamente.\n";
