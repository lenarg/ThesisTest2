<?php 
/* user file */
require('connect.php');
include('session.php');

if($admin_check == 1 ) {
				try {
					$result2 = $dbh ->prepare("SELECT * FROM allplaces");
					$result2->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}	
}else{
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


