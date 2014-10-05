<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
&lt;?php<br />
// Connection's Parameters<br />
$db_host=&quot;localhost&quot;;<br />
$db_name=&quot;sakila&quot;;<br />
$username=&quot;root&quot;;<br />
$password=&quot;naveen123&quot;;<br />
$db_con=mysql_connect($db_host,$username,$password);<br />
$connection_string=mysql_select_db($db_name);<br />
// Connection<br />
mysql_connect($db_host,$username,$password);<br />
mysql_select_db($db_name);<br />
?&gt;
</body>
</html>