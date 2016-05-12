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

if (isset($_POST['user_id']) && isset($_POST['userlog_old_password']) && isset($_POST['userlog_new_password']) && !empty($_POST['user_id']) && !empty($_POST['userlog_old_password']) && !empty($_POST['userlog_new_password'])) {

	$id = $_POST['user_id'];
	$old_password = $_POST['userlog_old_password'];
	$new_password = $_POST['userlog_new_password'];

	$result1 = mysql_query("SELECT  FROM userlog WHERE userlog_password='$old_password' AND userlog_id = (SELECT user_userlog_id FROM user WHERE user_id = $id))");
	
	if ($result1) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Password successfully updated.";
        
		// update password for user with id $id
		$result2 = mysql_query("UPDATE userlog set userlog_password='$password' WHERE userlog_id = (SELECT user_userlog_id FROM user WHERE user_id = $id)");

		if ($result) {
			// successfully updated
			$response["success"] = 1;
			$response["message"] = "Password successfully updated.";
			
			// echoing JSON response
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "Password update failed.";
			
			// echoing JSON response
			echo json_encode($response);
		}
    } else {
        $response["success"] = 0;
        $response["message"] = "Wrong old password.";
		
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
