<?php
/* user file */
include('session.php');
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https 
?>

<!DOCTYPE html>
<html>
<head>
	<title>Tours</title>
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
	
	<link rel="stylesheet" type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/base/jquery-ui.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<?php include("navBar.php"); 
echo '<div class="container">
	
	<div id="content">
		
		<center>
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title">Saved Tours</h3>
			  </div>
			  <div class="panel-body">
				
				<div id="container">
				
					 <p><a href="newtour.php" class="btn btn-info" role="button">Add New Tour</a></p>
					 <div id="maincontent">
					 <div class="table-responsive">';
							if($admin_check == 1 ) {
								//$result=mysqli_query($link, "SELECT * FROM tours");
								try {
									$result = $dbh ->prepare("SELECT * FROM tours");
									$result->execute();
								}
								catch(PDOException $e) {
									echo "Error: " . $e->getMessage();
								}		
							} else{ 
								//$result=mysqli_query($link, "SELECT * FROM tours WHERE user_id = '$userid' ");
								try {
									$result = $dbh ->prepare("SELECT * FROM tours WHERE user_id = :userid");
									$result->bindParam(':userid', $userid, PDO::PARAM_INT);
									$result->execute();
								}
								catch(PDOException $e) {
									echo "Error: " . $e->getMessage();
								}
							} 
							
							echo "<table class='table' cellpadding='10' border='1'  class='sortable'>";
							echo "<thead>";
							echo "<tr> <th>Tour ID</th>";  //"<tr> <th>Tour ID</th> <th>User ID</th> <th>Tour Name</th> <th>Tour Places</th> <th>Tour Description</th> </tr>";
							if($admin_check == 1 ) {
								echo "<th>User</th>";
							}
							echo "<th>Tour Name</th> <th>Tour Places</th> <th>Tour Description</th> </tr>";
							echo "</thead>";
							echo "<tbody>";
								
							while($row = $result->fetch(PDO::FETCH_ASSOC)) {
								echo '<tr>';
								echo '<td>' . $row['tour_id'] . '</td>';
								if($admin_check == 1 ) {
									echo '<td>' . $row['user_id'] . '.';
									//$usern = mysqli_query($link, "SELECT * FROM users WHERE user_id = '".$row['user_id']."' ");
									//while($row2 = mysqli_fetch_array( $usern )) {
									//	echo ' ' . $row2['username'] . '</td>';
									//}	
									try {
										$usern = $dbh ->prepare("SELECT * FROM users WHERE user_id = :user_id");
										$usern->bindParam(':user_id', $row['user_id'], PDO::PARAM_INT);   //
										$usern->execute();
									}
									catch(PDOException $e) {
										echo "Error: " . $e->getMessage();
									}		
									//$row = $usern->fetch(PDO::FETCH_ASSOC);
									while( $row2 = $usern->fetch(PDO::FETCH_ASSOC) ) {
										echo ' ' . $row2['username'] . '</td>';
									}	
									
								}
								echo '<td>' . $row["tour_name"] . '</td>';
								//mtakes the place_ids from the db and shows them as their names
								$tplaces = explode( ";" , $row["tour_places"] );
								$nampl = '';
								for ($i = 0; $i < count($tplaces); $i++) {
									//$result2=mysqli_query($link, "SELECT name FROM allplaces WHERE place_id = '$tplaces[$i]' ");
									try {
										$result2 = $dbh ->prepare("SELECT name FROM allplaces WHERE place_id = :place_id");
										$result2->bindParam(':place_id', $tplaces[$i] , PDO::PARAM_INT);   //
										$result2->execute();
									}
									catch(PDOException $e) {
										echo "Error: " . $e->getMessage();
									}	
									
									while($row2 = $result2->fetch(PDO::FETCH_ASSOC)) {
										$nampl = $nampl . ' ' . $row2['name'];
									}
								}
								echo '<td>' . $nampl . '&nbsp;<a class="button" title="View Tour" style="float: right;" data-toggle="modal" href="viewtour.php?tid=' . $row['tour_id'] . '"><img src="images/viewt.png" width="20" /></a></td>';
								//echo '<td>' . $row['tour_places'] . '</td>';
								echo '<td>' . $row['tour_desc'] . '</td>';
								echo '<td><a class="button" href="edittour.php?tid=' . $row['tour_id'] . '">Edit</a>&nbsp;<a class="button" a href="deletetour.php?tid=' . $row['tour_id'] . '" onclick="return confirm(\'Delete tour?\');">Delete</a></td>';
								echo '</tr>';	 
							}			
							echo '</tbody>';
							echo '</table>'; // close table
						 
				echo '</div>
					</div>
				</div>				
						
				<script src="https://code.jquery.com/jquery-1.12.4.min.js"
					integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
					crossorigin="anonymous"></script>
			  </div>
			</div>
		</center>
	
	</div>
	</div>'; 
	
	include('footer.php'); ?>
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>