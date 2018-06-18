<?php
/* user file */
require('connect.php');
include ('session.php');?>

<script>
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
					$data = '<table class="table" cellpadding="4" border="1"  class="sortable" id="places_table">
						<tr>
							<th onclick="sortTable(0)">User</th>
							<th onclick="sortTable(1)">Name</th>
							<th>Description</th>
							<th>Buttons</th>
						</tr>';
	}else{
					$data = '<table class="table" cellpadding="4" border="1"  class="sortable" id="places_table">
						<tr>
							<th onclick="sortTable(0)">Name</th>
							<th>Description</th>
							<th>Buttons</th>
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
					<td>'.$row2['username'].'</td>
					<td>'.$row['name'].'</td>
					<td>'.$row['description'].'</td>
					<td>
						<button onclick="GetPlaceDetails('.$row['place_id'].')" class="btn btn-warning btn-sm">Update</button>
						<button onclick="DeletePlace('.$row['place_id'].')" class="btn btn-danger btn-sm">Delete</button>
					</td>
				</tr>';
			}else{
				$data .= '<tr>
					<td>'.$row['name'].'</td>
					<td>'.$row['description'].'</td>
					<td>
						<button onclick="GetPlaceDetails('.$row['place_id'].')" class="btn btn-warning btn-sm">Update</button>
						<button onclick="DeletePlace('.$row['place_id'].')" class="btn btn-danger btn-sm">Delete</button>
					</td>
				</tr>';
			
			}
    		//$number++;
    	}
    }
    else
    {
    	// records now found 
    	$data .= '<tr><td colspan="4">Records not found!</td></tr>';
    }

    $data .= '</table>';

    echo $data;
?>