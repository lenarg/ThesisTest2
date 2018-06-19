<?php 
/* user file */
require('connect.php');
include('session.php');

			if($admin_check == 1 ) {
				//$result2 = mysqli_query($link, "SELECT * FROM allplaces"); 
				try {
					$result2 = $dbh ->prepare("SELECT * FROM allplaces");
					$result2->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}	
			}else{
				//$result2 = mysqli_query($link, "SELECT * FROM allplaces WHERE user_id = '$userid' "); 
				try {
					$result2 = $dbh ->prepare("SELECT * FROM allplaces WHERE user_id = :userid");
					$result2->bindParam(':userid', $userid, PDO::PARAM_INT);
					$result2->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}	
			}
			
				  $place_id = array();
				  $user_id = array();
				  $name = array();
				  $description = array();
				  $type = array();
				  $coordinates = array();
				  $pfimage = array();
				  //while ($row = mysqli_fetch_array($result2)) {  
				while ($row = $result2->fetch(PDO::FETCH_ASSOC)) { 
					$place_id[] = $row['place_id']; 
					$user_id[] = $row['user_id']; 
					$name[] = $row['name']; 
					$description[] = $row['description']; 
					$type[] = $row['type']; 
					$coordinates[] = $row['coordinates']; 
					$pfimage[] = $row['pfimage'];
				} 
		   
		   
?>
