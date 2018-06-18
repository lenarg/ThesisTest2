<?php 
/* user file */
require('connect.php');
include('session.php');
include('https.php'); //Includes the control file that always redirects to https

if($admin_check == 1 ) {
	try {
		$result1 = $dbh ->prepare("SELECT name,place_id FROM allplaces");
		$result1->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
}else{
	try {
		$result1 = $dbh ->prepare("SELECT name,place_id FROM allplaces WHERE user_id = :userid");
		$result1->bindParam(':userid', $userid, PDO::PARAM_INT);
		$result1->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
	
}


// check if the form has been submitted. If it has, process the form and save it to the database
 if ( isset($_POST['submit']) )
 {
	 // confirm that the 'id' value is a valid integer before getting the form data
	 if ( is_numeric($_POST['tour_id']) )
	 {	 
		 // get form data, making sure it is valid
		$tour_id = $_POST['tour_id'];
		$tour_name = trim(($_POST['tour_name']));  
		$tour_desc = trim(($_POST['tour_desc']));  
		
		$remove[] = "'";
		$remove[] = '"';
		$tour_name = str_replace( $remove, "", $tour_name );
		$tour_desc = str_replace( $remove, "", $tour_desc );
		
		if ($tour_name == '' )
		{
			// generate error message
			$error = 'ERROR: Please fill in all required fields(Tour Name, Tour Places)!';
		}
		else
		{
			$tour_select = $_POST['tour_select1'];
			$tourp ='';
			
			$tplac = $tour_select[0];
			 for ($i = 1; $i < count($tour_select); ++$i) {
				//print $tour_select[$i];
				$tplac1 = $tour_select[$i];
				$tplac = $tplac . ';' . $tplac1;
			}
			
			$tour_places = $tplac;
			
			 // save the data to the database 			
			try {
				$result2 = $dbh ->prepare("UPDATE tours SET tour_name=:tour_name, tour_places=:tour_places, tour_desc=:tour_desc WHERE tour_id=:tour_id");
				$result2->bindParam(':tour_name', $tour_name, PDO::PARAM_STR);
				$result2->bindParam(':tour_places', $tour_places, PDO::PARAM_STR);				
				$result2->bindParam(':tour_desc', $tour_desc, PDO::PARAM_STR);
				$result2->bindParam(':tour_id', $tour_id, PDO::PARAM_INT);
				$result2->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			 
			 // once saved, redirect back to the view page
			 header("Location: tours.php"); 
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
	 if (isset($_GET['tid']) && is_numeric($_GET['tid']) && $_GET['tid'] > 0)
	 {
		 // query db
		 $tour_id = $_GET['tid'];
		 try {
			$result = $dbh ->prepare("SELECT * FROM tours WHERE tour_id=:tour_id");
			$result->bindParam(':tour_id', $tour_id, PDO::PARAM_INT);
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
			 $tour_id = $row['tour_id'];
			 $user_id = $row['user_id'];
			 $tour_name = $row['tour_name'];
			 $tour_places = $row['tour_places'];
			 $tour_desc = $row['tour_desc'];
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


echo '<!DOCTYPE html>

<head>

	<!--<script type="text/javascript" src="tcal.js"></script> -->
	<title>Edit Tours</title>
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

</head>
 
 <body>'; 
 
 include('navBar.php'); 
 
 echo '</br>
	 </br>
	 <div class="container">
	  
	 <div id="maincontent">
		<div class="form-group">
		<form method="POST" action="">
		<table>
			<thead><tr><th><h3>Edit Tour</h3></th></tr></thead>
			<tbody>
				<tr><td><input type="hidden" name="tour_id" value="'; echo $tour_id; echo '" /></td></tr>
				<tr><td>Tour Name:</td><td><input type="text" name="tour_name" maxlength="90" value="'; echo $tour_name; echo '" /></td></tr>
				<tr><td>Tour Description:</td><td><input type="text" name="tour_desc" maxlength="500" value="'; echo $tour_desc; echo '" /></td></tr>
				<tr><td></br></td></tr>
				<tr><td> 
						<label for="select-places2">Select places for the tour: </br>(CTRL+Click for multiple selection)</label>
						<select class="form-control" name=\'tour_select1[]\' id="select-places2" multiple="multiple">';
						
							 $tplaces2 = explode( ";" , $tour_places );
							 $i=0;
							 while( $row = $result1->fetch(PDO::FETCH_ASSOC) ){ 
								if ( $row['place_id'] == $tplaces2[$i] ){
									echo "<option selected value'".$row['place_id']."'>".$row['name']."</option>";	
									$i++;
								}else{
									echo "<option value='".$row['place_id']."'>".$row['name']."</option>";
								}
							}
							
					echo '</select>
					</td>
					<td></td>
					</tr>
					<tr><td><br></td></tr>
				<tr><td colspan="2" align="right"><input type="submit" name="submit" value="Submit edits" /></td></tr>
				<tr><td><input type="hidden" name="tour_id" value="'; echo $tour_id; echo '" /></td></tr>
			</tbody>
		</table>
		</form>
	 </div>
	</div>
	 </div>';
	 
	 include('footer.php'); 
	 
	 echo '<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	 <!-- Latest compiled and minified JavaScript -->
	 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

</body>

</html>';

?> 