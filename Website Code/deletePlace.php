<?php
/* user file */
require('connect.php');
require('session.php');

// check request
if( isset($_POST['place_id']) && isset($_POST['place_id']) != "")
{
    // get Place id
    $place_id = $_POST['place_id'];
	
    // delete Place
    //$query = "DELETE FROM allplaces WHERE place_id = '$place_id'";
    //if (!$result = mysqli_query($link, $query)) {
    //    exit(mysqli_error($link));
    //}
	try {
		$query = $dbh ->prepare("DELETE FROM allplaces WHERE place_id = :place_id");
		$query->bindParam(':place_id', $place_id, PDO::PARAM_INT);
		$query->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}
	
	if (!$result = $query){
		exit($dbh->errorInfo());
	}	
	
}

?>