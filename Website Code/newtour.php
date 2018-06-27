<?php
/* user file */
require('connect.php');
include ('session.php');
include('https.php'); //Includes the control file that always redirects to https

if($admin_check == 1 ) {
			try {
				$result = $dbh ->prepare("SELECT name,place_id,type,coordinates FROM allplaces");
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			
}else{
			try {
				$result = $dbh ->prepare("SELECT name,place_id,type,coordinates FROM allplaces WHERE user_id =:user_id");
				$result->bindParam(':user_id', $userid, PDO::PARAM_INT);
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			
}

// check if the form has been submitted. If it has, process the form and save it to the database
 if ( isset($_POST['submit_tour']) ){
	 
	 if( !empty($_POST['tour_name']) AND !empty($_POST['tour_select']) ) {
		
		$tour_name = trim($_POST['tour_name']); 
		$tour_desc = trim($_POST['tour_desc']); 
		
				
		//$tour_places = $_POST[tour_places];
				
		$remove[] = "'";
		$remove[] = '"';
		$tour_name = str_replace( $remove, "", $tour_name );
		$tour_desc = str_replace( $remove, "", $tour_desc );
		

		try {			
			$selectresult = $dbh ->prepare("SELECT 1 FROM tours WHERE tour_name = :tour_name");
			$selectresult->bindParam(':tour_name', $tour_name, PDO::PARAM_STR);
			$selectresult->execute();
		}
		catch(PDOException $e) {
			echo "Error: " . $e->getMessage();
		}	
		//$num_rows = $selectresult->fetchColumn();
		$row_count = $selectresult->rowCount(); 	
				
		if( $row_count >0 ){ 						
			echo ("<SCRIPT LANGUAGE='JavaScript'> 
				window.alert('This tour name already exists, choose another') 
				window.location.href='newtour.php' 
				</SCRIPT>");
		}else{
						
			$tour_select = $_POST['tour_select'];
			$tourp ='';
			
			$tplac = '';
			for ($i = 0; $i < count($tour_select); ++$i) {
				//print $tour_select[$i];
				$tplac1 = $tour_select[$i];
				$tplac = $tplac . ';' . $tplac1;
			}			
						
			$tour_places = $tplac;
			
			try {
				
				$query = $dbh->prepare("INSERT INTO tours(user_id, tour_name, tour_places, tour_desc) VALUES(?,?,?,?)");   //(:user_id,:tour_name,:tour_places,:tour_desc)");
				$query->execute( array( $userid,$tour_name,$tour_places,$tour_desc ) ); 
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
				
			}					
						
			if ( $query ) {
				header('Location: tours.php');							
			} else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
						window.alert('Error: A new tour was NOT created!') 
						window.location.href='newtour.php' 
						</SCRIPT>");
			}
		}
	}
	else
	{
		echo ("<SCRIPT LANGUAGE='JavaScript'> 
		   window.alert('You did not complete all of the required fields') 
		   window.location.href='newtour.php' 
		   </SCRIPT>");

	}
}
	 
?> 

<!DOCTYPE html>
<html>
<head>
	<title>New Tour</title>
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

	<?php include('navBar.php'); ?>	
	
	<div class="container">
	
		<div id="content">
			
			</br>
			<div class="form-group">
			<form method="POST" action="">
				<table>
				<thead><tr><th><h3>Create New Tour</h3></th></tr></thead>
				<tbody>
					<tr><td>Tour Name:&nbsp;</td><td><input type="text" name="tour_name" maxlength="20"/></td></tr>
					<tr><td>Tour Description: &nbsp;</td><td><textarea type="text" name="tour_desc" maxlength="500"></textarea></td></tr>
					<tr><td><br></td></tr>
					<tr><td><br></td></tr>
					<tr><td> 
						<label for="select-places">Select places for the tour: </br>(CTRL+Click for multiple selection)</label>
						<select class="form-control" name='tour_select[]' id="select-places" multiple="multiple">
							<?php while( $row=$result->fetch(PDO::FETCH_ASSOC) ){ 
								echo "<option value='".$row['place_id']."'>".$row['name']."</option>";	
							} ?>
						</select>
						<div id="otherdetail" style="display: none;">
							
							More Detail Here Please 
							
						</div>
					</td>
					<td></td>
					</tr>
					
					<tr><td><br></td></tr>
					<tr><td><input type="submit" name="submit_tour" value="Save Tour"/></td></tr>
				</tbody>
				</table>
				
			</form>
			</div>
		</div>
	</div>
	<?php include('footer.php'); ?>
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

	</body>
</html>