<?php
/* user file */

	$host = '/zstorage/home/ictest00344/mysql/run/mysql.sock';
	$dbname = 'places';
	$user = 'root';
	$pass = 'root';
	
	try{	
		$dbh = new PDO("mysql:unix_socket=$host;dbname=$dbname;charset=utf8", $user, $pass);
		$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
		$dbh->query('set character_set_client=utf8');
		$dbh->query('set character_set_connection=utf8');
		$dbh->query('set character_set_results=utf8');
		$dbh->query('set character_set_server=utf8');
	}
	catch(PDOException $e){
		die('Connection error:' . $e->getmessage()); 
	}
	
?>

