<?php
/* user file */
require 'connect.php';
include('https.php'); //Includes the control file that always redirects to https

/**
 * Generate a random string, using a cryptographically secure 
 * pseudorandom number generator (random_int)
 * 
 * For PHP 7, random_int is a PHP core function
 * For PHP 5.x, depends on https://github.com/paragonie/random_compat
 * 
 * @param int $length      How many characters do we want?
 * @param string $keyspace A string of all possible characters
 *                         to select from
 * @return string
 */
function random_str($length, $keyspace = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ')
{
    $str = '';
    $max = mb_strlen($keyspace, '8bit') - 1;
    for ($i = 0; $i < $length; ++$i) {
        $str .= $keyspace[rand(0, $max)]; //rand instead of random_int
    }
    return $str;
}

// check if the form has been submitted. If it has, process the form and save it to the database
 if (isset($_POST['submit']))
 {
	 $email=$_POST['email'];
	 if ($email == '' )
	 {
	 // generate error message
	 $error = 'ERROR: Please fill in all required fields!';
	 }
	else
	 {
		 // search the database 
		//$email=$_POST['email'];
		//$sresult = mysqli_query($link, "SELECT * FROM users WHERE email='$email'");
		try {		
			$sresult = $dbh ->prepare("SELECT * FROM users WHERE email = :email");		
			$sresult->bindParam(':email', $email, PDO::PARAM_STR);		
			$sresult->execute();						
		}
		catch(PDOException $e) {
			echo "Error: " . $e->getMessage();
		}
		$row = $sresult->fetch(PDO::FETCH_ASSOC);
		
		//if(mysqli_num_rows($sresult)>0){
			if ( $row ){
			
				//NEW Password
				$a = random_str(8);
				$hash = password_hash($a, PASSWORD_DEFAULT);
								
				//SAVE NEW PASSWORD IN DB
				//$query = mysqli_query($link, "UPDATE users SET password='$hash' WHERE email='$email'")
				//			or die(mysqli_error($link));
				try {		
					$query = $dbh ->prepare("UPDATE users SET password='$hash' WHERE email=:email");		
					$query->bindParam(':email', $email, PDO::PARAM_STR);		
					$query->execute();						
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}
				
							
				if ($query) {
					
					//SEND EMAIL
					// The message
					$message = "You asked your password to be changed. This is your new temporary password:  $a    As soon as you login, go to your settings and change it to a new one only you will know. ";

					// In case any of our lines are larger than 70 characters, we should use wordwrap()
					$message = wordwrap($message, 70, "\r\n");

					// Send
					//mail('caffeinated@example.com', 'My Subject', $message);
					mail($email, 'Forgotten Password', $message);
					
					echo ("<SCRIPT LANGUAGE='JavaScript'> 
									window.alert('Email sent.') 
									window.location.href='index.php' 
									</SCRIPT>");
				}
				else{
					echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Error: A new password was NOT saved!') 
					window.location.href='ForgottenPassword.php' 
					</SCRIPT>");
				}
								
		}
		else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
								window.alert('Non registered email!') 
								window.location.href='ForgottenPassword.php' 
								</SCRIPT>");
				
		}
		 
		 // once saved, redirect back to the view page
		 //header("Location: index.html"); 
	 }
 }

echo'

<!DOCTYPE html>

<head>

<!--<script type="text/javascript" src="tcal.js"></script> -->
<title>Forgotten Password</title>
<link href="style.css" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

</head>
 
 <body>
<br><br>
 <div class="container">
  
	<h1>Forgot your password?</h1> <br>
 
	<form method="POST" action="">
	
		<p>Put your registered email below and an email with a new password will be send to you. <br>
			Please, for your safety, as soon as you get your email login to the site and change your <br>
			password to a new one only you will know.</p>
		
		<table>
			<tr><td>Registered Email:&nbsp;</td><td><input type="text" name="email" maxlength="30" /></td></tr>
			
			<tr><td colspan="2" align="right"></br></td></tr>
			<tr><td colspan="2" align="right"><input type="submit" name="submit" value="Send Email" /></td></tr>
		</table>
				
	</form>
 

 </div>'; 
 include('footer.php'); 
 echo '
 <!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

</body>

</html>';

?>