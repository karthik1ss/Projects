<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to the Code Contest</title>
<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
</head>

<body>


<form id="form1" name="form1" action="loginCheck.php" method="post">
  <p align="center">
    <input type="image" name="imageField" src="logo.png" />
  </p>
  <p align="center" class="style1">Welcome to the Contest Page of UT Dallas </p>
  <p align="center">&nbsp;</p>
  <p align="center">
    <label>UserName:
      <input type="text" name="Email" />
    </label>
  </p>
  <p align="center">
    <label> Password :
      <input type="password" name="Password" />
    </label>
  </p>
  <p align="center">
    <label> Usertype :
      <select name="usertype">
      	<option value="0">Select Usertype</option>
        <option value="1">USER</option>
        <option value="2">JUDGE</option>
        <option value="3">ADMIN</option>
      </select>
    </label>
  </p>
  <p align="center">&nbsp;</p>
  <p align="center">
    <label>
      <input type="submit" name="login" value="Login" />
    </label>
    <input type="reset" name="reset" value="Reset" />
  </p>
</form>
<form name="form2" action="UserRegistration.php" method="post">
  <blockquote>
    <blockquote>
      <blockquote>
        <blockquote>
          <blockquote>
            <blockquote>
              <blockquote>
                <blockquote>
                  <blockquote>
                    <blockquote>
                      <p>
                        Not a Member Yet? 
                        <input type="submit" name="Register" id="Register" value="Register" />
                      </p>
                    </blockquote>
                  </blockquote>
                </blockquote>
              </blockquote>
            </blockquote>
          </blockquote>
        </blockquote>
      </blockquote>
    </blockquote>
  </blockquote>
</form>
</body>
</html>
