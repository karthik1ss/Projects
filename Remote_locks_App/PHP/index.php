
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<title>Home Lock Project</title>
<link rel="stylesheet" type="text/css" href="default.css" />
</head>
<body>

	
<h1> Home Lock ACN Project </h1>

<div class="content"><br /><br /><br />
  
  <?php
  		if(isset($_GET['message'])){
			$message = $_GET['message'];
			if(isset($message)) { 
				echo "<div id='error_msg'>".$message."</div>"; 
			}
		}
	?>
</div>


  <form name="form1" method="post" action="loginValidate.php">
	<p>&nbsp;</p>
	<table width="312" height="107" border="0">
	  <tr>
	    <td><label>Username: </label></td>
	    <td><input type="text" name="username" /></td>
	    </tr>
	  <tr>
	    <td>Password :</td>
	    <td><input type="password" name="password" /></td>
	    </tr>
	  <tr>
	    <td><input type="submit" name="Submit" value="Submit" /></td>
	    <td><input type="reset" name="Submit2" value="Reset" /></td>
	    </tr>
	  </table>
	<p>&nbsp;</p>
    <h3><strong>Not a Member yet? <a href="registerUser.php">Register </a></strong></h3>
      <p>&nbsp;</p>
	
 </form>
 
 
</body>
</html>