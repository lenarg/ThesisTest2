<?php
/* user file */
	include('login.php'); // Includes Login Script
	include('https.php'); //Includes the control file that always redirects to https
	
	if(isset($_SESSION['login_user'])){
		header("location: profile.php");
	}?>

<!DOCTYPE html>
<html>
<head>
	<title>mARs - Log In page</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1><b>mARs</b> - Tourist Maps of Western Macedonia </h1>
		
		<div id="login">
				<h2>Login Form</h2>
				<form action="login.php" method="POST">
					<label>Username :</label>
					<input id="username" name="username" placeholder="username" type="text">
					<label>Password :</label>
					<input id="password" name="password" placeholder="********" type="password">
					<input name="submit" type="submit" value="Login">
					<span><?php echo $error; ?></span>
				</form>
				<font size="1"><a href="ForgottenPassword.php">Forgot your password?</a></font> <br><br><br>
				<p>or <font size="5"><a href="signup.php">Sign up</a></font></p>
		</div>
	</div>
	
	<?php include('footer.php');?>
	
	
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>