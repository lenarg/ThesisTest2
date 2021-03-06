<?php
/* user file */
include('session.php'); 
include('https.php'); //Includes the control file that always redirects to https

if(isset($_POST['submit_pass']))
{
	if(!empty($_POST['password']) AND !empty($_POST['cpassword'])) 
	{
			$password = $_POST['password'];
			$cpassword = $_POST['cpassword'];
			
			if($password !== $cpassword){
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Passwords do NOT match') 
								window.location.href='changepassword.php' 
								</SCRIPT>");
			}
			elseif( mb_strlen( $password) < 6 ){
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Password must be more than 6 characters') 
								window.location.href='changepassword.php' 
								</SCRIPT>");
				
			}
			else{
				    $hash = password_hash($password, PASSWORD_DEFAULT); //bcrypt hashing
					try {
						$query = $dbh ->prepare("UPDATE users SET password=:password WHERE user_id = :userid");
						$query->bindParam(':password', $hash, PDO::PARAM_STR);
						$query->bindParam(':userid', $userid, PDO::PARAM_INT);
						$query->execute();
					}
					catch(PDOException $e) {
						echo "Error: " . $e->getMessage();
					}		
				
					if ( $query ){ 
						header('Location: accountsettings.php');							
					} else {
						echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Error: The new password was NOT saved!') 
								window.location.href='accountsettings.php' 
								</SCRIPT>");
					}
			}
	}
	else
	{
		echo ("<SCRIPT LANGUAGE='JavaScript'> 
		   window.alert('You did not complete all of the required fields') 
		   window.location.href='changepassword.php' 
		   </SCRIPT>");

	}
}
?>




