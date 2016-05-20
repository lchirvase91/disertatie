<?php

/*
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id_livrare']) && isset($_POST['datetime_livrare'])) {
    
    $id_livrare = $_POST['id_livrare'];
	$data_livrare = $_POST['datetime_livrare'];
    
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

	// mysql inserting a new row
	$insert = mysql_query("INSERT INTO operare(operare_user_id, operare_colet_id, operare_data) SELECT comanda_asignare, colet_id, NOW() from colet, comanda where colet_comanda_id = comanda_id and comanda_id = $id_livrare");
	
	if ($insert) {
		// mysql update row with matched pid
		$update = mysql_query("UPDATE comanda,colet SET colet_status = 'livrat', comanda_data_livrare = '$data_livrare' WHERE comanda_id = colet_comanda_id AND colet_comanda_id = $id_livrare");
	
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
