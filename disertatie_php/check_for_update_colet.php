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

if (isset($_POST['id_colet']) && isset($_POST['id_user'])) {

	$idColet = $_POST['id_colet'];
	$idUser = $_POST['id_user'];

	// useri cu drept de update la comanda curenta
	$result = mysql_query("SELECT * FROM user
WHERE user_id=$idUser AND user_hub_id IN
(SELECT proxi_next FROM
      (SELECT  proxi_hub_id_plecare,  @pv:=proxi_next as proxi_next 
FROM  ( SELECT proxi_hub_id_plecare, proxi_next FROM proxi , hub WHERE proxi_hub_id_sosire = hub_id
              AND 
	hub_localitate = (SELECT c2.client_localitate   
	FROM comanda as c, client as c2
	                                WHERE c.comanda_dest_id=c2.client_id
                                                   	                                                AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet)) 
                             AND
                             hub_judet = (SELECT c2.client_judet
	FROM comanda as c, client as c2
	                                WHERE c.comanda_dest_id=c2.client_id
                                                                                                           AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet))  ) as SEGMENTE

JOIN
(SELECT @pv:= (SELECT  hub_id FROM hub WHERE
	                     hub_localitate=(SELECT c1.client_localitate
	                                                                 FROM comanda as c, client as c1
	                                                                    WHERE c.comanda_exp_id=c1.client_id
                                                                                                           AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet))    
                                                                            AND
                                                                            hub_judet=(SELECT c1.client_judet  
	                                              FROM comanda as c, client as c1
	                                                                    WHERE c.comanda_exp_id=c1.client_id
                                                                                                               AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet)) ))     TMP

      WHERE  proxi_hub_id_plecare=@pv) as  STATII)
UNION
SELECT * FROM user
WHERE user_id=$idUser AND user_hub_id =   (SELECT hub_id  FROM hub WHERE
                                                        hub_localitate= (SELECT c1.client_localitate  
                                                                                       FROM comanda as c, client as c1
                                                                                       WHERE c.comanda_exp_id=c1.client_id
                                                                                        AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet))
                                                       AND
                                                         hub_judet= (SELECT c1.client_judet  
                                                                                       FROM comanda as c, client as c1
                                                                                      WHERE c.comanda_exp_id=c1.client_id
                                                                                      AND c.comanda_id=(SELECT colet_comanda_id from colet WHERE colet_id = $idColet)));") or die(mysql_error());

	// check for empty result
	if (!empty($result) && mysql_num_rows($result) > 0) {

		// success
		$response["success"] = 1;
		$response["message"] = "User cu drept de update!";

		// echoing JSON response
		echo json_encode($response);
	} else {
		// no products found
		$response["success"] = 0;
		$response["message"] = "User fara drept de update!";

		// echo no users JSON
		echo json_encode($response);
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Lipsa informatii";

    // echoing JSON response
    echo json_encode($response);
}
?>
