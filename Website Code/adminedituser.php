<?php 
/* admin file */
include('adminsession.php');
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https

// check if the form has been submitted. If it has, process the form and save it to the database
 if (isset($_POST['submit']))
 {
	 // confirm that the 'id' value is a valid integer before getting the form data
	 if (is_numeric($_POST['id']))
	 {	 
		 // get form data, making sure it is valid
		$user_id = $_POST['id'];
		$username=trim($_POST[username]);		//mysqli_real_escape_string($link, trim($_POST[username]));
		$name=trim($_POST[name]);		//mysqli_real_escape_string($link, trim($_POST[name]));
		$surname=trim($_POST[surname]);		//mysqli_real_escape_string($link, trim($_POST[surname]));
		$email=trim($_POST[email]);		//mysqli_real_escape_string($link, trim($_POST[email]));
		$address=trim($_POST[address]);		//mysqli_real_escape_string($link, trim($_POST[address]));
		$city=trim($_POST[city]);		//mysqli_real_escape_string($link, trim($_POST[city]));
		$phone=trim($_POST[phone]);		//mysqli_real_escape_string($link, trim($_POST[phone]));
		$tou=trim($_POST[tou]);		//mysqli_real_escape_string($link, trim($_POST[tou]));
		
		$remove[] = "'";
		$remove[] = '"';
		$username=str_replace( $remove, "", $username );
		$name=str_replace( $remove, "", $name );
		$surname=str_replace( $remove, "", $surname );
		$email=str_replace( $remove, "", $email );
		$address=str_replace( $remove, "", $address );
		$city=str_replace( $remove, "", $city );
		$phone=str_replace( $remove, "", $phone );
		$tou=str_replace( $remove, "", $tou );
		
		if ($username == '' || $name == '' || $surname == '' || $email == '' || $tou == '')
		{
			 // generate error message
			 $error = 'ERROR: Please fill in all required fields!';
		}
		else
		{
			 // save the data to the database 
			 //mysqli_query($link, "UPDATE users SET username='$username', name='$name', surname='$surname', email='$email', address='$address', 
			//				city='$city', phone='$phone', tou='$tou' WHERE user_id='$user_id'");
			try {
				$result = $dbh ->prepare("UPDATE users SET username=:username, name=:name, surname=:surname, email=:email, address=:address, 
						city=:city, phone=:phone, tou=:tou WHERE user_id=:user_id");
				$result->bindParam(':username', $username, PDO::PARAM_STR);
				$result->bindParam(':name', $name, PDO::PARAM_STR);
				$result->bindParam(':surname', $surname, PDO::PARAM_STR);
				$result->bindParam(':email', $email, PDO::PARAM_STR);
				$result->bindParam(':address', $address, PDO::PARAM_STR);
				$result->bindParam(':city', $city, PDO::PARAM_STR);
				$result->bindParam(':phone', $phone, PDO::PARAM_STR);				
				$result->bindParam(':user_id', $user_id, PDO::PARAM_INT);			
				$result->bindParam(':tou', $tou, PDO::PARAM_INT);					
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}	
			 
			 // once saved, redirect back to the view page
			 header("Location: adminusers.php"); 
		}
	 }

	else
	 {
		 // if the 'id' isn't valid, display an error
		 echo 'Error in id!';
	 }
 }
 else
 // if the form hasn't been submitted, get the data from the db and display the form
 {
	 
	 // get the 'id' value from the URL (if it exists), making sure that it is valid (checing that it is numeric/larger than 0)
	 if (isset($_GET['id']) && is_numeric($_GET['id']) && $_GET['id'] > 0)
	 {
		 // query db
		 $user_id = $_GET['id'];
		 //$result = mysqli_query($link, "SELECT * FROM users WHERE user_id='$user_id'")
		//	or die(mysqli_error($link)); 
		 //$row = mysqli_fetch_array($result);
		 try {
			$result = $dbh ->prepare("SELECT * FROM users WHERE user_id=:user_id");
			$result->bindParam(':user_id', $user_id, PDO::PARAM_INT);
			$result->execute();
		}
		catch(PDOException $e) {
			echo "Error: " . $e->getMessage();
		}		
			
		$row = $result->fetch(PDO::FETCH_ASSOC);	 
		   
		 // check that the 'id' matches up with a row in the databse
		 if($row)
		 {		 
			 // get data from db	
			 $username= $row['username']; 
			 $name= $row['name']; 
			 $surname= $row['surname']; 
			 $email= $row['email']; 
			 $address= $row['address'];
			 $city= $row['city']; 
			 $phone= $row['phone']; 
			 $tou= $row['tou'];
		 }
		 else
		 // if no match, display result
		 {
			 echo "No results!";
		 }
	 }
	 else // if the 'id' in the URL isn't valid, or if there is no 'id' value, display an error
	 {
		 echo 'Error!';
	 }
 }

echo '<!DOCTYPE html">

<head>

<!--<script type="text/javascript" src="tcal.js"></script> -->
<title></title>
<link href="style.css" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

</head>
 
 <body>';
 include('navBar.php'); 
 
 echo ' <div class="container">
  
 <div id="maincontent">
 
	<form method="POST" action="">
	<br><br>
	<table>
		<thead><tr><th><h3>Edit User</h3></th></tr></thead>
		<tbody>
			<tr><td>
				<input type="hidden" name="id" value="'; echo $user_id; echo '" />
			</td></tr>
			<tr><td>*Username&nbsp;</td><td><input type="text" name="username" maxlength="20" value="'; echo $username;  echo '"/></td></tr>
			<tr><td>*Name</td><td><input type="text" name="name" maxlength="20" value="'; echo $name;  echo '"/></td></tr>
			<tr><td>*Surname</td><td><input type="text" name="surname" maxlength="20" value="'; echo $surname;  echo '"/></td></tr>
			<tr><td>*Email</td><td><input type="text" name="email" maxlength="30" value="'; echo $email;  echo '"/></td></tr>
			<tr><td>&nbsp;Address</td><td><input type="text" name="address" maxlength="50" value="'; echo $address;  echo '"/></td></tr>
			<tr><td>&nbsp;City</td><td><input type="text" name="city" maxlength="15" value="'; echo $city;  echo '"/></td></tr>
			<tr><td>&nbsp;Phone</td><td><input type="text" name="phone" maxlength="15" value="'; echo $phone;  echo '"/></td></tr>
			<tr><td>Type Of User:</td><td><input type="text" name="tou" maxlength="1" value="'; echo $tou;  echo '"/></td></tr>
			
			<tr><td colspan="2" align="right"><input type="submit" name="submit" value="Submit edits" /></td></tr>
			<tr><td>
				<input type="hidden" name="id" value="'; echo $user_id;  echo '" />
			</td></tr>
		</tbody>
	</table>
	</form>
 
</div> 

 </div>'; 
 
 include('footer.php'); 
 
 echo ' <!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

</body>
</html>';

?> 