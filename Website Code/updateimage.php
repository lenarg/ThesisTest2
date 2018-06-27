<?php
require('connect.php');
include('session.php');

if (isset($_POST['update_image']))
 {
	 // confirm that the 'id' value is a valid integer before getting the form data
	 if (is_numeric($_POST['id']))
	 {	 
		$pid = $_POST['id'];
		$pname = $_POST['name'];
		
		//echo $place_id;
		if(empty($_FILES["myimage2"]["name"])){
			echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('You have not chosen an image!') 
					window.location.href='maps.php' 					
					</SCRIPT>");
					
			
		}else{
			$upload_image = $_FILES["myimage2"]["name"]; 
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
			move_uploaded_file( $_FILES["myimage2"]["tmp_name"] , $folder.$ihash ); 
		
		//$place_id=103;
		
			try {
				$query = $dbh ->prepare("UPDATE allplaces SET pfimage=:pfimage WHERE place_id=:place_id");				
				$query->bindParam(':pfimage', $ihash, PDO::PARAM_STR);//$ihash
				$query->bindParam(':place_id', $pid, PDO::PARAM_INT);	
				$query->execute(); 
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}

			if ( $query ) {
				$pfimage= $ihash;
				$name = $pname;
				header('Location: maps.php');
					//echo $place_id;
			} else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Error: A new record was NOT created!') 
					window.location.href='maps.php' 
					</SCRIPT>");
			}		
		}		
	 }

	else
	 {
		 // if the 'id' isn't valid, display an error
		 echo 'Error in id!';
	 }
 
 }
 else if (isset($_POST['delete_image']))
 {
	 if (is_numeric($_POST['id']))
	 {	
			$pid = $_POST['id'];
			$pname = $_POST['name'];
			 $pfimage =null;
			// delete the entry
			//$result1= mysqli_query($link, "DELETE FROM users WHERE user_id='$user_id'");
			try {
				$query = $dbh ->prepare("UPDATE allplaces SET pfimage=:pfimage WHERE place_id=:place_id");				
				$query->bindParam(':pfimage', $pfimage, PDO::PARAM_STR);//$ihash
				$query->bindParam(':place_id', $pid, PDO::PARAM_INT);	
				$query->execute(); 
			}
				catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}			 
			 
			if ( $query ) {
				//$pfimage= $ihash;
				$name = $pname;
				$pfimage =null;
				header('Location: maps.php');
					//echo $place_id;
			} else {
				echo ("<SCRIPT LANGUAGE='JavaScript'> 
					window.alert('Error: The image is not deleted!') 
					window.location.href='maps.php' 
					</SCRIPT>");
			}

			
	 } else {   // if id isn't set, or isn't valid, redirect back to view page
			//header("Location: adminusers.php");
	 }
 }
 else
 // if the form hasn't been submitted, get the data from the db and display the form
 {

	if (isset($_GET['id']) && is_numeric($_GET['id']) && $_GET['id'] > 0)
	 {
		$place_id = $_GET['id'];
		
		try {
					$result = $dbh ->prepare("SELECT name, pfimage FROM allplaces WHERE place_id=:place_id");
					$result->bindParam(':place_id', $place_id, PDO::PARAM_INT);
									
					$result->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}	
				
				$row = $result->fetch(PDO::FETCH_ASSOC);
				
				if($row)
				{		 
					// get data from db	
					$pfimage= $row['pfimage']; 
					$name= $row['name']; 
			 
				}
	 }

 }
?>
<!DOCTYPE html>
<html>
<head>
	<title>Image of <?php  echo $name; ?></title>
	<link href="style.css" rel="stylesheet" type="text/css">
	   
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>

<?php include('navBar.php');?>

	
	<div class="container">
		<br/><br/>
		 <p><b><h3><?php echo $name; ?></h3></b>
		 <form action="" method="POST" enctype="multipart/form-data">
		 <input type="hidden" name="id" value="<?php echo $place_id; ?>" />
		 <input type="hidden" name="name" value="<?php echo $name; ?>" />
			<div class="row">
			<br/>
			</div>
			<div class="row">
				<div class="col-md-3"><input type="file" class="form-control-file" name="myimage2"/></div>
			</div>
			<div class="row">
			<br/>
			</div>
			<div class="row">
				<div class="col-md-2"><input type="submit" value="Update Image" name="update_image"></p></div>
				<!--<div class="col-md-6"><a class="btn btn-danger" a href="delete.php?id=' . $row['user_id'] . '" onclick="return confirm(\'Delete image?\');">Delete</a></div>-->
				<div class="col-md-2"><input type="submit" value="Delete Image" name="delete_image"  onclick="return confirm('Delete image?');"></div>
				<div class="col-md-8"></div>
			</div>
			
		</form>
		<br/>
		<?php if ($pfimage!=null){?>
			<img src='placeimgs/<?php  echo $pfimage; ?>' >
		<?php }?>
		
		
		
	</div>
	
	<?php  include 'footer.php';?>
	
		
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>