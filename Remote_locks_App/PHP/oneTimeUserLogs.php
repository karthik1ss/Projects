<?php
session_start();
?>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Onetime User Logs</title>
<link rel="stylesheet" type="text/css" href="default.css" />
</head>


<body>


  <div class="content">
    <ul class="nav">
      <li><a href="Logout.php">Logout</a></li>
    </ul>
    
  <div class="content"><br /><br /><br />
  
  <?php
  		if(isset($_GET['message'])){
			$message =$_GET['message'];
			if(isset($message)) { 
				echo "<div id='error_msg'>".$_SESSION['username']."-".$message."</div>"; 
			}
		}

	$username = $_SESSION['username'];
	
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
   
   
   $piquery = "select ol.id as ID, ol.lockstatus as lockstatus, ol.timestamp as timestamp from onetimeuserlog ol join onetimeuser a on ol.id = a.id ".
   				"where a.addedby = '$username'";
   
   $piret = $db->query($piquery);
   
   echo '<br><br>';
   
   echo '<table style="width:300px" border="0">';
   echo '<tr>';
   echo '<th>ID</th>';
   echo '<th>LockStatus</th>';
   echo '<th>TimeStamp</th>';
   echo '</tr>';
   
   while($row = $piret->fetchArray(SQLITE3_ASSOC))
   {
   		echo '<tr>';
   		echo '<td>'.$row['ID'].'</td>';
   		echo '<td>'.$row['lockstatus'].'</td>';
   		echo '<td>'.$row['timestamp'].'</td>';
   		echo '<tr>';
   }
   
   
	?>
	
	
	
	
	
	</div>
</body>
</html>