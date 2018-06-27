<?php
/* user file */	
	require 'connect.php';
	include('https.php'); //Includes the control file that always redirects to https
	
	session_start();// Starting Session
	
	// Storing Session
	$user_check=$_SESSION['username'];
	
	//Fetching Complete Information Of User
	try {
		$result = $dbh ->prepare("SELECT username, tou FROM users WHERE username=:user_check");
		$result->bindParam(':user_check', $user_check, PDO::PARAM_STR);
		$result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
	$row = $result->fetch(PDO::FETCH_ASSOC);
	
	
	$login_session =$row['username'];
	$admin_check=$row['tou'];
	if(!isset($login_session)){
		$dbh = null;
		header('Location: index.php'); // Redirecting To Home Page
	}
	elseif($admin_check != '1'){
		$dbh = null;		
		header('Location: profile.php'); // Redirecting To Simple User profile page
	}	
?>

