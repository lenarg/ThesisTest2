<?php
/* user file */
	require 'connect.php';
	include('https.php'); //Includes the control file that always redirects to https
	
	if(!isset($_SESSION)) 
    { 
        session_start(); //Starting Session if it has not already started
    } 
	
	// Storing Session
	$user_check=$_SESSION['username'];
	
	try {		
		$ses_sql = $dbh ->prepare("SELECT username, tou, user_id FROM users WHERE username = :username");		
		$ses_sql->bindParam(':username', $user_check, PDO::PARAM_STR);		
		$ses_sql->execute();						
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
					
	$row = $ses_sql->fetch(PDO::FETCH_ASSOC);
			
	$login_session =$row['username'];
	$admin_check=$row['tou'];
	$userid=$row['user_id'];
	if(!isset($login_session)){
		$dbh = null;
		header('Location: index.php'); // Redirecting To Home Page
	}
	
?>

