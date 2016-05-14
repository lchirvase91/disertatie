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

if (isset($_POST['userlog_username']) && isset($_POST['userlog_password']) && !empty($_POST['userlog_username']) && !empty($_POST['userlog_password'])) {

	$username = $_POST['userlog_username'];
	$psw = $_POST['userlog_password'];

    // get a user from userlog table
    $result = mysql_query("SELECT * FROM user where user_userlog_id = (select userlog_id FROM userlog WHERE userlog_username = '$username' AND userlog_password = '$psw')");

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
			$response["message"] = "Autentificare reusita!";

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
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Completati toate campurile!";

    // echoing JSON response
    echo json_encode($response);
}

?>
