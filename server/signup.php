<?php

if(isset($_POST["fullname"])){
  
  require_once "dbconnect.php";

        global $db;

        $query = $db->prepare("INSERT INTO users (id, fullname, username, password, email) VALUES ('', :fullname, :username, :password, :email)");
        $query->bindValue(":fullname", $_POST["fullname"], PDO::PARAM_STR);
        $query->bindValue(":username", $_POST["username"], PDO::PARAM_STR);
        $query->bindValue(":password", password_hash($_POST["password"], PASSWORD_DEFAULT), PDO::PARAM_STR);
        $query->bindValue(":email", $_POST["email"], PDO::PARAM_STR);
        $query->execute();
        echo "Sign Up Success";
}
