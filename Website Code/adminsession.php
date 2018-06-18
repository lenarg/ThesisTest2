<?php
/* user file */
	//error_reporting(E_ALL ^ E_DEPRECATED); // until I change all to mysqlis
	require 'connect.php';
	include('https.php'); //Includes the control file that always redirects to https
	
	session_start();// Starting Session
	
	// Storing Session
	$user_check=$_SESSION['username'];
	
	// SQL Query To Fetch Complete Information Of User
	//$ses_sql=mysqli_query($link, "SELECT username, tou FROM users WHERE username='$user_check'")
	//	or die(mysqli_error($link));
	//$row = mysqli_fetch_assoc($ses_sql);
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
		//mysqli_close($connection); // Closing Connection
		$dbh = null;
		header('Location: index.php'); // Redirecting To Home Page
	}
	elseif($admin_check != '1'){
		//mysqli_close($connection); // Closing Connection		
		$dbh = null;		
		header('Location: profile.php'); // Redirecting To Simple User profile page
	}
	
?>

