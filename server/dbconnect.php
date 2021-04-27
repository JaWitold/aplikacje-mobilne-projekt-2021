<?php
$data = ['login' => 'daoehremvz_test', 'password' => 'Qwerty123', 'host' => 's130.cyber-folks.pl', 'db_name' => 'daoehremvz_test'];


global $db;

try {
    $db = new PDO('mysql:host=s130.cyber-folks.pl; dbname=daoehremvz_test',
        "daoehremvz_test",
        'Qwerty123',
        [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, PDO::ATTR_EMULATE_PREPARES => false, PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"]);
    // echo "wszystko wporządku";
} catch (PDOException $e) {
    //exit($e->getMessage());
    exit("Błąd serwera");
}
