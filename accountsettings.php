<?php
/* user file */
include('session.php');
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
<head>
	<title>User Account</title>
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
				<h3 class="panel-title">User Account</h3>
			  </div>
			  <div class="panel-body">
								
				<div id="container">
					 					 
					 <div id="maincontent"> 
					 <?php
								//$result=mysqli_query($link, "SELECT * FROM users WHERE user_id = '$userid' ");
								try {
									$result = $dbh ->prepare("SELECT * FROM users WHERE user_id = :userid");
									$result->bindParam(':userid', $userid, PDO::PARAM_INT);
									$result->execute();
								}
								catch(PDOException $e) {
									echo "Error: " . $e->getMessage();
								}		
																
								$row = $result->fetch(PDO::FETCH_ASSOC);
							   
							   
								echo '<table cellpadding="10" border="1"  class="sortable">';
								echo '<thead>';
								echo '<tr> <th>ID</th> <th>Username</th> <th>Password</th> <th>Name</th> <th>Surname</th> <th>email</th> <th>Address</th> <th>City</th> <th>Phone</th> </tr>';
								echo '</thead>';
								
								echo '<tbody>';
								
								//while($row ){   //= mysqli_fetch_array( $result )) {
									 
									 echo '<tr>';
									 echo '<td>' . $row['user_id'] . '</td>';
									 echo '<td>' . $row['username'] . '</td>';
									 echo '<td> ****** </td>';
									 echo '<td>' . $row['name'] . '</td>';
									 echo '<td>' . $row['surname'] . '</td>';
									 echo '<td>' . $row['email'] . '</td>';
									 echo '<td>' . $row['address'] . '</td>';
									 echo '<td>' . $row['city'] . '</td>';
									 echo '<td>' . $row['phone'] . '</td>';
									 echo '<td><a class="button" href="accountedit.php?id=' . $row['user_id'] . '">Edit</a>&nbsp;<a class="button" href="changepassword.php?id=' . $row['user_id'] . '">Change Password</a></td>';
									 echo '</tr>';
									 
								//}
										
								echo '</tbody>';
									
								
								echo '</table>'; // close table
					?>
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