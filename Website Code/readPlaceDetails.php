<?php
/* user file */
require('connect.php');

// check request
if(isset($_POST['place_id']) && isset($_POST['place_id']) != "")
{
    // get Place ID
    $place_id = $_POST['place_id'];

    // Get Place Details
    //$query = "SELECT * FROM allplaces WHERE place_id = '$place_id'";
	try {
		$query = $dbh ->prepare("SELECT * FROM allplaces WHERE place_id = :place_id");
		$query->bindParam(':place_id', $place_id, PDO::PARAM_INT);
		$query->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}
	
    if (!$result = $query ){	//mysqli_query($link, $query)) {
        //exit(mysqli_error($link));
		exit($dbh->errorInfo());
    }
    $response = array();
    if( $result ){ 	//mysqli_num_rows($result) > 0) {
        while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
            $response = $row;
        }
    }
    else
    {
        $response['status'] = 200;
        $response['message'] = "Data not found!";
    }
    // display JSON data
    echo json_encode($response);
}
else
{
    $response['status'] = 200;
    $response['message'] = "Invalid Request!";
}

?>