<?php
/* user file */
require 'connect.php';
include 'session.php';
include('https.php'); //Includes the control file that always redirects to https

if(isset($_POST['save_region'])){
	
	$coords = $_POST['coords'];
	
	if(!empty($_POST['region_name']) AND !empty($_POST['region_desc'])) 
	{
		if(empty($_FILES["myimage"]["name"])){
			$upload_image = null;
			$ihash = null;			
		}else{
			$upload_image = $_FILES["myimage"]["name"]; 
			$folder = "placeimgs/";   	
			
			//Checks if the ihash already exists then creates another
			include 'locations.php';
					
			$i = 0; $j = 0;
			
			$ihash = md5(rand());
			
			while( $j == 0 ){
				$k = 0;
				for($i=0; $i<count($pfimage); $i++ ){
					if ( $ihash == $pfimage[$i] ){
						$k++;
					}
				}
				if ($k == 0){
					$j = 1;
				} else {
					$ihash = hash_file( 'md5', rand() );  
				}
			}			
			move_uploaded_file( $_FILES["myimage"]["tmp_name"] , $folder.$ihash ); 
		}
			// Define $username and $password			
			$rname=$_POST['region_name'];
			$rdesc=$_POST['region_desc'];
			
			// To protect MySQL injection for Security purpose			
			$rname = stripslashes($rname);
			$rdesc = stripslashes($rdesc);
				
			$remove[] = "'";
			$remove[] = '"';
			$rname = str_replace( $remove, "", $rname );
			$rdesc = str_replace( $remove, "", $rdesc );
					
			try {
				$query = $dbh ->prepare("INSERT INTO allplaces (user_id,name,description,type,coordinates,pfimage) 
							VALUES (?,?,?,?,?,?)");
				
				$query->execute(array($userid,$rname,$rdesc,$_POST[region_type],$coords,$ihash)); 
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}

			if ( $query ) {
				header('Location: maps.php');
					
			} else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Error: A new record was NOT created!') 
					window.location.href='maps.php' 
					</SCRIPT>");
			}		
	}
	else
	{
		echo ("<SCRIPT LANGUAGE='JavaScript'> 
		   window.alert('You did not complete all of the required fields') 
		   window.location.href='maps.php' 
		   </SCRIPT>");
	}
}	
/*if(isset($_POST['update_coords'])){
	
	$upcoords = $_POST['upcoords'];
	$place_id = $_POST['pid'];//112;	
					
			try {
				$query = $dbh ->prepare("UPDATE allplaces SET coordinates=:coordinates WHERE place_id=:place_id");
				$query->bindParam(':coordinates', $upcoords, PDO::PARAM_STR);
				$query->bindParam(':place_id', $place_id, PDO::PARAM_INT);
				$query->execute();
				 
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}

			if ( $query ) {
				header('Location: maps.php');
					
			} else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Error: Coordinates were NOT created!') 
					window.location.href='maps.php' 
					</SCRIPT>");
			}		
	
}*/
?>






