<?php
/* user file */
include('session.php');
include('https.php'); //Includes the control file that always redirects to https
?>

<!DOCTYPE html>
<html>
<head>
	<title>About Page</title>
	<link href="style.css" rel="stylesheet" type="text/css">
	   
	<!-- Bootstrap starts here -->
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
</head>
<body>

	<?php include('navBar.php');?>

</br></br>
	<div class="aboutcontainer">
		
		<h1 class="abouth1">About</h1>
        </br>
		  <table class="table table-striped">
			<thead>
			</thead>
			<tbody>
			  <tr>
				<td>Αναπτύχθηκε από:</td>
				<td>Ρέγκου Ελένη</td>				
			  </tr>
			  <tr>
				<td>Επιβλέπων Καθηγητής:</td>
				<td>Δασυγένης Μηνάς</td>				
			  </tr>
			  <tr>
				<td>Τμήμα:</td>
				<td>Μηχανικών Πληροφορικής και Τηλεπικοινωνιών </td>				
			  </tr>
			  <tr>
				<td>Πανεπιστήμιο:</td>
				<td>Πανεπιστήμιο Δυτικής Μακεδονίας</td>				
			  </tr>
			  <tr>
				<td>Έτος:</td>
				<td>2018</td>				
			  </tr>
			  <tr>
				<td>Θέμα Διπλωματικής Εργασίας:</td>
				<td> Σχεδιασμός και Ανάπτυξη Εφαρμογής Android με Υποστηρικτική Ιστοσελίδα </br>για Δημιουργία Ταξιδιωτικού Οδηγού Δυτικής Μακεδονίας</td>				
			  </tr>
			</tbody>
		  </table>
	 
	<!-- Αναπτύχθηκε από: Ρέγκου Ελένη
	 Επιβλέπων Καθηγητής: Δασυγένης Μηνάς
	 Τμήμα: Μηχανικών Πληροφορικής και Τηλεπικοινωνιών 
	 Πανεπιστήμιο Δυτικής Μακεδονίας
	 Έτος: 2018
	 Θέμα Διπλωματικής Εργασίας: Σχεδιασμός και Ανάπτυξη Εφαρμογής Android με Υποστηρικτική Ιστοσελίδα για Δημιουργία Ταξιδιωτικού Οδηγού Δυτικής Μακεδονίας
	 -->
	 
	</div>
	
	
	<?php include 'footer.php';?>
	
		
	<!-- jQuery (necessary for Bootstraps JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</body>
</html>