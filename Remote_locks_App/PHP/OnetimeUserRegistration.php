<?php
ob_start();
session_start();

$onetimeusername = $_POST['username'];
$password = $_POST['password'];
$duration = $_POST['duration'];
$pimacid = $_POST['pimacid'];
$piprocid = $_POST['piprocid'];

$md5 = md5($password);

$piserialno = $pimacid.$piprocid;
$mainusername = $_SESSION['username'];

echo $piserialno;
echo $mainusername;

$currtime = date('Y-m-d H:i:s');
echo $currtime;

$flag = 0;

if ($onetimeusername == '')
{
	header("location:registerOnetimeUser.php?message=Enter+username");
}
elseif ($password == '')
{
	header("location:registerOnetimeUser.php?message=Enter+Password");
}
elseif($duration == '')
{
	header("location:registerOnetimeUser.php?message=Enter+Duration");
}

elseif($pimacid == '')
{
	header("location:registerOnetimeUser.php?message=Enter+RaspberryPi+MACID");
}

elseif($piprocid == '')
{
	header("location:registerOnetimeUser.php?message=Enter+RaspberryPi+PROCESSORID");
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
	
	$piquery = "select id from raspberrypi where serialno = '$piserialno'";
	
	$piret = $db->query($piquery);
	
	while($row = $piret->fetchArray(SQLITE3_NUM))
	{
		$GLOBALS['piserialexists'] = 1;
	}
	
	
	if($GLOBALS['piserialexists'] == 1)
	{
		
		$query = "insert into OneTimeUser (USERNAME,PASSWORD,STARTTIME,FLAG,DURATION,SERIALNO,ADDEDBY) values ".
				"('$onetimeusername', '$md5', '$currtime', $flag, $duration, '$piserialno','$mainusername')";
		
		echo $query;
		
		$ret = $db->exec($query);
		
		if($ret == ''){
			echo $db->lastErrorMsg();
		}
		else {
			header("location:Home.php?message=Successfully+Added+Onetime+user");
		}
		
	}
	
	else {
		echo $db->lastErrorMsg();
		header("location:registerOnetimeUser.php?message=Invalid+RaspberryPi");
		
	}
		
		
}
	
	


ob_end_flush();

?>