<?php 
/* user file */
include('session.php'); 
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
<head>
	<title>Change Password</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>

	<?php include('navBar.php'); ?>

	<div class="container">
	</br></br>
	<h1>Change Password</h1>
	</br></br>
	The fields with the asterisk(*) are mandatory</br>
	<form method="post" action="savepass.php">
		<table>
			<tr><td>*New Password&nbsp;</td><td><input type="password" name="password" maxlength="30"/></td></tr>
			<tr><td>*Confirm New Password&nbsp;</td><td><input type="password" name="cpassword" maxlength="30"/></td></tr>
			
			<tr><td colspan="2" align="right"></br></td></tr>
			<tr><td colspan="2" align="right"><input type="submit" name="submit_pass" value="Save"/></td></tr>
		</table>
		
	</form>
	</div>
	
	<?php include('footer.php'); ?>
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>