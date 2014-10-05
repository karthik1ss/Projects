<?php

if(isset($_POST['submit'])
{
	//$con = mysql_connect("localhost","root","root") or die('Could not connect: ' . mysql_error());
	
	$con = new mysqli('localhost','root','root','code_contest_db');
	
	/* check connection */
	if (mysqli_connect_errno())
	{
    	printf("Connect failed: %s\n", mysqli_connect_error());
    	exit();
	}
		
		//mysql_select_db("code_contest_db", $con);
        session_start();
        $_SESSION['email'] = $_POST['Email'];
        $_SESSION['password'] = $_POST['Password'];
		$_SESSION['authtype']=1;
        
		$username = stripslashes($username);
		$password = stripslashes($password);
        $username=mysql_real_escape_string($_POST['Email]'); 
        $password=mysql_real_escape_string($_POST['Password']);
		$usertypes=array('Select Usertype','USER','JUDGE','ADMIN');
		$selected_val=$usertypes[$_POST['usertype']];
		
		$stmt=$con->stmt_init();
		
        //$result = mysql_query("SELECT * FROM authentication WHERE emailId=? AND password=? and usertype=?);
		
		if($stmt->prepare("SELECT * FROM authentication WHERE emailId=? AND password=? and usertype=?"))
		{
			$stmt -> bind_param('sss',$username,$password,$selected_val);
			$stmt->execute();
			if(stmt->num_rows())
			{
				header('Location: UserMain.php');
            	exit();
			}
			else
			{
				header('Location: index.php');
            	exit();
			}
			
			$stmt->close();			
		}
		
       /* $count=mysql_num_rows($result);
        if($count==1){      
            header('Location: UserMain.php');
            exit();
        }
        else{
            header('Location: index.php');
            exit();
        }
		mysql_close($con);
		*/
}
?>