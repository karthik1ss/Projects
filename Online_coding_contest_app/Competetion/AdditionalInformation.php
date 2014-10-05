<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Additional Information</title>
<style type="text/css">
<!--
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background-color: #42413C;
	margin: 0;
	padding: 0;
	color: #000;
}

/* ~~ Element/tag selectors ~~ */
ul, ol, dl { /* Due to variations between browsers, it's best practices to zero padding and margin on lists. For consistency, you can either specify the amounts you want here, or on the list items (LI, DT, DD) they contain. Remember that what you do here will cascade to the .nav list unless you write a more specific selector. */
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 /* removing the top margin gets around an issue where margins can escape from their containing div. The remaining bottom margin will hold it away from any elements that follow. */
	padding-right: 15px;
	padding-left: 15px; /* adding the padding to the sides of the elements within the divs, instead of the divs themselves, gets rid of any box model math. A nested div with side padding can also be used as an alternate method. */
}
a img { /* this selector removes the default blue border displayed in some browsers around an image when it is surrounded by a link */
	border: none;
}

/* ~~ Styling for your site's links must remain in this order - including the group of selectors that create the hover effect. ~~ */
a:link {
	color: #42413C;
	text-decoration: underline; /* unless you style your links to look extremely unique, it's best to provide underlines for quick visual identification */
}
a:visited {
	color: #6E6C64;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* this group of selectors will give a keyboard navigator the same hover experience as the person using a mouse. */
	text-decoration: none;
}

/* ~~this fixed width container surrounds the other divs~~ */
.container {
	width: 960px;
	background-color: #FFF;
	margin: 0 auto; /* the auto value on the sides, coupled with the width, centers the layout */
}

/* ~~ the header is not given a width. It will extend the full width of your layout. It contains an image placeholder that should be replaced with your own linked logo ~~ */
.header {
	background-color: #ADB96E;
}

/* ~~ These are the columns for the layout. ~~ 

1) Padding is only placed on the top and/or bottom of the divs. The elements within these divs have padding on their sides. This saves you from any "box model math". Keep in mind, if you add any side padding or border to the div itself, it will be added to the width you define to create the *total* width. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design.

2) No margin has been given to the columns since they are all floated. If you must add margin, avoid placing it on the side you're floating toward (for example: a right margin on a div set to float right). Many times, padding can be used instead. For divs where this rule must be broken, you should add a "display:inline" declaration to the div's rule to tame a bug where some versions of Internet Explorer double the margin.

3) Since classes can be used multiple times in a document (and an element can also have multiple classes applied), the columns have been assigned class names instead of IDs. For example, two sidebar divs could be stacked if necessary. These can very easily be changed to IDs if that's your preference, as long as you'll only be using them once per document.

4) If you prefer your nav on the right instead of the left, simply float these columns the opposite direction (all right instead of all left) and they'll render in reverse order. There's no need to move the divs around in the HTML source.

*/
.sidebar1 {
	float: left;
	width: 180px;
	background-color: #EADCAE;
	padding-bottom: 10px;
}
.content {

	padding: 10px 0;
	width: 780px;
	float: left;
}

/* ~~ This grouped selector gives the lists in the .content area space ~~ */
.content ul, .content ol { 
	padding: 0 15px 15px 40px; /* this padding mirrors the right padding in the headings and paragraph rule above. Padding was placed on the bottom for space between other elements on the lists and on the left to create the indention. These may be adjusted as you wish. */
}

/* ~~ The navigation list styles (can be removed if you choose to use a premade flyout menu like Spry) ~~ */
ul.nav {
	list-style: none; /* this removes the list marker */
	border-top: 1px solid #666; /* this creates the top border for the links - all others are placed using a bottom border on the LI */
	margin-bottom: 15px; /* this creates the space between the navigation on the content below */
}
ul.nav li {
	border-bottom: 1px solid #666; /* this creates the button separation */
}
ul.nav a, ul.nav a:visited { /* grouping these selectors makes sure that your links retain their button look even after being visited */
	padding: 5px 5px 5px 15px;
	display: block; /* this gives the link block properties causing it to fill the whole LI containing it. This causes the entire area to react to a mouse click. */
	width: 160px;  /*this width makes the entire button clickable for IE6. If you don't need to support IE6, it can be removed. Calculate the proper width by subtracting the padding on this link from the width of your sidebar container. */
	text-decoration: none;
	background-color: #C6D580;
}
ul.nav a:hover, ul.nav a:active, ul.nav a:focus { /* this changes the background and text color for both mouse and keyboard navigators */
	background-color: #ADB96E;
	color: #FFF;
}

