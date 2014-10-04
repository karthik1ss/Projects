<?php
session_start(); 
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>RaspberryPi Registration</title>
<link rel="stylesheet" type="text/css" href="default.css" />
</head>


<body>


<?php


if(isset($_GET['message'])){
	$message =$_GET['message'];
	if(isset($message)) {
		echo "<div id='error_msg'>".$_SESSION['username']."-".$message."</div>";
	}
}

?>



  <div class="content">
    <ul class="nav">
      <li><a href="Logout.php">Logout</a></li>
    </ul>
    
  <div class="content"><br /><br /><br />
  
  <li><a href="registerPi.php">RaspberryPi Registration</a></li>
  
  <li><a href="registerOnetimeUser.php">Onetime User Registration</a></li>
  
  <li><a href="oneTimeUserLogs.php">Onetime User Logs</a></li>
  
  
  
</div>
</body>
</html>