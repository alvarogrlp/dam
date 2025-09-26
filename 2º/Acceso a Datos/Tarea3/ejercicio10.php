<?php
// Crear directorio
if (!file_exists("ArchivosJson")) {
    mkdir("ArchivosJson");
}

$archivo = "ArchivosJson/datos.json";

// Leer el contenido existente
if (file_exists($archivo)) {
    $contenido = file_get_contents($archivo);
    $usuarios = json_decode($contenido, true);
} else {
    $usuarios = [];
}

// Crear el nuevo dato
$nuevoUsuario = [
    "nombre" => "Ana",
    "edad" => 25
];

// Añadir al array
$usuarios[] = $nuevoUsuario;

// Guardar en JSON
file_put_contents($archivo, json_encode($usuarios, JSON_PRETTY_PRINT));

echo file_get_contents($archivo);
?>
