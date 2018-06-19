<?php
require('connect.php');
include('session.php');

//action.php
if(isset($_POST["action"]))
{
	
 if($_POST["action"] == "fetch")
 {
  //$query = "SELECT * FROM tbl_images ORDER BY id DESC";
  //$result = mysqli_query($connect, $query);
  $place_id = 99;
  try {
	  $result = $dbh ->prepare("SELECT pfimage FROM allplaces WHERE place_id=:place_id");
	  $result->bindParam(':place_id', $place_id, PDO::PARAM_INT);		
	  $result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}
  
  $output = '
   <table class="table table-bordered table-striped">  
    <tr>
     <th width="70%">Image</th>
     <th width="10%">Change</th>
     <th width="10%">Remove</th>
    </tr>
  ';   //<img src="data:image/jpeg;base64,'.base64_encode($row['name'] ).'" height="60" width="75" class="img-thumbnail" />
  while($row = $result->fetch(PDO::FETCH_ASSOC))
  {
   $output .= '

    <tr>
     <td>      
	  <img src="placeimgs/'.$row['pfimage'].'" height="60" width="75" class="img-thumbnail" />
     </td>
     <td><button type="button" name="update" class="btn btn-warning bt-xs update" id="'.$place_id.'">Change</button></td>
     <td><button type="button" name="delete" class="btn btn-danger bt-xs delete" id="'.$place_id.'">Remove</button></td> 
    </tr>
   ';
  } //row["place_id"]
  $output .= '</table>';
  echo $output;
 }

 /*if($_POST["action"] == "insert")
 {
  $file = addslashes(file_get_contents($_FILES["image"]["tmp_name"]));
  $query = "INSERT INTO tbl_images(name) VALUES ('$file')";
  if(mysqli_query($connect, $query))
  {
   echo 'Image Inserted into Database';
  }
 }*/
 
 if($_POST["action"] == "update")
 {
	
	  //$file = addslashes(file_get_contents($_FILES["image"]["tmp_name"]));
	  $file = $_FILES["image"]["tmp_name"];
	  
	  $folder = "placeimgs/";   	//"/zstorage/home/ictest00344/public_html/placeimgs/";
			
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
	  //$query = "UPDATE tbl_images SET name = '$file' WHERE id = '".$_POST["image_id"]."'";
	  
	  move_uploaded_file( $_FILES["image"]["tmp_name"] , $folder.$ihash );
	  try {
		  $query = $dbh ->prepare("UPDATE allplaces SET pfimage = :pfimage WHERE place_id = :place_id");
		  $query->bindParam(':pfimage', $ihash, PDO::PARAM_STR);
		  $query->bindParam(':place_id', $place_id, PDO::PARAM_INT);		  
		  $query->execute();
		}
		catch(PDOException $e) {
			echo "Error: " . $e->getMessage();
		}
	  if($query)
	  {
		echo 'Image Updated into Database';
	  }
		
 }
/* if($_POST["action"] == "delete")
 {
  $query = "DELETE FROM tbl_images WHERE id = '".$_POST["image_id"]."'";
  if(mysqli_query($connect, $query))
  {
   echo 'Image Deleted from Database';
  }
 }*/
}
?>