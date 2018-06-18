<?php
/* user file */
require('connect.php');

// check request
if(isset($_POST))
{
    // get values
    $place_id = $_POST['place_id'];
	$name = trim($_POST['name']);
	$description = trim($_POST['description']);
	
	$remove[] = "'";
	$remove[] = '"';
	$name = str_replace( $remove, "", $name );
	$description = str_replace( $remove, "", $description );
	
	if ($name == '' || $description == ''){
		// generate error message
		$error = 'ERROR: Please fill in all required fields!';
		//echo '<script type="text/javascript">alert("$error");</script>';
		
	}else{
		// Updaste Place details
		//$query = "UPDATE allplaces SET name = '$name', description = '$description' WHERE place_id = '$place_id'";
		//if (!$result = mysqli_query($link, $query)) {
		//	exit(mysqli_error($link));
		//}
		try {
			$query = $dbh ->prepare("UPDATE allplaces SET name = :name, description = :description WHERE place_id = :place_id");
			$query->bindParam(':name', $name, PDO::PARAM_STR);
			$query->bindParam(':description', $description, PDO::PARAM_STR);
			$query->bindParam(':place_id', $place_id, PDO::PARAM_INT);
			$query->execute();
		}
		catch(PDOException $e) {
			echo "Error: " . $e->getMessage();
		}	
		if (!$result = $query ){
			exit($dbh->errorInfo());
		}
	}
}

?>