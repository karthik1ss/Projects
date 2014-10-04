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


  <div class="content">
    <ul class="nav">
      <li><a href="Logout.php">Logout</a></li>
    </ul>
    
  <div class="content"><br /><br /><br />
  
  <?php
  		if(isset($_GET['message'])){
			$message =$_GET['message'];
			if(isset($message)) { 
// 				echo "<div id='error_msg'>".$_SESSION['username']."-".$message."</div>"; 
			}
		}
// 		echo $_SESSION['username'];
	?>
	    <form method="post" action="PiRegistrationCheck.php" name="form1" class="style1">
	       <p>&nbsp;</p>
	       <h1> RaspberryPi Registration</h1><br /><br />
	       <FONT FACE="arial" SIZE="2">
	      <table width="372" border="0">
	        
	        <tr>
	          <td>RaspberryPi MAC ID : </td>
	          <th nowrap="nowrap"> <div align="left">
	            <input type="text"  name="pimacid" />
	          </div></th>
	        </tr>
	        
	        <tr>
	          <td>RaspberryPi PROCESSOR ID : </td>
	          <th nowrap="nowrap"> <div align="left">
	            <input type="text"  name="piprocid" />
	          </div></th>
	        </tr>
	        
	      </table>
	      <p>&nbsp; </p>
	      <p>
	        <input type="submit" name="Submit" value="Submit" />
	        <input type="reset" name="Submit2" value="Reset" />
	
	      </p>
	      </FONT>
	    </form>
	    
	    <form >
	        <input type="submit" name="Submit3" value="Cancel"   onclick="form.action='index.php';"/>
	    </form>
    <h1>&nbsp;</h1>
    <!-- end .content -->
    </div>
  
</div>
</body>
</html>