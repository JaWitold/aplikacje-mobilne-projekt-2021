<?php

if(isset($_POST["username"])){
  
  require_once "dbconnect.php";

        global $db;

        $query = $db->prepare("SELECT * FROM users WHERE username = :login");
        $query->bindValue(":login", $_POST["username"], PDO::PARAM_STR);
        $query->execute();

        if ($query->rowCount() == 1) {
            $result = $query->fetchAll(PDO::FETCH_ASSOC);

            $result = $result[0];
            if (password_verify($_POST["password"], $result["password"])) {
                echo "logged";
            }
        }
}
