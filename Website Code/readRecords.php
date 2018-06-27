<?php
/* user file */
require('connect.php');
include ('session.php');?>

<script>
		var cc = 0;	
		var bsclat = 0;
		var bsclng = 0;
		var bscrad = 0;
		//console.log("cc1 " +cc + "bsclat1 " +bsclat+"bsclng1"+bsclng+"bscrad1"+bscrad);
		$(document).ready(function(){
			$("div.placeTable table").delegate('tr', 'click', function() {
				var id = $(this).children('td');
				//alert("You clicked my <tr>!"+id[0].innerHTML);
				var ppid = id[0].innerHTML;	
				showShape(ppid);
				//get <td> element values here!!??
			});
		});

		function showShape(ppid){  
			//alert(" here");
			
		var j;
		<?php include "locations.php";?>
		
			<?php
			
			echo '								
				var place_id = new Array();
				var user_id = new Array();
				var name = new Array();
				var description = new Array();				
				var type = new Array();
				var coordinates = new Array();				
				var pfimage = new Array(); ';
				
			for ($i=0;$i<count($place_id); $i++)
				{
					echo "place_id[$i]='".$place_id[$i]."';\n";
					echo "user_id[$i]='".$user_id[$i]."';\n";
					echo "name[$i]='".$name[$i]."';\n";
					echo "description[$i]='".$description[$i]."';\n";
					echo "type[$i]='".$type[$i]."';\n";
					echo "coordinates[$i]='".$coordinates[$i]."';\n";
					echo "pfimage[$i]='".$pfimage[$i]."';\n";
				} ?>
			
			for ( j = 0; j < place_id.length; j++) { 
				
				if ( place_id[j] == ppid ){
					var id = j;
					//alert(" id ="+id);
				}
				
			}			  
		//}
								
					
					var str = coordinates[id];
					var coo = str.split(";"); 
					
								
					<?php if($admin_check == 1 ) { 
					
							echo '				
							if(  pfimage[id] == ""  ){ 
								var contentString1 = "<b>Name: </b><br>" + name[id] + "<br><b>Place ID: </b>" + place_id[id] + "<br><b>Description: </b><br>" + description[id] +"<br><b>User: </b><br>" + user_id[id] +"<br><button onclick=\'GetPlaceDetails("+place_id[id]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[id] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[id]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} else {
								var contentString1 = "<b>Name: </b><br>" + name[id] + "<br><b>Place ID: </b>" + place_id[id] + "<br><b>Description: </b><br>" + description[id] +"<br><b>User: </b><br>" + user_id[id] +"<br><img src=\'placeimgs/" + pfimage[id] + "\' width=\'100\' height=\'100\' ><br><button onclick=\'GetPlaceDetails("+place_id[id]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[id] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[id]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							}  ';
					} else {	
							echo '	
							if(  pfimage[id] == ""  ){ 
								var contentString1 = "<b>Name: </b><br>" + name[id] + "<br><b>Place ID: </b>" + place_id[id] + "<br><b>Description: </b><br>" + description[id] +"<br> <button onclick=\'GetPlaceDetails("+place_id[id]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[id] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[id]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} else { 
								var contentString1 = "<b>Name: </b><br>" + name[id] + "<br><b>Place ID: </b>" + place_id[id] + "<br><b>Description: </b><br>" + description[id] +"<br> <img src=\'placeimgs/" + pfimage[id] + "\' width=\'100\' height=\'100\' > <br><button onclick=\'GetPlaceDetails("+place_id[id]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[id] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[id]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} 	';
					
					} ?>

					
					
					//Remove previous chosen shapes
					if (cc!=0){		
							if (prevshapetype == 2){
								conRectangle2.setMap(null);
							}else{
								conCircle2.setMap(null);
							}
						}
						
					if ( type[id] == 2 ){		//rectangle						
						
						// Construct the rectangle.						
						var srlatne = parseFloat(coo[1]);
						var srlngne = parseFloat(coo[2]);
						var srlatsw = parseFloat(coo[3]);
						var srlngsw = parseFloat(coo[4]);
						
						conRectangle2 = new google.maps.Rectangle({
						  html: contentString1,
						  strokeColor: "#0000ff",//"#FF0000",
						  strokeOpacity: 0.8,
						  strokeWeight: 2,
						  fillColor: "#0000ff",//"#FF0000",
						  fillOpacity: 0.35 ,
						  bounds: {
							north: srlatne,
							south: srlatsw,
							east: srlngne,
							west: srlngsw
						  }

						});
						conRectangle2.setMap(map);
						
						var infowpos = new google.maps.LatLng(srlatne, srlngne);
						//console.log("infowpos " +infowpos);
						infoWindow.setContent(contentString1);
						infoWindow.setPosition(infowpos);

						infoWindow.open(map);					
						cc++;
						prevshapetype = 2;						
						google.maps.event.addListener(conRectangle2, "click",
							function (event){
								var contentString1 = this.html;
								infoWindow.setContent(contentString1);
								infoWindow.setPosition(event.latLng);

								infoWindow.open(map);
							}
							);
						
					} else if ( type[id] == 3 ){		//circle						
						
						
						// Construct the circle.
						var scrad = parseFloat(coo[1]);
						var sclat = parseFloat(coo[2]);
						var sclng = parseFloat(coo[3]);
						conCircle2 = new google.maps.Circle({
						  html: contentString1,
						  strokeColor: "#0000ff", //"#FF0000",
						  strokeOpacity: 0.8,
						  strokeWeight: 2,
						  fillColor: "#0000ff",//"#FF0000",
						  fillOpacity: 0.35,
						  center: {lat: sclat, lng: sclng}, // Define the LatLng coordinates for the circle
						  radius: scrad
						});
						conCircle2.setMap(map);
						
						var infowpos = new google.maps.LatLng(sclat, sclng);
						//console.log("infowpos " +infowpos);
						infoWindow.setContent(contentString1);
						infoWindow.setPosition(infowpos);

						infoWindow.open(map);						
						
						cc++;
						prevshapetype = 3;
						
						google.maps.event.addListener(conCircle2, "click",
							function (event){
								var contentString1 = this.html;
								infoWindow.setContent(contentString1);
								infoWindow.setPosition(event.latLng);

								infoWindow.open(map);
							}
							);
					}
				
				
				/*var drawingManager = new google.maps.drawing.DrawingManager({
				  drawingControl: true,
				  drawingControlOptions: {
					position: google.maps.ControlPosition.TOP_CENTER,
					drawingModes: [
					  google.maps.drawing.OverlayType.CIRCLE,
					  google.maps.drawing.OverlayType.RECTANGLE
					]
				  },
				  
				});
				
				drawingManager.setMap(map);			*/
			 
		}
		
		function sortTable(n) {
		  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
		  table = document.getElementById("places_table");
		  switching = true;
		  // Set the sorting direction to ascending:
		  dir = "asc";
		  /* Make a loop that will continue until
		  no switching has been done: */
		  while (switching) {
			// Start by saying: no switching is done:
			switching = false;
			rows = table.getElementsByTagName("TR");
			/* Loop through all table rows (except the
			first, which contains table headers): */
			for (i = 1; i < (rows.length - 1); i++) {
			  // Start by saying there should be no switching:
			  shouldSwitch = false;
			  /* Get the two elements you want to compare,
			  one from current row and one from the next: */
			  x = rows[i].getElementsByTagName("TD")[n];
			  y = rows[i + 1].getElementsByTagName("TD")[n];
			  /* Check if the two rows should switch place,
			  based on the direction, asc or desc: */
			  if (dir == "asc") {
				if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
				  // If so, mark as a switch and break the loop:
				  shouldSwitch= true;
				  break;
				}
			  } else if (dir == "desc") {
				if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
				  // If so, mark as a switch and break the loop:
				  shouldSwitch= true;
				  break;
				}
			  }
			}
			if (shouldSwitch) {
			  /* If a switch has been marked, make the switch
			  and mark that a switch has been done: */
			  rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			  switching = true;
			  // Each time a switch is done, increase this count by 1:
			  switchcount ++;
			} else {
			  /* If no switching has been done AND the direction is "asc",
			  set the direction to "desc" and run the while loop again. */
			  if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			  }
			}
		  }
		}
		
		
	</script>

