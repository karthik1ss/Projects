<?php
session_start();
if(isset($_SESSION['myusername'])){
header("location:JudgeHome.php");
}
?>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<title>Contest Page of UT Dallas</title>
<link rel="stylesheet" type="text/css" href="default.css" />
</head>
<body>

<div id="outer">

	<div id="upbg"></div>

	<div id="inner">

		<div id="header">
			<h1><span>Contest Page of UT Dallas</span></h1>
			<h2></h2>
		</div>
	
		<div id="splash"></div>
	
<!--		<div id="menu">
			<ul>
				<li class="first"><a href="#">Home</a></li>
				<li><a href="#">Archives</a></li>
				<li><a href="#">Links</a></li>
				<li><a href="#">Resources</a></li>
				<li><a href="#">Contact</a></li>
			</ul>

		<!--<div id="date">August 1, 2006</div> 
		</div> -->
	
<div id="menu">
<h1> Welcome to Contest Page of UT Dallas </h1>
</div>
		<div id="primarycontent">
		    <div class="content" align="center">
			 <form name="form1" method="post" action="loginCheck.php">
	<p>
      <label>Email:
        <input type="text" name="emailid" />
      </label>
    </p>
    <p>
      <label> Password :
        <input type="password" name="password" />
      </label>
    </p>
      <p>
      <label>
        <input type="submit" name="Submit" value="Submit" />
      </label>
      <input type="reset" name="Submit2" value="Reset" />
    </p>
	
	</form>
	</div>

			<!-- primary content start -->
		
			<!-- <div class="post">
				<div class="header">
					<h3>TerraFirma<sup>1.0</sup> by NodeThirtyThree</h3>
					<div class="date">August 1, 2006</div>
				</div>
				<div class="content">
					<img src="images/pic1.jpg" class="picA floatleft" alt="" />
					<p><strong>TerraFirma</strong><sup>1.0</sup> is a free, lightweight, tableless, W3C-compliant website design by <a href="http://www.nodethirtythree.com/">NodeThirtyThree Design</a>. You're free to dissect, manipulate and use it to your heart's content. We only ask that you link back to our site in some way. If you find this design useful, feel free to let us know :)</p>
					<p>You can find more of our free work at this site or <a href="http://www.nodethirtythree.com/">our site</a>, or some of our commercial work on <a href="http://www.4templates.com/?aff=n33">4Templates.com</a>, a commercial website template site.</p>
				</div>			
				<div class="footer">
					<ul>
						<li class="printerfriendly"><a href="#">Printer Friendly</a></li>
						<li class="comments"><a href="#">Comments (18)</a></li>
						<li class="readmore"><a href="#">Read more</a></li>
					</ul>
				</div>
			</div>
		
			<div class="post">
				<div class="header">
					<h3>Vivamus tortor sed aenean</h3>
					<div class="date">July 31, 2006</div>
				</div>
				<div class="content">
					<p>Volutpat at varius sed sollicitudin et, arcu. Vivamus viverra. Nullam turpis. Vestibulum sed etiam. Lorem ipsum sit amet dolore. Nulla facilisi. Sed tortor. Aenean felis. Quisque eros. Cras lobortis commodo metus. Vestibulum vel purus. In eget odio in sapien adipiscing blandit. Quisque augue tortor, facilisis sit amet, aliquam, suscipit vitae, cursus sed, arcu lorem ipsum dolor sit amet.</p>
				</div>			
				<div class="footer">
					<ul>
						<li class="printerfriendly"><a href="#">Printer Friendly</a></li>
						<li class="comments"><a href="#">Comments (18)</a></li>
						<li class="readmore"><a href="#">Read more</a></li>
					</ul>
				</div>
			</div>

			<div class="post">
				<div class="header">
					<h3>Vivamus tortor sed aenean</h3>
					<div class="date">July 31, 2006</div>
				</div>
				<div class="content">
					<p>Volutpat at varius sed sollicitudin et, arcu. Vivamus viverra. Nullam turpis. Vestibulum sed etiam. Lorem ipsum sit amet dolore. Nulla facilisi. Sed tortor. Aenean felis. Quisque eros. Cras lobortis commodo metus. Vestibulum vel purus. In eget odio in sapien adipiscing blandit. Quisque augue tortor, facilisis sit amet, aliquam, suscipit vitae, cursus sed, arcu lorem ipsum dolor sit amet.</p>
				</div>			
				<div class="footer">
					<ul>
						<li class="printerfriendly"><a href="#">Printer Friendly</a></li>
						<li class="comments"><a href="#">Comments (18)</a></li>
						<li class="readmore"><a href="#">Read more</a></li>
					</ul>
				</div>
			</div> 

			<!-- primary content end -->
	
		</div>
		<!--
		<div id="secondarycontent">

			<!-- secondary content start 
		
			<h3>About Me</h3>
			<div class="content">
				<img src="images/pic2.jpg" class="picB" alt="" />
				<p><strong>Nullam turpis</strong> vestibulum et sed dolore. Nulla facilisi. Sed tortor. lobortis commodo. <a href="#">More ...</a></p>
			</div>
			
			<h3>Topics</h3>
			<div class="content">
				<ul class="linklist">
					<li class="first"><a href="#">Accumsan congue (32)</a></li>
					<li><a href="#">Dignissim nec augue (14)</a></li>
					<li><a href="#">Nunc ante elit nulla (83)</a></li>
			</div>

			<!-- secondary content end 

		</div>	-->
		<div id="footer">
		
			&copy; My Website. All rights reserved. 
		
		</div>

	</div>

</div>

</body>
</html>