<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id_user']) && isset($_POST['id_colet']) && isset($_POST['data_operare'])) {
    
    $user_id = $_POST['id_user'];
	$colet_id = $_POST['id_colet'];
    $data_operare = $_POST['data_operare'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $insert = mysql_query("INSERT INTO operare VALUES(NULL, '$user_id', '$colet_id', '$data_operare')");

    // check if row inserted or not
    if ($insert) {
         // mysql update row with matched pid
		$update1 = mysql_query("UPDATE colet, comanda, client, operare, user, hub SET colet_status = 'livrat' WHERE colet_comanda_id = comanda_id AND comanda_dest_id = client_id AND colet_id = operare_colet_id AND operare_user_id = user_id AND user_hub_id = hub_id AND hub_judet = client_judet AND colet_id = 1 AND colet_status = 'in curs de livrare'");
		$update2 = mysql_query("UPDATE colet, comanda, client, operare, user, hub SET colet_status = 'in curs de livrare' WHERE colet_comanda_id = comanda_id AND comanda_dest_id = client_id AND colet_id = operare_colet_id AND operare_user_id = user_id AND user_hub_id = hub_id AND hub_judet = client_judet AND colet_id = $colet_id AND colet_status = 'in tranzit'");
		$update3 = mysql_query("UPDATE colet, comanda, client exp, client dest, operare, user, hub set colet_status='in tranzit' where colet_comanda_id = comanda_id AND comanda_dest_id = dest.client_id AND comanda_exp_id = exp.client_id AND colet_id = operare_colet_id AND operare_user_id = user_id AND user_hub_id = hub_id AND hub_judet <> exp.client_judet AND hub_judet <> dest.client_judet AND colet_id = $colet_id AND colet_status = 'procesat'");

		// check if row inserted or not
		if ($update1 || $update2 || $update3) {
			// successfully updated
			$response["success1"] = 1;
			$response["message1"] = "Actualizare reusita";
			
			$result1 = mysql_query("SELECT COUNT(colet_id) FROM colet, comanda WHERE comanda_id = colet_comanda_id GROUP BY comanda_id HAVING comanda_id = (SELECT colet_comanda_id FROM colet WHERE colet_id = $colet_id) AND COUNT(colet_id) = (SELECT COUNT(*) FROM colet WHERE colet_comanda_id = (SELECT colet_comanda_id FROM colet WHERE colet_id = $colet_id) AND colet_status='in tranzit')");
			$result2 = mysql_query("SELECT COUNT(colet_id) FROM colet, comanda WHERE comanda_id = colet_comanda_id GROUP BY comanda_id HAVING comanda_id = (SELECT colet_comanda_id FROM colet WHERE colet_id = $colet_id) AND COUNT(colet_id) = (SELECT COUNT(*) FROM colet WHERE colet_comanda_id = (SELECT colet_comanda_id FROM colet WHERE colet_id = $colet_id) AND colet_status='livrat')");
				
			if ($update3 && !empty($result1) && mysql_num_rows($result1) > 0) {
				$update4 = mysql_query("UPDATE comanda SET comanda_data_expediere = $data_operare WHERE comanda_id = (SELECT colet_comanda_id from colet WHERE colet_id = $colet_id);");
			}	
			if ($update1 && !empty($result2) && mysql_num_rows($result2) > 0) {
				$update4 = mysql_query("UPDATE comanda SET comanda_data_livrare = $data_operare WHERE comanda_id = (SELECT colet_comanda_id from colet WHERE colet_id = $colet_id);");
			}	
			
			// echoing JSON response
			echo json_encode($response);
		} else {
			$response["success1"] = 0;
			$response["message1"] = "Actualizare esuata";
			
			// echoing JSON response
			echo json_encode($response);
		}
    } else {
        // failed to insert row
        $response["success1"] = 0;
        $response["message1"] = "Inserare esuata";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success1"] = 0;
    $response["message1"] = "Informatii incomplete";

    // echoing JSON response
    echo json_encode($response);
}
?>