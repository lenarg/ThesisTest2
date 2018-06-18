<?php
/* user file */
//error_reporting(E_ALL ^ E_DEPRECATED); // until I change all to mysqlis 

//mysql_connect(':/zstorage/home/ictest00344/mysql/run/mysql.sock', 'root', 'root');
//mysql_select_db('places');

//mysqli_connect( 'host', 'username', 'password', 'database', 'port', 'socket');


//function db_open(){
	// connection information
	$host = '/zstorage/home/ictest00344/mysql/run/mysql.sock';
	$dbname = 'places';
	$user = 'root';
	$pass = 'root';
	ini_set('display_errors', 1);
	//echo "here1";
	// connect to database or return error
	try{
		//echo "here2";
		$dbh = new PDO("mysql:unix_socket=$host;dbname=$dbname;charset=utf8", $user, $pass);
		$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
		$dbh->query('set character_set_client=utf8');
		$dbh->query('set character_set_connection=utf8');
		$dbh->query('set character_set_results=utf8');
		$dbh->query('set character_set_server=utf8');
	}
	catch(PDOException $e){
		//echo "here3";
		die('Connection error:' . $e->getmessage()); 
		//echo 'Exception -> ';
		//var_dump($e->getMessage());
		//$dbh->null;
	}
	//return $dbh;
	
//}

/*

try{
	$db = new PDO('mysql:host=127.0.0.1;port=13691;dbname=places;charset=utf8','root','root');
	
	var_dump($db);
}

catch(Exception $e){
	echo $e->getMessage();
	//echo $e;
}*/


/*
$link = mysqli_connect( '127.0.0.1', 'root', 'root', 'places', '13691', '/zstorage/home/mdasyg/mysql/run/mysql.sock');*/


?>