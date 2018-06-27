<?php
/* user file */
require('connect.php');
include ('session.php');
include('https.php'); //Includes the control file that always redirects to https
 
if (isset($_GET['tid']) && is_numeric($_GET['tid']) && $_GET['tid'] > 0)
{
	// query db
	$tour_id = $_GET['tid'];
	//$result = mysqli_query($link, "SELECT * FROM tours WHERE tour_id='$tour_id'")
	//	or die(mysqli_error($link)); 
	//$row = mysqli_fetch_array($result);
	try {
		$result = $dbh ->prepare("SELECT * FROM tours WHERE tour_id=:tour_id");
		$result->bindParam(':tour_id', $tour_id, PDO::PARAM_INT);
		$result->execute();
	}
	catch(PDOException $e) {
		echo "Error: " . $e->getMessage();
	}		
	$row = $result->fetch(PDO::FETCH_ASSOC);
		   
		   
	// check that the 'id' matches up with a row in the databse
	if( $row )
	{		 
		// get data from db	
		$tour_id = $row['tour_id'];
		$user_id = $row['user_id'];
		$tour_name = $row['tour_name'];
		$tour_places = $row['tour_places'];
		$tour_desc = $row['tour_desc'];
		
		$tplaces = explode(";",$row['tour_places']);
	}
	else //if no match, display result
	{
		echo 'No results!';
	}
	
	$j=0;
	for ($i=1;$i<count($tplaces); $i++)
	{
				$plid = $tplaces[$i];
				
				try {
					$result4 = $dbh ->prepare("SELECT * FROM allplaces WHERE place_id=:plid");
					$result4->bindParam(':plid', $plid, PDO::PARAM_INT);
					$result4->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}		
				$row4 = $result4->fetch(PDO::FETCH_ASSOC);
				
				
				if ( $row4['type'] == 3 ){ //circle
					$pcoo = explode(";",$row4['coordinates']);
					
					$pcenter[$j]= $pcoo[2].";".$pcoo[3];
					$j++;
								
				}else if ( $row4['type'] == 2 ){ //rectangle
					$pcoo = explode(";",$row4['coordinates']);
					$clat = ($pcoo[1]+$pcoo[3])/2;
					$clng = ($pcoo[2]+$pcoo[4])/2;
					$pcenter[$j]= $clat.";".$clng;
					$j++;
				}
				
				
	}
	$dist =0;
	for ($i=0;$i<count($pcenter)-1; $i++){
		$pcen = explode(";",$pcenter[$i]); //pcen0 = lat, pcen 1 =lng
		$pcennext = explode(";",$pcenter[$i+1]);
		$earthRadius = 6371000;//meters
		
		// convert from degrees to radians
		  $latFrom = deg2rad($pcen[0]);
		  $lonFrom = deg2rad($pcen[1]);
		  $latTo = deg2rad($pcennext[0]);
		  $lonTo = deg2rad($pcennext[1]);

		  //$latDelta = $latTo - $latFrom;
		  $lonDelta = $lonTo - $lonFrom;

		  $a = pow(cos($latTo) * sin($lonDelta), 2) + pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
		  $b = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);

		  $angle = atan2(sqrt($a), $b);
		  //$angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) + cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
		  $dist = $dist + ($angle * $earthRadius);
	}
	$unit = ' m';
	if ($dist>1000){
		$dist = $dist/1000;
		$unit = ' km';
	}
	$dist= round($dist, 2);
	
}
else // if the 'id' in the URL isn't valid, or if there is no 'id' value, display an error
{
    echo 'Error!';
}

	function GMapCircle($Lat,$Lng,$Rad,$Detail=8){
		 $R    = 6371;

		 $pi   = pi();

		 $Lat  = ($Lat * $pi) / 180;
		 $Lng  = ($Lng * $pi) / 180;
		 $d    = $Rad / $R;

		 $points = array();
		 $i = 0;

		 for($i = 0; $i <= 360; $i+=$Detail):
			   $brng = $i * $pi / 180;

			   $pLat = asin(sin($Lat)*cos($d) + cos($Lat)*sin($d)*cos($brng));
			   $pLng = (($Lng + atan2(sin($brng)*sin($d)*cos($Lat), cos($d)-sin($Lat)*sin($pLat))) * 180) / $pi;
			   $pLat = ($pLat * 180) /$pi;

			   $points[] = array($pLat,$pLng);
		 endfor;

		 require_once('PolylineEncoder.php');
		 $PolyEnc   = new PolylineEncoder($points);
		 $EncString = $PolyEnc->dpEncode();

		 return $EncString['Points'];
	}
	
	
?>

<!DOCTYPE html>
<html>
<head>
	<title>Tour Details</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" language="Javascript">
		<!--hide
		function load(url){
		window.location.href=url;
		}
	</script>
	
	
	
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
	
	<link rel="stylesheet" type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/base/jquery-ui.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<?php include('navBar.php'); 

