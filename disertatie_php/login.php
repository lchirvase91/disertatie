<?php

/*
 * Following code will get single user
 * An user is identified by username and password
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();



    // get a user from userlog table
    $result = mysql_query("SELECT * FROM user where user_userlog_id IN (select userlog_id FROM userlog WHERE userlog_username = 'lchirvase' AND userlog_password= 'test123')");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $user = array();
            $user["id"] = $result["user_id"];
            $user["nume"] = $result["user_nume"];
            $user["prenume"] = $result["user_prenume"];
            $user["telefon"] = $result["user_telefon"];
            $user["statut"] = $result["user_statut"];
            // success
            $response["success"] = 1;

            // user node
            $response["user"] = array();

            array_push($response["user"], $user);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "Autentificare esuata!";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "Autentificare esuata!";

        // echo no users JSON
        echo json_encode($response);
    }

?>