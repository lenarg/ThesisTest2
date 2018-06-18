<?php
/* admin file */
include('adminsession.php');
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
<head>
	<title>Users</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" language="Javascript">
		<!--hide
		function load(url){
		window.location.href=url;
		}
	</script>
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
	
	<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/base/jquery-ui.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

 <?php include('navBar.php'); ?>

<div class="container">
		<center>
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title">Saved Users</h3>
			  </div>
			  <div class="panel-body">
						
				<div id="container">
					 <p><a class="button" href="signup.php">Add a new user</a></p>
					 <!-- <p><button id="create-place">Add a new place</button></p> -->
					 
					 <div id="maincontent">
					 <div class="table-responsive">
						<?php							
							//$result=mysqli_query($link, "SELECT * FROM users");
							try {
								$result = $dbh ->prepare("SELECT * FROM users");
								$result->execute();
							}
							catch(PDOException $e) {
								echo "Error: " . $e->getMessage();
							}		
							//$row = $result->fetch(PDO::FETCH_ASSOC);
							   
								echo "<table class='table' cellpadding='10' border='1'  class='sortable'>";
								echo "<thead>";
								echo "<tr> <th>ID</th> <th>Username</th> <th>Name</th> <th>Surname</th> <th>email</th> <th>Address</th> <th>City</th> <th>Phone</th> <th>Type of user</th> </tr>";
								echo "</thead>";
								
								echo "<tbody>";
								
								while($row = $result->fetch(PDO::FETCH_ASSOC)){	//$row = mysqli_fetch_array( $result )) {
									 
									 echo "<tr>";
									 echo '<td>' . $row['user_id'] . '</td>';
									 echo '<td>' . $row['username'] . '</td>';
									 echo '<td>' . $row['name'] . '</td>';
									 echo '<td>' . $row['surname'] . '</td>';
									 echo '<td>' . $row['email'] . '</td>';
									 echo '<td>' . $row['address'] . '</td>';
									 echo '<td>' . $row['city'] . '</td>';
									 echo '<td>' . $row['phone'] . '</td>';
									 echo '<td>' . $row['tou'] . '</td>';
									 echo '<td><a class="button" href="adminedituser.php?id=' . $row['user_id'] . '">Edit</a>&nbsp;<a class="button" a href="admindeleteuser.php?id=' . $row['user_id'] . '" onclick="return confirm(\'Delete user?\');">Delete</a></td>';
									 echo "</tr>";
									 
								}
										
								echo "</tbody>";
									
								
								echo "</table>"; // close table
						?>
						   
					</div>	
					 </div>
				</div>
						
				<script src="https://code.jquery.com/jquery-1.12.4.min.js"
					integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
					crossorigin="anonymous"></script>
			  </div>
			</div>
		</center>
		</div>
	
	<?php include('footer.php'); ?>
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>