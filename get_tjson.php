<?php 
/* user file */
require('connect.php');
//include('https.php'); //Includes the control file that always redirects to https

    
    //Replace * in the query with the column names.
    //$result = mysqli_query($link, "select * from tours");  
	try {
		$result = $dbh ->prepare("SELECT * FROM tours");
		$result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}
    
    //Create an array
    $json_response = array();
    $json_response['tours'] = array(); //show the tablename
	
    while ( $row = $result->fetch(PDO::FETCH_ASSOC) ) {
        $row_array['tour_id'] = $row['tour_id'];
        $row_array['user_id'] = $row['user_id'];
        $row_array['tour_name'] = $row['tour_name'];
        $row_array['tour_desc'] = $row['tour_desc'];
        $row_array['tour_places'] = $row['tour_places'];
        
        //push the values in the array
        array_push($json_response['tours'],$row_array); //here too
    }
    echo json_encode($json_response);
 
?>