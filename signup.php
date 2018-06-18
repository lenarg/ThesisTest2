<!-- user file -->
<!DOCTYPE html>
<html>
<head>
	<title>Sign Up </title>
	<link href="style.css" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>

	<div class="container">
	
			
		<div id="content">
			
			<h1>Sign Up</h1>
			</br>
			The fields with the asterisk(*) are mandatory</br>
			<form method="post" action="saveuser.php">
				<table>
					<tr><td>*Username&nbsp;</td><td><input type="text" name="username" maxlength="20"/></td></tr>
					<tr><td>*Password&nbsp;</td><td><input type="password" name="password" maxlength="30"/></td></tr>
					<tr><td>*Confirm Password&nbsp;</td><td><input type="password" name="cpassword" maxlength="30"/></td></tr>
					<tr><td>*Name</td><td><input type="text" name="name" maxlength="20"/></td></tr>
					<tr><td>*Surname</td><td><input type="text" name="surname" maxlength="20"/></td></tr>
					<tr><td>*Email</td><td><input type="text" name="email" maxlength="30"/></td></tr>
					<tr><td>&nbsp;Address</td><td><input type="text" name="address" maxlength="50"/></td></tr>
					<tr><td>&nbsp;City</td><td><input type="text" name="city" maxlength="15"/></td></tr>
					<tr><td>&nbsp;Phone</td><td><input type="text" name="phone" maxlength="15"/></td></tr>
					<tr><td colspan="2" align="right"></br></td></tr>
					<tr><td colspan="2" align="right"><input type="submit" name="submit_user" value="Sign up"/></td></tr>
				</table>
				
			</form>
		</div><!-- #content -->
	
	</div>
	
	<?php include('footer.php'); ?>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

</body>
</html>