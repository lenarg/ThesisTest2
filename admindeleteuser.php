<?php
/* admin file */
include('adminsession.php');
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https
 
 // check if the 'id' variable is set in URL, and check that it is valid
 if (isset($_GET['id']) && is_numeric($_GET['id'])) {  //einai id epeidi etsi to exoume grammeno meta to href
	// get id value
	$user_id = $_GET['id'];
	 
	// delete the entry
	//$result1= mysqli_query($link, "DELETE FROM users WHERE user_id='$user_id'");
	try {
		$result1 = $dbh ->prepare("DELETE FROM users WHERE user_id = :user_id");
		$result1->bindParam(':user_id', $user_id, PDO::PARAM_INT);
		$result1->execute();
	}
		catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}			 
	 
	// redirect back to the view page
	header("Location: adminusers.php");
 } else {   // if id isn't set, or isn't valid, redirect back to view page
	header("Location: adminusers.php");
 }
 
?>