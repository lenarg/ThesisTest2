<?php
/* user file */
require 'connect.php';
include 'session.php';
include('https.php'); //Includes the control file that always redirects to https

if(isset($_POST['save_region']))
{
	
	$coords = $_POST['coords'];
	//$pimage = $_FILES['image'];
	
	if(!empty($_POST['region_name']) AND !empty($_POST['region_desc'])) 
	{
		if(empty($_FILES["myimage"]["name"])){
			$upload_image = null;
			$ihash = null;			
		}else{
			$upload_image = $_FILES["myimage"]["name"]; //to onoma pou apothikeuetai sth db
			$folder = "placeimgs/";   	//"/zstorage/home/ictest00344/public_html/placeimgs/";
			
			//Checks if the ihash already exists then creates another
			include 'locations.php';
					
			$i = 0; $j = 0;
			
			$ihash = md5(rand());//hash_file( 'md5', rand() );  //('sha256', $_FILES["myimage"]["tmp_name"]);
			
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
					$ihash = hash_file( 'md5', rand() );  //$ihash = hash_file('sha256', $_FILES["myimage"]["tmp_name"]);
				}
			}
			
			//prepei na to kanw move me onoma to hash
			move_uploaded_file( $_FILES["myimage"]["tmp_name"] , $folder.$ihash ); //"$folder" );//.$ihash );//$_FILES["myimage"]["name"] ); //filename, destination //$_FILES["myimage"]["tmp_name"]
			
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
				$query = $dbh ->prepare("INSERT INTO allplaces (user_id,name,description,type,coordinates,pimage,pfimage) 
							VALUES (?,?,?,?,?,?,?)");
				
				$query->execute(array($userid,$rname,$rdesc,$_POST[region_type],$coords,$upload_image,$ihash));
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
	
?>


