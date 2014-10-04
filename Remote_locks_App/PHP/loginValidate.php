<?php
ob_start();
session_start();

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
   
   $username = $_POST['username'];
   $password = $_POST['password'];
   
   $md5 = md5($password);
   
   echo $md5;
   
   $query = "select count(*) as count from AndroidClients where username='$username' and password='$md5'";
   
   echo $query;

   $ret = $db->query($query);
   
   while($row = $ret->fetchArray(SQLITE3_ASSOC) )
   {
   		$cnt = $row['count'];
   }
   
//     $cnt = $ret->numColumns();
   
   echo "count:".$cnt;
   
   if($cnt == 1)
   {
   	
	    $_SESSION['username'] = $username;
	    header("location:Home.php?message=User+Validated+Successfully");
	    
   }
   else 
   {
   		header("location:index.php?message=Invalid+User.+Please+re-enter+login+details");
//    	echo "Validation Failed: Wrong Login Credentials.Please re-enter login details";
   }
   
   $db->close();
   
ob_end_flush();
?>