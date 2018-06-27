<?php 
/* user file */
include('session.php');
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
  <head>	
    <title>Χάρτης Δυτικής Μακεδονίας</title>    
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
	<!-- <meta charset="utf-8"> -->
	<link href="style.css" rel="stylesheet" type="text/css"> 
        	
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
  </head>
  <body>
  
 <?php include('navBar.php'); ?>
 
 <!-- Modal - Update Place details -->
<div class="modal fade" id="update_place_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Update</h4>
            </div>
            <div class="modal-body">
 
                <div class="form-group">
                    <label for="update_name">Name</label>
                    <input type="text" id="update_name" placeholder="Name" class="form-control"/>
                </div>
 
                <div class="form-group">
                    <label for="update_description">Description</label>
                    <!--<input type="text" id="update_description" placeholder="Description" class="form-control"/>-->
					<textarea id="update_description" placeholder="Description" class="form-control" rows="3"></textarea>
                </div>
				
				<!--<div class="form-group">
                    <label for="update_image">Image</label>
                    <!--<input type="text" id="update_description" placeholder="Description" class="form-control"/>-->
					<!--<input type="file" name="upimage" placeholder="Image" />-->
					<!--<input type="file" id="update_image" placeholder="Image" class="form-control" />
					<!--<table class="table table-bordered table-striped">  
						<tr>
							 <td id="showsavedimage">    </td>
							 <td><button type="button" onclick="UpdateImage()" name="update" class="btn btn-warning bt-xs update" id="img_chng_btn">Change</button></td>
							 <td><button type="button" onclick="DeleteImage()" name="delete" class="btn btn-danger bt-xs delete" id="img_rmv_btn">Remove</button></td> 
						</tr>
					</table>
					
                </div>-->
				
 
                <!--<div class="form-group">
                    <label for="update_email">Email Address</label>
                    <input type="text" id="update_email" placeholder="Email Address" class="form-control"/>
                </div>-->
 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="UpdatePlaceDetails()" >Save Changes</button>
                <input type="hidden" id="hidden_place_id">
            </div>
        </div>
    </div>
