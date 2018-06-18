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
			//$username = mysql_real_escape_string($username);
			//$password = mysql_real_escape_string($password);
			//$username = mysqli_real_escape_string($link, $username); //not needed in pdo
			//$password = mysqli_real_escape_string($link, $password);
			
			//$query = mysqli_query($link, "SELECT password,tou FROM users WHERE username = '$username' ") 
			//		or die(mysqli_error($link));
			
			try {
				$result = $dbh ->prepare("SELECT password,tou FROM users WHERE username = :username");
				$result->bindParam(':username', $username, PDO::PARAM_STR);
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			
			//$rows = mysqli_num_rows($query);
			$row = $result->fetch(PDO::FETCH_ASSOC);
			//echo $row[0];
			
			if ( ! $row ){
				//echo "here1";
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Wrong username password combination. Please re-enter.') 
					window.location.href='index.php' 
					</SCRIPT>");
					
					
			} else {				//if ($rows == 1) {
				
				//$row = mysqli_fetch_row($query);
				//$row = $result->fetch(PDO::FETCH_ASSOC);
				//echo "here2";
				//echo $row['password'];
				//echo $row['tou'];
				$hash = $row['password'];
				
				if (password_verify($password, $hash)) { 
				
					$tou=$row['tou'];//$row[1];
					$_SESSION['username']=$username;		// Initializing Session
					$_SESSION['tou']=$tou;
					header("location:profile.php");
					
				}
				else { 						//its safer to not let the user know if the username or password was incorrect
				
					echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Wrong username password combination. Please re-enter.') 
					window.location.href='index.php' 
					</SCRIPT>");
					
				}				
			} 
		}
	}
?>