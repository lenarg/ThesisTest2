<?php 
/* user file */
require('connect.php');
include('https.php'); //Includes the control file that always redirects to https

// check if the form has been submitted. If it has, process the form and save it to the database
 if (isset($_POST['submit']))
 {
	 // confirm that the 'id' value is a valid integer before getting the form data
	 if (is_numeric($_POST['place_id']))
	 {
	 
		 // get form data, making sure it is valid
		$place_id = $_POST['place_id'];
		$name = trim($_POST['name']);
		$description = trim($_POST['description']);
		$remove[] = "'";
		$remove[] = '"';
		$name = str_replace( $remove, "", $name );
		$desc = str_replace( $remove, "", $desc );
			
		if ($name == '' || $description == '')
		{
			 // generate error message
			 $error = 'ERROR: Please fill in all required fields!';
		}
		else
		 {
			 // save the data to the database 
			 //mysqli_query($link, "UPDATE allplaces SET name='$name', description='$description' WHERE place_id='$place_id'");
			 try {
				$result2 = $dbh ->prepare("UPDATE allplaces SET name=:name, description=:description WHERE place_id=:place_id");
				$result2->bindParam(':name', $name, PDO::PARAM_STR);
				$result2->bindParam(':description', $description, PDO::PARAM_STR);
				$result2->bindParam(':place_id', $place_id, PDO::PARAM_INT);
				$result2->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			 
			 // once saved, redirect back to the view page
			 header("Location: maps.php"); 
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
	 if (isset($_GET['place_id']) && is_numeric($_GET['place_id']) && $_GET['place_id'] > 0)
	 {
		 // query db
		 $place_id = $_GET['place_id'];
		 try {
				$result = $dbh ->prepare("SELECT * FROM allplaces WHERE place_id=:place_id");
				$result->bindParam(':place_id', $place_id, PDO::PARAM_INT);
				$result->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
			
			//$rows = mysqli_num_rows($query);
			$row = $result->fetch(PDO::FETCH_ASSOC);
		   
		 // check that the 'id' matches up with a row in the databse
		 if($row)
		 {		 
			 // get data from db
			 $name = $row['name'];
			 $description = $row['description'];		  
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
</head>
 
 <body>

 <div id="container">
  
 <div id="maincontent">
 
	<form method="POST" action="">
	<table>
		<thead><tr><th>Edit</th></tr></thead>
		<tbody>
			<tr><td>
				<input type="hidden" name="place_id" value="'; echo $place_id; echo '" />
			</td></tr>
			<tr>
				<td>Name:</td><td><input type="text" name="name" maxlength="90" value="'; echo $name;echo '" /></td>
			</tr>
			<tr>
				<td>Description:</td><td><input type="text" name="description" maxlength="500" value="'; echo $description;echo '" /></td>
			</tr>
			
			<tr><td colspan="2" align="right"><input type="submit" name="submit" value="Submit edits" /></td></tr>
			<tr><td>
				<input type="hidden" name="place_id" value="'; echo $place_id; echo '" />
			</td></tr>
		</tbody>
	</table>
	</form>
 
</div> 
</div> 
</body>
</html>';

?>