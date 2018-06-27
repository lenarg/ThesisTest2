<?php
/* user file */
require 'connect.php'; //not sure if we need it
include('https.php'); //Includes the control file that always redirects to https

if(isset($_POST['submit_user']))
{
	session_start();   //starting the session	

	if(!empty($_POST['username']) AND !empty($_POST['password']) AND !empty($_POST['cpassword']) 
				AND !empty($_POST['name']) AND !empty($_POST['surname']) AND !empty($_POST['email'])) 
	{
			$username= trim($_POST[username]);
			$email= trim($_POST[email]);
			
			$remove[] = "'";
			$remove[] = '"';
			$username=str_replace( $remove, "", $username );
			$email=str_replace( $remove, "", $email );
			
			$password = $_POST['password'];
			$cpassword = $_POST['cpassword'];
			try {
				$slquery = $dbh ->prepare("SELECT 1 FROM users WHERE email = :email");
				$slquery->bindParam(':email', $email, PDO::PARAM_STR);
				$slquery->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}	
			$row = $slquery->fetch(PDO::FETCH_ASSOC);
			
			try {
				$slquery2 = $dbh ->prepare("SELECT 1 FROM users WHERE username = :username");
				$slquery2->bindParam(':username', $username, PDO::PARAM_STR);
				$slquery2->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}	
			$row2 = $slquery2->fetch(PDO::FETCH_ASSOC);			
			
			if( $row ){				 
				 echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Email already exists!') 
								window.location.href='signup.php' 
								</SCRIPT>");
								
			} elseif( $row2 ){			
				 echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Username already exists!') 
								window.location.href='signup.php' 
								</SCRIPT>");
								
			} elseif( $password !== $cpassword ){				
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Passwords do NOT match') 
								window.location.href='signup.php' 
								</SCRIPT>");
								
			} elseif( mb_strlen( $password) < 6 ){				
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Password must be more than 6 characters') 
								window.location.href='signup.php' 
								</SCRIPT>");
				
			} else{
				    $hash = password_hash($password, PASSWORD_DEFAULT); //bcrypt hashing
					
					$name=trim($_POST[name]);		
					$surname=trim($_POST[surname]);	
					$address=trim($_POST[address]);	
					$city=trim($_POST[city]);	
					$phone=trim($_POST[phone]);	
					$name=str_replace( $remove, "", $name );
					$surname=str_replace( $remove, "", $surname );
					$address=str_replace( $remove, "", $address );
					$city=str_replace( $remove, "", $city );
					$phone=str_replace( $remove, "", $phone );
					$tou='2'; //2 at tou because they are not administrators, only the admin can change the tou
					
					try {
						$query = $dbh ->prepare("INSERT INTO users (username,password,name,surname,email,address,city,phone,tou) VALUES (?,?,?,?,?,?,?,?,?)");
						$query->execute(array($username,$hash,$name,$surname,$email,$address,$city,$phone,$tou));
					}
					catch(PDOException $e) {
						echo "Error: " . $e->getMessage();
					}		
					
					if (($query)){    //=== TRUE) {
						header('Location: index.php');							
					} else {
						echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Error: A new record was NOT created!') 
								window.location.href='signup.php' 
								</SCRIPT>");
					}
			}
	}
	else
	{
		echo ("<SCRIPT LANGUAGE='JavaScript'> 
		   window.alert('You did not complete all of the required fields') 
		   window.location.href='signup.php' 
		   </SCRIPT>");

	}
}
?>


