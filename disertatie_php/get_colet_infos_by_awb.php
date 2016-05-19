<?php

/*
 * 
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

if (isset($_POST['awb'])) {

	$awb = $_POST['awb'];

    // get a user from userlog table
    $result = mysql_query("SELECT colet_id, colet_status FROM colet WHERE colet_awb=$awb");

	// check for empty result
	if (!empty($result) && mysql_num_rows($result) > 0) {

		$result = mysql_fetch_array($result);

		$colet = array();
		$colet["colet_id"] = $result["colet_id"];
		$colet["colet_status"] = $result["colet_status"];
		// success
		$response["success"] = 1;
		$response["message"] = "Preluare date reusita";

		// colet node
		$response["colet"] = array();

		array_push($response["colet"], $colet);

		// echoing JSON response
		echo json_encode($response);
	} else {
		// no product found
		$response["success"] = 0;
		$response["message"] = "Preluare date esuata";

		// echo no users JSON
		echo json_encode($response);
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Lipsa AWB";

    // echoing JSON response
    echo json_encode($response);
}

?>
