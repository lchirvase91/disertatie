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

// get all comenzi (preluare) from comenda table
$result = mysql_query("SELECT DISTINCT comanda_id, client_nume, client_judet, client_localitate, client_adresa, client_telefon, comanda_nr_colete, comanda_greutate, comanda_data_comanda FROM client, comanda, colet WHERE client_id = comanda_exp_id AND comanda_id = colet_comanda_id  AND colet_awb IS NULL ORDER BY comanda_data_comanda") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["comenzi"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $comanda = array();
        $comanda["comanda_id"] = $row["comanda_id"];
        $comanda["client_nume"] = $row["client_nume"];
        $comanda["client_judet"] = $row["client_judet"];
        $comanda["client_localitate"] = $row["client_localitate"];
        $comanda["client_adresa"] = $row["client_adresa"];
        $comanda["client_telefon"] = $row["client_telefon"];
		$comanda["nr_colete"] = $row["comanda_nr_colete"];
        $comanda["greutate"] = $row["comanda_greutate"];
        $comanda["data_comanda"] = $row["comanda_data_comanda"];

        // push single product into final response array
        array_push($response["comenzi"], $comanda);
    }
    // success
    $response["success"] = 1;
    $response["message"] = "Lista comenzi";

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No data found";

    // echo no users JSON
    echo json_encode($response);
}
?>
