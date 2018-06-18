<?php
/* user file */
	//error_reporting(E_ALL ^ E_DEPRECATED); // until I change all to mysqlis
	require 'connect.php';
	include('https.php'); //Includes the control file that always redirects to https
	
	if(!isset($_SESSION)) 
    { 
        session_start(); //Starting Session if it has not already started
    } 
	//session_start();// Starting Session
	
	// Storing Session
	$user_check=$_SESSION['username'];
	
	// SQL Query To Fetch Complete Information Of User
	//$ses_sql=mysqli_query($link, "SELECT username, tou, user_id FROM users WHERE username='$user_check'")
	//	or die(mysql_error($link));
		
	try {		
		$ses_sql = $dbh ->prepare("SELECT username, tou, user_id FROM users WHERE username = :username");		
		$ses_sql->bindParam(':username', $user_check, PDO::PARAM_STR);		
		$ses_sql->execute();						
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
			
	//$row = mysqli_fetch_assoc($ses_sql);		
	$row = $ses_sql->fetch(PDO::FETCH_ASSOC);
			
	$login_session =$row['username'];
	$admin_check=$row['tou'];
	$userid=$row['user_id'];
	if(!isset($login_session)){
		//mysqli_close($connection); // Closing Connection
		$dbh = null;
		header('Location: index.php'); // Redirecting To Home Page
	}
	
?>