<?php
	
	if($admin_check == 1 ) {
		//echo "<th onclick='sortTable(0)'>User</th>";
					//echo "<th onclick='sortTable(1)'>Name</th>";
					$data = '<div class="placeTable"><table class="table" cellpadding="4" border="1"  class="sortable" id="places_table">
						<tr>
							<th>id</th>
							<th onclick="sortTable(0)">User</th>
							<th onclick="sortTable(1)">Name</th>
							<th>Description</th>
							
						</tr>';
	}else{
					$data = '<div class="placeTable"><table class="table" cellpadding="4" border="1"  class="sortable" id="places_table">
						<tr>
							<th>id</th>
							<th onclick="sortTable(0)">Name</th>
							<th>Description</th>							
						</tr>';
	}
	// Design initial table header 
	/*$data = '<table class="table" cellpadding="4" border="1"  class="sortable" id="places_table">
						<tr>
							<th>ID</th>
							<th onclick="sortTable(0)">Name</th>
							<th>Description</th>
							<th>Update</th>
							<th>Delete</th>
						</tr>';*/
						
	if($admin_check == 1 ) {
		//$query=mysqli_query($link, "SELECT * FROM allplaces");
		//$query = "SELECT * FROM allplaces";
		try {
				$query = $dbh ->prepare("SELECT * FROM allplaces");
				$query->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
	}else{ 
	    //$query = "SELECT * FROM allplaces";
		//$query = "SELECT * FROM allplaces WHERE user_id='$userid'";
		try {
				$query = $dbh ->prepare("SELECT * FROM allplaces WHERE user_id=:userid");
				$query->bindParam(':userid', $userid, PDO::PARAM_INT);
				$query->execute();
			}
			catch(PDOException $e) {
				echo "Error: " . $e->getMessage();
			}		
	}
							 
	
	//$query = "SELECT * FROM allplaces WHERE user_id='$user_id'";

	if ( !$result = $query ){			//(!$result = mysqli_query($link, $query)) {
        exit($dbh->errorInfo());
    }

    // if query results contains rows then featch those rows 
    if($result)		//(mysqli_num_rows($result) > 0)
    {
    	//$number = 1;
    	while( $row = $result->fetch(PDO::FETCH_ASSOC) )		//($row = mysqli_fetch_assoc($result))
    	{
			if($admin_check == 1 ) {
				//$usern = mysqli_query($link, "SELECT * FROM users WHERE user_id = '".$row['user_id']."' ");
				//$row2 = mysqli_fetch_array( $usern );
				//echo '<td>' . $row2['username'] . '</td>';
				try {
					$usern = $dbh ->prepare("SELECT * FROM users WHERE user_id = :user_id");
					$usern->bindParam(':user_id',$row['user_id'], PDO::PARAM_INT);
					$usern->execute();
				}
				catch(PDOException $e) {
					echo "Error: " . $e->getMessage();
				}					
				$row2 = $usern->fetch(PDO::FETCH_ASSOC);
				
				$data .= '<tr>					
					<td>'.$row['place_id'].'</td>
					<td>'.$row2['username'].'</td>
					<td>'.$row['name'].'</td>
					<td class="tabldesc">'.$row['description'].'</td>
					
				</tr>';
			}else{
				$data .= '<tr>
					<td>'.$row['place_id'].'</td>
					<td>'.$row['name'].'</td>
					<td>'.$row['description'].'</td>					
				</tr>';
			
			}
    		//$number++;
			/*<td>
						<button onclick="GetPlaceDetails('.$row['place_id'].')" class="btn btn-warning btn-xs">Update</button>
						<button onclick="DeletePlace('.$row['place_id'].')" class="btn btn-danger btn-xs">Delete</button>
					</td>*/
    	}
    }
    else
    {
    	// records now found 
    	$data .= '<tr><td colspan="4">Records not found!</td></tr>';
    }

    $data .= '</table></div>';

    echo $data;
?>