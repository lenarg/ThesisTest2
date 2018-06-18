<?php
/* user file */
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https

 // check if the 'tid' variable is set in URL, and check that it is valid
 if (isset($_GET['tid']) && is_numeric($_GET['tid'])) //einai tid epeidi etsi to exoume grammeno meta to href
 {
	 // get id value
	 $tour_id = $_GET['tid'];
	 
	 // delete the entry
	 //$result1= mysqli_query($link, "DELETE FROM tours WHERE tour_id='$tour_id'");
	 try {
		$result = $dbh ->prepare("DELETE FROM tours WHERE tour_id=:tour_id");
		$result->bindParam(':tour_id', $tour_id, PDO::PARAM_INT);
		$result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
	 
	 // redirect back to the view page
	 header("Location: tours.php");
 }
 else
 // if id isn't set, or isn't valid, redirect back to view page
 {
	header("Location: tours.php");
 }
 
?>