echo '<div class="container">
		<div id="content">';
			echo '<table id="tourtable" class="table" cellpadding="0" border="0"  class="sortable">';
			echo '<thead>';
			echo '<tr><th><h1><b>Tour View</b></h1></th><tr>';
			echo '<tr><font size="5"><td><b>Tour Name: </b></td><td>'.$row['tour_name'].'</td><td></td><td></td></font></tr>'; 
			
			if($admin_check == 1 ) {
				echo '<tr><font size="5"><td><b>By User: </b></td><td>'.$row['user_id'].'.'; //..'<p>';
				//$usern = mysqli_query($link, "SELECT * FROM users WHERE user_id = '".$row['user_id']."' ");
				try {
					$usern = $dbh ->prepare("SELECT * FROM users WHERE user_id = :user_id");
					$usern->bindParam(':user_id', $row['user_id'], PDO::PARAM_INT);
					$usern->execute();
				}
				catch(PDOException $e) {
					echo 'Error: ' . $e->getMessage();
				}	
				
				while($row2 = $usern->fetch(PDO::FETCH_ASSOC)) {
					echo ' ' . $row2['username'] . '</td><td></td><td></td></font></tr>';
				}
			}
									
			echo '<tr><font size="5"><td><b>Tour Description: </b></td><td>'.$row['tour_desc'].'</td><td></td><td></td></font></tr>';
			
			
			
			echo '<tr><font size="5"><td><b>Aproximate </br>Total Distance: </b></td><td>'.$dist.$unit.'</td><td></td><td></td></font></tr>';
			echo '</thead><tbody>';
			
			//echo '<h2>Tour View</h2>';
			
			
			
			echo '<tr><font size="5"><td><b><u>Tour Places </u></b></td><td></td><td></td><td></td></font></tr>';
			//echo "test1";
			
			for ($i=1;$i<count($tplaces); $i++)
			{
				$plid = $tplaces[$i];				
				try {
					$result2 = $dbh ->prepare("SELECT * FROM allplaces WHERE place_id=:plid");
					$result2->bindParam(':plid', $plid, PDO::PARAM_INT);
					$result2->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}		
				$row3 = $result2->fetch(PDO::FETCH_ASSOC);
				
				
				echo '<tr><td><b>'.$row3['name'].'</b></td><td>'.$row3['description'].'</td>';
				
				if ( $row3['type'] == 3 ){ //circle
					$pcoo = explode(";",$row3['coordinates']);
					
					/* set some options */
					$MapLat    = $pcoo[2]; //'-42.88188'; // latitude for map and circle center
					$MapLng    = $pcoo[3]; //'147.32427'; // longitude as above
					$MapRadius = $pcoo[1]/1000; //100;         // the radius of our circle (in Kilometres)
					
					/* create our encoded polyline string */
					$EncString = GMapCircle($MapLat,$MapLng, $MapRadius);
					//echo '<td>'.$EncString.'</td><td>';
					/* put together the static map URL */
					$MapAPI = 'http://maps.google.com.au/maps/api/staticmap?';
					//$MapURL = $MapAPI.'center='.$MapLat.','.$MapLng.'&size=200x200&maptype=roadmap
					//&path=fillcolor:0x'.$MapFill.'33%7Ccolor:0x'.$MapBorder.'00%7Cenc:'.$EncString.'&sensor=false';
					$MapURL = 'http://maps.google.com.au/maps/api/staticmap?center='.$MapLat.','.$MapLng.'&size=200x200&maptype=roadmap
					&path=color:0xff0000ff|weight:3%7Cenc:'.$EncString.'&sensor=false';
					
					/* output an image tag with our map as the source */
					echo '<td><img src="'.$MapURL.'" /></td><td>';					
								
				}else if ( $row3['type'] == 2 ){ //rectangle
					$pcoo = explode(";",$row3['coordinates']);
					
					echo '<td><img src="https://maps.googleapis.com/maps/api/staticmap?
								&path=color:0xff0000ff|weight:3|'.$pcoo[1].','.$pcoo[2].'|'.$pcoo[1].','.$pcoo[4].'|'.$pcoo[3].','.$pcoo[4].'|'.$pcoo[3].','.$pcoo[2].'|'.$pcoo[1].','.$pcoo[2].'
								&size=200x200&maptype=roadmap
								&key=AIzaSyCRGBjwj3Q_vd5D2O0cWzzv0E_9_iufy5c" ></td><td>';
				}
				if(  $row3['pfimage'] != ''  ){ 
					echo '<img src="placeimgs/'.$row3['pfimage'].'" height="100" > ';
				}
								
				echo '</td></tr>';
				
			}
			echo '</tbody>';
			echo '</table>';
			
			
	echo '</div>
	</div>'; 
	include('footer.php'); ?>
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>
