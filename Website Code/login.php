<?php
/* user file */
	require 'connect.php';	
	include('https.php'); //Includes the control file that always redirects to https

	session_start(); // Starting Session
	$error=''; // Variable To Store Error Message
	if (isset($_POST['submit'])) {
		if (empty($_POST['username']) || empty($_POST['password'])) {
			echo ("<SCRIPT LANGUAGE='JavaScript'> 
				window.alert('You did not complete all of the required fields') 
				window.location.href='index.php' 
				</SCRIPT>");
		}
		else
		{
			// Define $username and $password
			$username=$_POST['username'];
			$password=$_POST['password'];

			// To protect MySQL injection for Security purpose
			$username = stripslashes($username);
			$password = stripslashes($password);
			
			try {
				$result = $dbh ->prepare("SELECT password,tou FROM users WHERE username = :username");
				$result->bindParam(':username', $username, PDO::PARAM_STR);
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			$row = $result->fetch(PDO::FETCH_ASSOC);
			if ( ! $row ){
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Wrong username password combination. Please re-enter.') 
					window.location.href='index.php' 
					</SCRIPT>");
			} else {				
				$hash = $row['password'];				
				if (password_verify($password, $hash)) { 				
					$tou=$row['tou'];
					$_SESSION['username']=$username;		// Initializing Session
					$_SESSION['tou']=$tou;
					header("location:profile.php");					
				}
				else { 						
					//its safer to not let the user know if the username or password was incorrect				
					echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Wrong username password combination. Please re-enter.') 
					window.location.href='index.php' 
					</SCRIPT>");					
				}				
			} 
		}
	}
?>