/* ~~ The footer ~~ */
.footer {
	padding: 10px 0;
	background-color: #CCC49F;
	position: relative;/* this gives IE6 hasLayout to properly clear */
	clear: both; /* this clear property forces the .container to understand where the columns end and contain them */
}

/* ~~ miscellaneous float/clear classes ~~ */
.fltrt {  /* this class can be used to float an element right in your page. The floated element must precede the element it should be next to on the page. */
	float: right;
	margin-left: 8px;
}
.fltlft { /* this class can be used to float an element left in your page. The floated element must precede the element it should be next to on the page. */
	float: left;
	margin-right: 8px;
}
.clearfloat { /* this class can be placed on a <br /> or empty div as the final element following the last floated div (within the #container) if the #footer is removed or taken out of the #container */
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}
-->
</style></head>

<body>
<?php
echo "Hello";
?>
<div class="container">
  <div class="header"><a href="#"><img src="ut-dallas-logo.jpg" alt="Insert Logo Here" name="Insert_logo" width="300" height="111" id="Insert_logo" style="background-color: #C6D580; display:block;" /></a> 
    <!-- end .header --></div>
  <div class="sidebar1">
    <ul class="nav">
      <li><a href="#">Contest</a></li>
      <li><a href="#">Grades</a></li>
      <li><a href="#">Profile</a></li>
      <li><a href="#">Additional Information</a></li>
      <li></li>
      <p>Logout</p>
    </ul>
    <!-- end .sidebar1 --></div>
  <div class="content">Currently, only the submussions via the &quot;Quick Submit&quot; link or the &quot;Submit&quot; icon for each problem is possible. In order to submit your code, just fill in the form and press &quot;submit&quot;. Using this form, you don't have to include any special header to the file as everything is handled by the system.
    <p><u><strong>General Specifications:</strong></u></p>
    <p>The program reads the test input from the standard input (stdin) and places the results in the standard output (stdout). This is <strong>always</strong> the case, regardless on what the problem description says. Every line in the input is guaranteed to be finished by the end-of-line character ('\n').  In a similar way, your program must print an end-of-line character at the end of every line. A correct output with a missing end-of-line character will not be judged as Accepted.</p>
    <p>The program doesn't need (in fact, they aren't allowed to) open files or to execute some system calls. Remember: <strong>never try to open any file</strong> or your program will be judged as wrong.</p>
    <p>In order to help some people, the Online Judge defines always the 'ONLINE_JUDGE' symbol while compiling your program. Thus, you can test for it to redirect the input/output to a file except while the Online Judge is testing your program by, for example, using the preprocessor directive #ifdef in C.</p>
    <p><u><strong>Pascal Specifications:</strong></u></p>
    <p>FreePascal compiler seems not to use signals to notify runtime errors, but emits a short text message which merges with your program outputs. So, if you have a Runtime Error, you might receive a Wrong Answer error instead. We know this is a problem, but we cannot focus on it right now.</p>
    <p>As a reference, we provide a <a href="http://online-judge.uva.es/problemset/data/p100.p.html">sample Pascal code</a></p>
    <p><u><strong>C/C++ Specifications:</strong></u></p>
    <p>Don't assume that any header file (stdio.h, stdlib.h, math.h, etc) is going to be included by default. Please, include all the headers that you actually need.</p>
    <p>The use of dangerous or deprecated functions is discouraged. We don't judge as Compile Error a submission using those functions, but you use them under your own risk. For example, the gets function can cause a runtime error even if fairly used. Please, use fgets instead.</p>
    <p>As a reference, we provide a <a href="http://online-judge.uva.es/problemset/data/p100.c.html">sample C code</a></p>
    <u><strong>Java Specifications:</strong></u>
    <p>The Java programs submitted must be in a single source code (not .class) file. Nevertheless, you can add as many classes as you need in this file. All the classes in this file must not be within any package.</p>
    <p>All programs must begin in a static main method in a Main class.</p>
    <p>Do not use public classes: even Main must be non public to avoid compile error.</p>
    <p> Use buffered I/O to avoid time limit exceeded due to excesive flushing.</p>
    <p>As a reference, we provide a <a href="http://online-judge.uva.es/problemset/data/p100.java.html">sample Java code</a></p>
    <p> </p>
    <p>If you have any question or consider that some information is missing, please contact us.</p>
  </div>
  <div class="footer">
    <p>Copyrights</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>