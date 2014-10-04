<?php
ob_start();


// Values sent from form 

$username = $_POST['username'];
$password1 = $_POST['password1'];
$password2 = $_POST['password2'];
$deviceid = $_POST['deviceid'];

$md5 = md5($password1);


if ($username == '')
{
	header("location:registerUser.php?message=Enter+username");
}
elseif ($password1 == '')
{
	header("location:registerUser.php?message=Enter+Password");
}
elseif($password1 != $password2)
{
	header("location:registerUser.php?message=Passwords+Mismatch");
}


else {
	
	
	class ConnectDB extends SQLite3
	{
		function __construct()
		{
			$this->open('/home/acn/edu/SerialValidation/acngroup10');
		}
	}
	
	$db = new ConnectDB();
	
	if(!$db){
		echo $db->lastErrorMsg();
	} else {
		echo "Connected to database successfully \n";
	}
	
	$query = "insert into androidclients (USERNAME,PASSWORD,DEVICEID) values ('$username', '$md5', '$deviceid')";
	
    
	$ret = $db->exec($query);
	if(!$ret)
	{
		echo $db->lastErrorMsg();
		header("location:index.php?message=Database+Insertion+Failed");
	} 
	else 
	{
		echo "Records Inserted successfully\n";
		header("location:index.php?message=Successfully+Inserted+Record+into+Database");
	}
	$db->close();
}
ob_end_flush();
?>