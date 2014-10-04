<?php
ob_start();
session_start();

// Values sent from form 

$username = $_SESSION['username'];
$pimacid = $_POST['pimacid'];
$piprocid = $_POST['piprocid'];

$piserialno = $pimacid.$piprocid;

// echo "SESSION :".$_SESSION['username'];

if ($pimacid == '')
{
	header("location:registerPi.php?message=Enter+RaspberryPi+MAC+Id");
}
elseif ($piprocid == '')
{
	header("location:registerPi.php?message=Enter+RaspberryPi+Processor+Id");
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
	
	$userquery = "select id from androidclients where username = '$username'";
	
	$userret = $db->query($userquery);
	
	while($row = $userret->fetchArray(SQLITE3_NUM))
	{
		echo 'hi';
		$GLOBALS['userid'] = $row[0];
	}
	
	
	if($GLOBALS['userid'] == '')
	{
		echo $db->lastErrorMsg();
		header("location:registerPi.php?message=Invalid+username");
	}
	
	else 
	{
		
		$piexistsquery = "select count(*) from Raspberrypi where serialno = '$piserialno'";
		 
		
		$existsret = $db->query($piexistsquery);
		 
		while($row = $existsret->fetchArray(SQLITE3_NUM) )
		{
			$cnt = $row[0];
		}
			
		if($cnt == 0){
			
			$query = "insert into raspberrypi (serialno) VALUES ('$piserialno')";
			
			$ret = $db->exec($query);
			
			if($ret == ''){
				echo $db->lastErrorMsg();
			}
			
		}
		
// 		echo '<br><br>'.$ret;
// 		echo '<br>'.$query;
			
			$piquery = "select id from raspberrypi where serialno = '$piserialno'";
			$piret = $db->query ( $piquery );
			
			while ( $row = $piret->fetchArray ( SQLITE3_NUM ) ) {
				echo '<br>pi id coming';
				$GLOBALS ['piid'] = $row [0];
			}
			
			if ($GLOBALS ['piid'] != '') {
				$userid = $GLOBALS ['userid'];
				$piid = $GLOBALS ['piid'];
				echo "USERID:" . $userid;
				echo "PIID:" . $piid;
				
				$usersquery = "insert into users (userid,piid) VALUES ($userid,$piid)";
				echo $usersquery;
				$usersret = $db->exec ( $usersquery );
				
				if (! $usersret) {
					echo $db->lastErrorMsg ();
					header ( "location:registerPi.php?message=Database+Insertion+Failed" );
				} else {
					header ( "location:Home.php?message=Successfully+Inserted+Records" );
				}
			} else {
				// echo 'hi';
				header ( "location:registerPi.php?message=Database+Insertion+Failed" );
			}
			
		}
	
	
	$db->close();
}
ob_end_flush();
?>