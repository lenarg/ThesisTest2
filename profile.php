<?php
/* user file */
include('session.php');
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
<head>
	<title>Home Page</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	   
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>

<?php include('navBar.php');
echo ' 
	
	<div class="container">
		<div id="profile">
			<h1><b id="welcome">Welcome <i>'; echo $login_session; echo '</i>!</b></h1>
			<div class="container" id="menudiv">
				<br><br><b id="menu"><big> Menu </big></b>';
				if($admin_check == 1 ) { 
					echo '<br><a id="users" href="adminusers.php"> Users </a>';	
				} 
				echo '
				<br><a id="maps" href="maps.php"> Maps </a>
				<br><a id="maps" href="tours.php"> Tours </a>
				<br><a id="logout" href="logout.php">Log Out</a>
			</div>
		</div>
	</div>';
	
	include 'footer.php';?>
	
		
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>

