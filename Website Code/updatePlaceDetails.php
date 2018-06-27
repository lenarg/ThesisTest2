<?php
/* user file */
require('connect.php');

//include_once("post.php.inc");savemsg(json_encode($_FILES));
//include_once("post.php.inc");savemsg(json_encode($_POST));


// check request
if(isset($_POST))
{
    // get values
    $place_id = $_POST['place_id'];
	$name = trim($_POST['name']);
	$description = trim($_POST['description']);
	//$image = 
	
	$remove[] = "'";
	$remove[] = '"';
	$name = str_replace( $remove, "", $name );
	$description = str_replace( $remove, "", $description );
	
	if ( $name == '' || $description == '' )){
		// generate error message
		$error = 'ERROR: Please fill in all required fields!';
		//echo '<script type="text/javascript">alert("$error");</script>';
		/*echo ("<SCRIPT LANGUAGE='JavaScript'> 
		   window.alert('You did not complete all of the required fields') 
		   window.location.href='maps.php' 
		   </SCRIPT>");*/
		
		
	}else{
		echo $name;
		echo $description;
		echo $place_id;
		//include 'locations.php';
		/*if(!empty($_FILES["update_image"]["name"])){
			
			$upload_image = $_FILES["update_image"]["name"]; //to onoma pou apothikeuetai sth db
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
			
			if ( move_uploaded_file( $_FILES["update_image"]["tmp_name"] , $folder.$ihash ) ){ 
			
				try {
					$queryi = $dbh ->prepare("UPDATE allplaces SET pfimage = :pfimage WHERE place_id = :place_id");				
					$queryi->bindParam(':pfimage', $pfimage, PDO::PARAM_STR);
					$queryi->bindParam(':place_id', $place_id, PDO::PARAM_INT);
					$queryi->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}
			}
		}*/
		
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
			
		if (!$query ){//result = $query ){
			exit($dbh->errorInfo());
		}
	}
}

?>
