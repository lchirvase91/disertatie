<?php

/*
 * 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id_livrare']) && isset($_POST['datetime_livrare'])) {
    
    $id = $_POST['id_livrare'];
	$data_livrare = $_POST['datetime_livrare'];
    
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE comanda,colet SET colet_status = 'livrat', comanda_data_livrare = '$data_livrare' WHERE comanda_id = colet_comanda_id AND colet_comanda_id = $id");
	

    // check if row inserted or not
    if ($result) {
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
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Lipsa informatii";

    // echoing JSON response
    echo json_encode($response);
}
?>
