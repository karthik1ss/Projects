<?php
session_start();
$username = $_POST['username'];
echo $username;
$_SESSION['username'] = $username; 
?>

<html>
<body>
<a href="trial2.php">link</a>
</body>
</html>