<?php 
/* user file */
require('connect.php');
    
	try {
		$result = $dbh ->prepare("SELECT * FROM allplaces");
		$result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}    
    //Create an array
    $json_response = array();
    $json_response['allplaces'] = array(); //show the tablename
	
	while ( $row = $result->fetch(PDO::FETCH_ASSOC) ) {
		
        $row_array['place_id'] = $row['place_id'];
        $row_array['user_id'] = $row['user_id'];
        $row_array['name'] = $row['name'];
        $row_array['description'] = $row['description'];
        $row_array['type'] = $row['type'];
        $row_array['coordinates'] = $row['coordinates'];
		$row_array['pfimage'] = $row['pfimage'];
        
        //push the values in the array
        array_push($json_response['allplaces'],$row_array); //here too
    }
    echo json_encode($json_response); 
?>

