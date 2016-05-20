<?php

/*
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id_preluare']) && isset($_POST['datetime_preluare'])) {
    
    $id_preluare = $_POST['id_preluare'];
	$data_preluare = $_POST['datetime_preluare'];
    
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
		
	// mysql inserting a new row
	$insert = mysql_query("INSERT INTO operare(operare_user_id, operare_colet_id, operare_data) SELECT comanda_asignare, colet_id, NOW() from colet, comanda where colet_comanda_id = comanda_id and comanda_id = $id_preluare");

	if ($insert) {
		// mysql update row with matched pid
		$update = mysql_query("UPDATE comanda,colet SET colet_status = 'preluat', comanda_data_preluare = '$data_preluare' WHERE comanda_id = colet_comanda_id AND colet_comanda_id = $id_preluare");
		
		// check if row inserted or not
		if ($update) {
			// successfully updated
			$response["success"] = 1;
			$response["message"] = "Comanda actualizata";
			
			// echoing JSON response
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "Actualizare nereusita";
		}
	} else {
			$response["success"] = 0;
			$response["message"] = "Actualizare nereusita";
	}
	 
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Lipsa informatii";

    // echoing JSON response
    echo json_encode($response);
}
?>
