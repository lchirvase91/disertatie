<?php

/*
 * 
 * 
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

if (isset($_POST['id']) && isset($_POST['old_password']) && isset($_POST['new_password']) && !empty($_POST['id']) && !empty($_POST['old_password']) && !empty($_POST['new_password'])) {

	$id = $_POST['id'];
	$old_password = $_POST['old_password'];
	$new_password = $_POST['new_password'];

	$result1 = mysql_query("SELECT * FROM userlog WHERE userlog_password = '$old_password' AND userlog_id = (SELECT user_userlog_id FROM user WHERE user_id = $id)");
	
	if (!empty($result1) && mysql_num_rows($result1) > 0) {
		
		// update password for user with id $id
		$result2 = mysql_query("UPDATE userlog set userlog_password='$new_password' WHERE userlog_id = (SELECT user_userlog_id FROM user WHERE user_id = $id)");

		if ($result2) {
			// successfully updated
			$response["success"] = 1;
			$response["message"] = "Actualizare parola reusita.";
			
			// echoing JSON response
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "Actualizare parola esuata.";
			
			// echoing JSON response
			echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "Parola veche gresita.";
		
		// echoing JSON response
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