</div>
<!-- // Modal -->
  
	<div id="sidebar">
			
		<div class="panel panel-default">
			  <div class="panel-heading">
				<center><h3 class="panel-title">Saved Regions</h3></center>
			  </div>
			  <div class="records_content">

				
				<?php //include 'placestable.php';?>
								<!-- Jquery JS file -->
				<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
				 
				<!-- Bootstrap JS file -->
				<script type="text/javascript" src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
				<!-- Custom JS file -->
				<script type="text/javascript" src="script.js"></script>
				
				<script src="//code.jquery.com/jquery-1.12.4.min.js"
								integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
								crossorigin="anonymous"></script>
			  </div>
		</div>
	</div>
			  
		</div>
		<div id="map"> 						
			<script>
			  
			  function initMap() {
				var contents;
				infoWindow = new google.maps.InfoWindow({
				  content: contents
				});

			  
				map = new google.maps.Map(document.getElementById("map"), {
				  center: {lat: 40.383210, lng: 21.483071},
				  scaleControl: true,
				  zoom: 10
				});
				
				//SHOW AREAS SHAPES -MAP 
				  
				<?php include "locations.php";?>
					
											
				var place_id = new Array();
				var user_id = new Array();
				var name = new Array();
				var description = new Array();				
				var type = new Array();
				var coordinates = new Array();				
				var pfimage = new Array(); 
				
				 <?php
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
				
				
				var infowindow = new google.maps.InfoWindow();

				var i;  

				for (i = 0; i < coordinates.length; i++) {  					
					
					var str = coordinates[i];
					var coo = str.split(";");  
					<?php
					if($admin_check == 1 ) { 
					
						echo '				
							if(  pfimage[i] == ""  ){ 
								var contentString = "<b>Name: </b><br>" + name[i] + "<br><b>Place ID: </b>" + place_id[i] + "<br><b>Description: </b><br>" + description[i] +"<br><b>User: </b><br>" + user_id[i] +"<br><button onclick=\'GetPlaceDetails("+place_id[i]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[i] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[i]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} else {
								var contentString = "<b>Name: </b><br>" + name[i] + "<br><b>Place ID: </b>" + place_id[i] + "<br><b>Description: </b><br>" + description[i] +"<br><b>User: </b><br>" + user_id[i] +"<br><img src=\'placeimgs/" + pfimage[i] + "\' width=\'200\' height=\'200\' ><br><button onclick=\'GetPlaceDetails("+place_id[i]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[i] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[i]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} '; 
					} else { 
						echo '					
							if(  pfimage[i] == ""  ){ 
								var contentString = "<b>Name: </b><br>" + name[i] + "<br><b>Place ID: </b>" + place_id[i] + "<br><b>Description: </b><br>" + description[i] +"<br> <button onclick=\'GetPlaceDetails("+place_id[i]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[i] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[i]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} else { 
								var contentString = "<b>Name: </b><br>" + name[i] +"<br><b>Place ID: </b>" + place_id[i] + "<br><b>Description: </b><br>" + description[i] +"<br> <img src=\'placeimgs/" + pfimage[i] + "\' width=\'200\' height=\'200\' > <br><button onclick=\'GetPlaceDetails("+place_id[i]+")\' class=\'btn btn-warning btn-sm\'>Update</button>&nbsp;<a href=\'updateimage.php?id=" + place_id[i] + "\' class=\'btn btn-default btn-sm\'>Image</a>&nbsp;<button onclick=\'DeletePlace("+place_id[i]+")\' class=\'btn btn-danger btn-sm\'>Delete</button><br>"; 
							} 	'; 
					
					}            ?>                                                                          
						
					
					if ( type[i] == 2 ){		//rectangle
						
						// Construct the rectangle.
						
						var srlatne = parseFloat(coo[1]);
						var srlngne = parseFloat(coo[2]);
						var srlatsw = parseFloat(coo[3]);
						var srlngsw = parseFloat(coo[4]);
						
						var conRectangle = new google.maps.Rectangle({
						  html: contentString,
						  id: place_id[i],
						  strokeColor: "#FF0000",
						  strokeOpacity: 0.8,
						  strokeWeight: 2,
						  fillColor: "#FF0000",
						  fillOpacity: 0.35 ,
						  editable: true,
						  bounds: {
							north: srlatne,
							south: srlatsw,
							east: srlngne,
							west: srlngsw
						  }

						});
						conRectangle.setMap(map);
						
						google.maps.event.addListener(conRectangle, "click",
							function (event){
								var contentString = this.html;
								infoWindow.setContent(contentString);
								infoWindow.setPosition(event.latLng);

								infoWindow.open(map);
							}
							);
							
						google.maps.event.addListener(conRectangle, "bounds_changed",
							function (event){
								console.log('Bounds changed.');
								var pid = this.id;
								var ne = this.getBounds().getNorthEast();
								var sw = this.getBounds().getSouthWest();
								var nelat = ne.lat();
								var nelng = ne.lng();
								var swlat = sw.lat();
								var swlng = sw.lng();
					
								var upcoordsrec = ";" + nelat.toFixed(6) + ";" + nelng.toFixed(6)+ ";" + swlat.toFixed(6) + ";" + swlng.toFixed(6);
								console.log(upcoordsrec);
	
								contentsr = '<form action="SaveData.php" method="POST" enctype="multipart/form-data"><input type="hidden" name="pid" type="text" size="50" value="'+pid+'"/><input type="hidden" name="upcoords" type="text" size="50" value="'+upcoordsrec+'"/><input type="hidden" name="region_type" value="2"><center><br/><input type="submit" value="Update Coordinates" name="update_coords"></center></form>'; 
					
								var boundsr = new google.maps.LatLng(ne.lat(), ne.lng());
							
								infoWindow.setContent(contentsr);
								infoWindow.setPosition(boundsr); 
								drawingManager.setDrawingMode(null);
								infoWindow.open(map);
					
					
							}
							);
						
					} else if ( type[i] == 3 ){		//circle
						
						// Construct the circle.
						
						var scrad = parseFloat(coo[1]);
						var sclat = parseFloat(coo[2]);
						var sclng = parseFloat(coo[3]);
						var conCircle = new google.maps.Circle({
						  html: contentString,
						  id: place_id[i],
						  strokeColor: "#FF0000",
						  strokeOpacity: 0.8,
						  strokeWeight: 2,
						  fillColor: "#FF0000",
						  fillOpacity: 0.35,
						  editable: true,
						  center: {lat: sclat, lng: sclng}, // Define the LatLng coordinates for the circle
						  radius: scrad
						});
						conCircle.setMap(map);
						
						google.maps.event.addListener(conCircle, "click",
							function (event){
								var contentString = this.html;
								infoWindow.setContent(contentString);
								infoWindow.setPosition(event.latLng);

								infoWindow.open(map);
							}
							);
							
						google.maps.event.addListener(conCircle, "radius_changed",
							function (event){
								console.log('radius changed.' + this.getRadius());
								var pid = this.id;
								var radius = this.getRadius();//conCircle.getRadius();
								var center = this.getCenter();//conCircle.getCenter();
								var clat = center.lat();
								var clng = center.lng();
								var upcoordscyc = ";" + radius.toFixed(6) + ";" + clat.toFixed(6) + ";" + clng.toFixed(6);
								console.log(upcoordscyc);
								contentsc = '<form action="SaveData.php" method="POST" enctype="multipart/form-data"><input type="hidden" name="pid" type="text" size="50" value="'+pid+'"/><input type="hidden" name="upcoords" type="text" size="50" value="'+upcoordscyc+'"/><input type="hidden" name="region_type" value="3"><center><br/><input type="submit" value="Update Coordinates" name="update_coords"></center></form>'; 
								var boundsc = new google.maps.LatLng(center.lat(), center.lng());
								
								infoWindow.setContent(contentsc);
								infoWindow.setPosition(boundsc); 
								drawingManager.setDrawingMode(null);
								infoWindow.open(map);

							}
							);
							
						google.maps.event.addListener(conCircle, "center_changed",
							function (event){
								console.log('center changed.' + conCircle.getCenter());
								var pid = this.id;
								var radius = this.getRadius();//conCircle.getRadius();
								var center = this.getCenter();//conCircle.getCenter();
								var clat = center.lat();
								var clng = center.lng();
								var upcoordscyc = ";" + radius.toFixed(6) + ";" + clat.toFixed(6) + ";" + clng.toFixed(6);
								console.log(upcoordscyc);
								contentsc = '<form action="SaveData.php" method="POST" enctype="multipart/form-data"><input type="hidden" name="pid" type="text" size="50" value="'+pid+'"/><input type="hidden" name="upcoords" type="text" size="50" value="'+upcoordscyc+'"/><input type="hidden" name="region_type" value="3"><center><br/><input type="submit" value="Update Coordinates" name="update_coords"></center></form>'; 
								var boundsc = new google.maps.LatLng(center.lat(), center.lng());
								
								infoWindow.setContent(contentsc);
								infoWindow.setPosition(boundsc); 
								drawingManager.setDrawingMode(null);
								infoWindow.open(map);
							}
							);
							
					}
				}	
				
				var drawingManager = new google.maps.drawing.DrawingManager({
				  drawingControl: true,
				  drawingControlOptions: {
					position: google.maps.ControlPosition.TOP_CENTER,
					drawingModes: [
					  google.maps.drawing.OverlayType.CIRCLE,
					  google.maps.drawing.OverlayType.RECTANGLE
					]
				  },
				  
				});
				
				drawingManager.setMap(map);
			
				
				google.maps.event.addListener(drawingManager, "rectanglecomplete", function(rectangle) {
				
					var ne = rectangle.getBounds().getNorthEast();
					var sw = rectangle.getBounds().getSouthWest();
					var nelat = ne.lat();
					var nelng = ne.lng();
					var swlat = sw.lat();
					var swlng = sw.lng();
					
					var coordsrec = ";" + nelat.toFixed(6) + ";" + nelng.toFixed(6)+ ";" + swlat.toFixed(6) + ";" + swlng.toFixed(6);
					console.log(coordsrec);
	
					contentsr = '<form action="SaveData.php" method="POST" enctype="multipart/form-data"><input type="hidden" name="coords" type="text" size="50" value="'+coordsrec+'"/><b>Region Name : </b><br/><input type="text" size="20" name="region_name"/><input type="hidden" name="region_type" value="2"><br/><b>Description : </b><br/><textarea name="region_desc" cols="20" rows="3"></textarea><br/><b>Image : </b><br/><input type="file" name="myimage"><br/><center><br/><input type="submit" value="Save Region" name="save_region"></center></form>'; 
					
					var boundsr = new google.maps.LatLng(ne.lat(), ne.lng());
				
					infoWindow.setContent(contentsr);
					infoWindow.setPosition(boundsr); 
					drawingManager.setDrawingMode(null);
					infoWindow.open(map);
				
				});

				google.maps.event.addListener(drawingManager, "circlecomplete", function(circle) {
					var radius = circle.getRadius();
					var center = circle.getCenter();
					var clat = center.lat();
					var clng = center.lng();
					var coordscyc = ";" + radius.toFixed(6) + ";" + clat.toFixed(6) + ";" + clng.toFixed(6);
					console.log(coordscyc);
					contentsc = '<form action="SaveData.php" method="POST" enctype="multipart/form-data"><input type="hidden" name="coords" type="text" size="50" value="'+coordscyc+'"/><b>Region Name : </b><br/><input type="text" size="20" name="region_name"/><input type="hidden" name="region_type" value="3"><br/><b>Description : </b><br/><textarea name="region_desc" cols="20" rows="3"></textarea><br/><br/><b>Image : </b><br/><input type="file" name="myimage"><br/><center><br/><input type="submit" value="Save Region" name="save_region"></center></form>'; 
					var boundsc = new google.maps.LatLng(center.lat(), center.lng());
					
					infoWindow.setContent(contentsc);
					infoWindow.setPosition(boundsc); 
					drawingManager.setDrawingMode(null);
					infoWindow.open(map);
				}); 
				
				/*google.maps.event.addListener(conCircle, 'radius_changed', function() {
				  console.log('radius changed.' + conCircle.getRadius());
				});
				
				google.maps.event.addListener(conCircle, 'center_changed', function() {
				  console.log('center changed.' + conCircle.getCenter());
				});
				
				google.maps.event.addListener(conRectangle, 'bounds_changed', function() {
				  console.log('Bounds changed.');
				});
*/
				
				
			  }
			</script>
		</div> 
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDRA3MU0N6jNYF7Jkj_VzYGMcxQ8XNkg_0&libraries=drawing&callback=initMap"
         async defer></script>  
		 
	<?php include('footer.php'); ?>
	 
	 <!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  </body>
</html>

