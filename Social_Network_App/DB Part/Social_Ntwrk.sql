-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.32-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema newzonia
--

CREATE DATABASE IF NOT EXISTS newzonia;
USE newzonia;

--
-- Definition of table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `screenName` varchar(50) NOT NULL,
  `ArticleName` varchar(100) NOT NULL,
  `Article` text,
  `commentsallowed` tinyint(1) NOT NULL,
  `ArticleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `posttime` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ArticleId`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1 COMMENT='It stores the entire articles of Newzonia blogs';

--
-- Dumping data for table `articles`
--

/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` (`screenName`,`ArticleName`,`Article`,`commentsallowed`,`ArticleId`,`posttime`) VALUES 
 ('user2','about amithab paa...','Can you believe it that Paa all set to enter the Guinness Book of world recordsâ?¦\r\rI am very much ecstatic and very happy to know that our PAA movie is entering into Guinness Bookâ?¦ Read this article to believe itâ?¦â?¦',1,5,'2009/12/16 15:46:30'),
 ('user1','Its about 3 Idiots','Three Idiots  is a forthcoming Indian Bollywood film directed by Rajkumar Hirani, starring Aamir Khan, R. Madhavan, Sharman Joshi, Kareena Kapoor, and Boman Irani in pivotal roles. Kajol will make a special appearance in the film. It is slated for a release on 25 December 2009.',1,6,'2009/12/16 15:59:09'),
 ('user1','About Surya and KamalHasan','Kamal Haasan and Suriya will share the Film Fans Associationâ??s Best Actor award for the year 2008. While Kamal will get the award for Dasavatharam, Suriya has been adjudged as the best actor for Vaaranam Aayiram. Simran, Sneha and Trisha are the be',1,7,'2009/12/18 17:51:14'),
 ('user1','Filmfare',' Aamir Khan, with his penchant for perfection and professionalism, is one of the few method actors in Bollywood, who has taken acting to a whole new level. Outwardly, his hairstyles from Dil Chahta Hai to Ghajini, are served as appetizers for cinephiles. An actor, director, producer, playback singer, a state tennis champion and mentor to his nephew â?? heâ??s all that and more.',0,10,'2009/12/21 12:58:15'),
 ('user1','Aamir','Aamir Khan celebrates his birthday on the 14th March. He was born on 1965 in Mumbai to Tahir and Zeenat Hussain. He has three siblings Faisal, Farhat and Nikhat. The film industry was part of his life growing up as many members of his family were part of it like his father, his late uncle Nasir Hussain and his cousin Mansoor Khan. It was Mansoor Khanâ??s Qayamat Se Qayamat Tak that brought him success.\rAamirâ??s family roots can be traced back to Afghanistan. He is said to be a descendant of Maulana Abul Kalam Azad who was a scholar and political leader; Dr.Zakir Hussain, the former President of India. Dr.Najma Heptullah, who was a Chairperson in the Rajya Sabha is his second cousin.\rEven as his first hit Qayamat Se Qayamat Tak made him an overnight heartthrob across the country, he married his sweetheart Reena Dutta secretly as their parents opposed the marriage. She even made a blink and you will miss appearance in his song Papa Kehte Hai in Qayamat Se Qayamat Tak. They have two children Junaid and Ira. The couple filed for divorce in 2002. Soon after, in 2005 he married Kiran Rao, who was an assistant director on the sets of Lagaan.\rAamir was honoured with the Padma Bhushan award by the Government of India in 2003. He turned down the offer to replic',0,11,'2009/12/21 13:02:09'),
 ('user1','A.R.Rahman','A.R.Rahman wins his 21st Filmfare Award for Guru -\rHe now holds the record for the maximum number of Filmfare Awards.\rOthers - Kamal Haasan - 19, Gulzar - 18, Javed Akhtar - 15,\rYash Chopra, Shah Rukh Khan - 14, Amithabh Bachchan - 12, Bimal Roy - 11.',1,12,'2009/12/23 15:23:08'),
 ('user1','aaaaaaa','aaaaaaaaaaaaaahgggggccccccccccccccccccccccccccccccccggggggggggggggggjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj',1,23,'2009/12/23 16:01:38'),
 ('Bhavani..','Avatar Movies Review','Get your pitchforks ready people! Iâ??m guessing from peopleâ??s reactions last night, not all of you are going to like what I have to say. To begin, yes this is the epic visual masterpiece that youâ??ve all been waiting for and it succeeds in everything it tries to do (visually).The overall look is stunning beyond belief, the special effects are impeccable, Cameron got me to shed a tear and sit on the edge of my seatâ?¦ BUT the film isnâ??t flawless, not by a long shotâ?¦',1,24,'2010/02/26 21:46:04'),
 ('Bhavani..','Avatar: Movie Review','Get your pitchforks ready people! Iâ??m guessing from peopleâ??s reactions last night, not all of you are going to like what I have to say. To begin, yes this is the epic visual masterpiece that youâ??ve all been waiting for and it succeeds in everything it tries to do (visually).The overall look is stunning beyond belief, the special effects are impeccable, Cameron got me to shed a tear and sit on the edge of my seatâ?¦ BUT the film isnâ??t flawless, not by a long shotâ?¦',1,25,'2010/02/27 12:22:20'),
 ('Bhavani..','2012 Movie Review','Roland Emmerichâ??s  2012 brings together the end of the world and government conspiracy in a way not particularly explored before. It also, I must imagine, breaks the record for how many people die in a film. Unfortunately, this is pretty well where the positive discussion of the film ends, and hopefully, you didnâ??t realize it had begun.\n\n',1,26,'2010/02/27 19:21:28'),
 ('Sampath..!','hi..','hi frends..',1,34,'2010/04/10 19:56:09'),
 ('Ajay..','Hi..','This Regarding Today Blog..',1,35,'2010/04/12 12:24:55'),
 ('Ajay..','Todays Article','This is my today article..',1,36,'2010/04/12 12:28:03'),
 ('fatima','friends','hey.....',1,37,'2010/04/17 21:55:24');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;


--
-- Definition of table `avatar`
--

DROP TABLE IF EXISTS `avatar`;
CREATE TABLE `avatar` (
  `planetname` varchar(50) DEFAULT NULL,
  `ringname` varchar(45) DEFAULT NULL,
  `expertise` varchar(45) DEFAULT NULL,
  `tagnames` varchar(45) DEFAULT NULL,
  `screenName` varchar(45) DEFAULT NULL,
  `websiteLink` varchar(45) DEFAULT NULL,
  `personalDescription` varchar(45) DEFAULT NULL,
  `emailAddress` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `isprofileexist` varchar(2) NOT NULL DEFAULT '0',
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regno` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `creationTime` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `contentSource` varchar(100) DEFAULT NULL,
  `representationname` varchar(45) DEFAULT NULL,
  `representationcontentType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10078 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `avatar`
--

/*!40000 ALTER TABLE `avatar` DISABLE KEYS */;
INSERT INTO `avatar` (`planetname`,`ringname`,`expertise`,`tagnames`,`screenName`,`websiteLink`,`personalDescription`,`emailAddress`,`country`,`isprofileexist`,`id`,`regno`,`firstname`,`lastname`,`creationTime`,`password`,`contentSource`,`representationname`,`representationcontentType`) VALUES 
 (NULL,NULL,NULL,NULL,NULL,NULL,NULL,'s@s.com',NULL,'0',10000,'reg10001','ss','kk','11:00\r\n','ss',NULL,NULL,NULL),
 ('Spartik','ring1','Social Network Site','Hi..','Sampath..!','www.sampath.com','Hi this is sampath..','sampath@gmail.com','INDIA','1',10070,'reg10001','sampath','kumar','2010-04-15 19:57:42','sampath','../WebDav/repository/973ae5bb/005e/4596/9f5f/a96ffb0ceb00/images (27).jpg','images (27).jpg','application/octet-stream'),
 ('Spartik','ring1','Programmer','Hi..','Suneel..!','www.sunil.com','Hi this is sunil..','sunil@gmail.com','FRANCE','1',10071,'reg10071','sunil','kashetty','2010-04-15 22:35:07','sunil','../WebDav/repository/ef048ba7/f255/4569/a243/4c14492b208e/5312550.jpg','5312550.jpg','application/octet-stream'),
 ('Spartik','ring1','Programmer','Hi..','Nethaji..!','www.nethaji.com','Hi this is Nethaji..','nethaji@gmail.com','INDIA','1',10072,'reg10072','nethaji','k','2010-04-17 12:33:16','nethaji','../WebDav/repository/d2ca7f91/6eee/4219/986b/88fbf6a56ffe/070753H1.jpg','070753H1.jpg','application/octet-stream'),
 ('Spartik','ring1','Programmer','hhdd','fatima','www.fatima.com','dfh','fatima@gmail.com','INDIA','1',10074,'reg10074','fatima','nayeem','2010-04-17 21:41:38','fatima','../WebDav/repository/1c07c597/0741/4411/81a9/14eb97eea568/451_greening_hollywood_logo.jpg','451_greening_hollywood_logo.jpg','application/octet-stream'),
 ('Spartik','ring1','Social Network Site','hi..','Praneeth..!','www.praneeth.com','hi this is praneeth..','praneeth@gmail.com','INDIA','1',10075,'reg10075','praneeth','venkat','2010-04-18 12:29:39','praneeth','../WebDav/repository/4fcd6665/e110/4792/b28a/f1a732201eb5/Hollywood_Trading_Company_logo.jpg','Hollywood_Trading_Company_logo.jpg','application/octet-stream'),
 ('Spartik','ring5','Web Designer','hi..','Ashwini..','www.ashwini.com','Hi this is achu..','ashwini@gmail.com','INDIA','1',10076,'reg10076','ashwini','reddy','2010-04-18 12:43:59','ashwini','../WebDav/repository/fea05b7a/f7ab/4191/8265/c42ea456e512/images (9).jpg','images (9).jpg','application/octet-stream'),
 ('Spartik','ring5','Communications Expert','hi..','Maddy..','www.madhava.com','hi this is madhava..','madhava@gmail.com','INDIA','1',10077,'reg10077','madhava','kasiraju','2010-04-18 13:06:27','madhava','../WebDav/repository/cff07ec9/fa21/4712/ba46/b4c13b1ae7ec/NapoleonPerdis.jpg','NapoleonPerdis.jpg','application/octet-stream');
/*!40000 ALTER TABLE `avatar` ENABLE KEYS */;


--
-- Definition of table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `commentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cusername` varchar(50) NOT NULL,
  `comments` varchar(20000) NOT NULL,
  `ctime` varchar(100) NOT NULL,
  `ArticleId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`commentid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='This table contains comments for entire Blogs.';

--
-- Dumping data for table `comments`
--

/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`commentid`,`cusername`,`comments`,`ctime`,`ArticleId`) VALUES 
 (1,'sampath','Nice Article','2009/12/23 16:01:38',6);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;


--
-- Definition of table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `eventname` varchar(50) NOT NULL,
  `eventdate` varchar(45) NOT NULL,
  `eventvenue` varchar(500) NOT NULL,
  `eventdesc` varchar(10000) NOT NULL,
  `website` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `imagetype` varchar(45) NOT NULL,
  `imagesource` varchar(200) NOT NULL,
  `imagename` varchar(45) NOT NULL,
  `screenname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` (`eventname`,`eventdate`,`eventvenue`,`eventdesc`,`website`,`phone`,`imagetype`,`imagesource`,`imagename`,`screenname`) VALUES 
 ('Avatar Premier Show','Sat May 29 18:00:00 GMT+0530 2010','Prasadz Imax,\nHyderaBad.','Hi We are conducting a premier Show for the movie Avatar.. The entry passes will be available in Our Afficial website..','www.Avatarshow.com','040-22333555','application/octet-stream','../WebDav/repository/1efd8606/4b74/4679/914c/4609a6c8bda3/avatar.jpg','avatar.jpg','Sampath..!'),
 ('project execution','Fri Apr 30 21:45:00 GMT+0530 2010','khairtabad','prjct desc','www.fatima.com','9444466741','application/octet-stream','../WebDav/repository/e506fb8d/bfc1/4b79/945b/131597f162fe/063365H1.jpg','063365H1.jpg','fatima');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;


--
-- Definition of table `invitations`
--

DROP TABLE IF EXISTS `invitations`;
CREATE TABLE `invitations` (
  `proj_member_name` varchar(45) DEFAULT NULL,
  `role` varchar(45) NOT NULL,
  `projectid` int(10) unsigned NOT NULL,
  `ScreenName` varchar(45) NOT NULL,
  `projectname` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invitations`
--

/*!40000 ALTER TABLE `invitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `invitations` ENABLE KEYS */;


--
-- Definition of table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `assetid` varchar(200) NOT NULL,
  `screenname` varchar(45) NOT NULL,
  `contentDescription` varchar(2000) NOT NULL,
  `contentName` varchar(100) DEFAULT NULL,
  `contentSource` varchar(200) NOT NULL,
  `contentType` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `prodname` varchar(45) DEFAULT '',
  `prodprice` varchar(45) DEFAULT '',
  `url` varchar(2000) DEFAULT '',
  `productimgname` varchar(100) DEFAULT NULL,
  `productimagesource` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`assetid`,`screenname`,`contentDescription`,`contentName`,`contentSource`,`contentType`,`type`,`status`,`prodname`,`prodprice`,`url`,`productimgname`,`productimagesource`) VALUES 
 ('2qqM22-projectImages-project','Sampath..!','Good painting..','planet-porsche.jpg','../WebDav/repository/9b0355ec/a1cb/4823/ab6e/6876c2405d45/planet-porsche.jpg','application/octet-stream','Image','Available','Nice Logo..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','planet-porsche.jpg','../WebDav/repository/a4baf82c/6ff4/4006/9b9d/73187f704cc4/planet-porsche.jpg'),
 ('mXDa6c-projectImages-project','Sampath..!','good one..','images_(18).jpg','../WebDav/repository/cee25344/cc2a/4f17/a4af/f3e75c72701a/images (18).jpg','application/octet-stream','Image','Available','good painting..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','images (18).jpg','../WebDav/repository/7e6050c9/2419/4596/84ef/f6fa620205b8/images (18).jpg'),
 ('KoxYi0-projectVideos-project','Sampath..!','Nice videos...','Haule_Haule.flv','../WebDav/repository/7a2990b1/40d0/4ee4/8735/a8c0592b02e4/Haule_Haule.flv','application/octet-stream','Video','Available','nice videos..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','rab ne.jpeg','../WebDav/repository/ffd64e23/e3ce/4a52/a3a2/9b43e1a4d2f0/rab ne.jpeg'),
 ('fPnm9V-projectVideos-project','Sampath..!','nice video..','Ghajini_(Hindi)_DVD-RIP____Video_Song_Promo____Guzarish.flv','../WebDav/repository/2411246d/09be/4c3f/948a/9340a5a09767/Ghajini_(Hindi)_DVD-RIP____Video_Song_Promo____Guzarish.flv','application/octet-stream','Video','Available','Gajini video','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','Ghajini-www.TumTube.com.jpg','../WebDav/repository/d31e0dc0/2f36/4843/9c58/0d1cf816ec64/Ghajini-www.TumTube.com.jpg'),
 ('null','Sampath..!','Enter audio description','03_- Swaasye [DRGM].mp3','../WebDav/repository/0abe7fa2/221e/43af/932e/b0aa258f6a8a/03 - Swaasye [DRGM].mp3','application/octet-stream','Audio','Available','Ye maya chesave..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','00 - Front.jpg','../WebDav/repository/aacbfb95/d4fb/43eb/bb2d/207a1fb9a53f/00 - Front.jpg'),
 ('Su4nTi-projectTexts-project','Sampath..!','Important doc..','PP_API_Reference.pdf','../WebDav/repository/a12d4ecf/a4c7/4a3b/920a/96cf5b63b76f/PP_API_Reference.pdf','application/octet-stream','Text','Available','paypal account Details..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','firebox-logo.jpg','../WebDav/repository/f857febb/7c09/424e/be05/b51088d08395/firebox-logo.jpg'),
 ('IfV5Va-projectImages-project','fatima','Enter image description','070753H1.jpg','../WebDav/repository/9e824a12/0db8/49f5/b651/98c5ac647ae0/070753H1.jpg','application/octet-stream','Image','Available','whatever','500','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','buds bands bbq.jpg','../WebDav/repository/ccb4ec45/a24e/4336/9d3e/4cbccda7420b/buds bands bbq.jpg'),
 ('WHouJL-projectImages-project','fatima','nice painting..','William_Brand_and_Annet_van_Egmond_Hollywood_Lamp_zv2.jpg','../WebDav/repository/01d0e2d7/5298/439d/be1e/5f48851d9bdf/William_Brand_and_Annet_van_Egmond_Hollywood_Lamp_zv2.jpg','application/octet-stream','Image','Available','nice one..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','William_Brand_and_Annet_van_Egmond_Hollywood_Lamp_zv2.jpg','../WebDav/repository/5315d9b5/c3ca/4628/b84f/2763b7789e33/William_Brand_and_Annet_van_Egmond_Hollywood_Lamp_zv2.jpg'),
 ('wX704P-projectImages-project','Sampath..!','hi..','ihotnews-logo.jpg','../WebDav/repository/c72e63d6/6d37/4749/bd01/25ab3692e6c3/ihotnews-logo.jpg','application/octet-stream','Image','Available','nice image..','1.75','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','ihotnews-logo.jpg','../WebDav/repository/f21f64ed/4b07/4953/80b4/2e66739479aa/ihotnews-logo.jpg'),
 ('nxBAGd-projectImages-project','Sampath..!','hi..','diya-basmati-logo.jpg','../WebDav/repository/fa34c43f/6318/4599/969a/30b193187684/diya-basmati-logo.jpg','application/octet-stream','Image','Available','hi..','1.75','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','diya-basmati-logo.jpg','../WebDav/repository/d12b4444/7cd9/4d92/90dc/526f996b92b8/diya-basmati-logo.jpg'),
 ('bIZ0Wo-projectImages-project','Sampath..!','nice','2010FGbutton.JPG','../WebDav/repository/ba8329e1/edd4/4a62/8de1/e4cda3cc4bd8/2010FGbutton.JPG','application/octet-stream','Image','Available','nice image..','1.75','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','2010FGbutton.JPG','../WebDav/repository/4e7d757d/a915/4eeb/ace3/6d591032cd98/2010FGbutton.JPG'),
 ('4S6ZGI-projectImages-project','Sampath..!','good...','divas-logo.jpg','../WebDav/repository/1c9b509d/b5a5/4485/8d77/5f91bd251a1e/divas-logo.jpg','application/octet-stream','Image','Available','nice image..','1.75','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','divas-logo.jpg','../WebDav/repository/2e9afb72/9142/4a16/8433/48fd0322b10e/divas-logo.jpg'),
 ('DQNnb5-projectImages-project','Sampath..!','nice one..','fire-head-logo.jpg','../WebDav/repository/91c2516c/a9de/42e6/8ac3/d790acaa157d/fire-head-logo.jpg','application/octet-stream','Image','Available','good image..','2.5','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AUQED29C255U8','fire-head-logo.jpg','../WebDav/repository/4b7970da/e74f/452f/bb02/a968ccd6cb3e/fire-head-logo.jpg'),
 ('G3AItv-projectImages-project','Sampath..!','My Mickys..','Holidays_Around the World.jpg','../WebDav/repository/6a7ecbe1/cc3d/454b/b72b/816f5388b6c1/Holidays Around the World.jpg','application/octet-stream','Image','Available','My images..','1.25','https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L937BUZJFFNPJ','Holidays Around the World.jpg','../WebDav/repository/3a57b4e1/b4a1/4c2e/99ac/f6c34b4a225b/Holidays Around the World.jpg');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;


--
-- Definition of table `projectassets`
--

DROP TABLE IF EXISTS `projectassets`;
CREATE TABLE `projectassets` (
  `projectassetType` varchar(200) DEFAULT NULL,
  `projectid` int(10) unsigned NOT NULL,
  `contentDescription` varchar(2500) NOT NULL,
  `contentName` varchar(200) DEFAULT NULL,
  `contentSource` varchar(200) DEFAULT NULL,
  `contentType` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `projectassets`
--

/*!40000 ALTER TABLE `projectassets` DISABLE KEYS */;
INSERT INTO `projectassets` (`projectassetType`,`projectid`,`contentDescription`,`contentName`,`contentSource`,`contentType`,`type`) VALUES 
 ('x0RdhT-projectImages-project',13,'Project Logo..','2010FGbutton.JPG','../WebDav/repository/18eaf988/a940/4399/81c3/c42ef38022ed/2010FGbutton.JPG','application/octet-stream','Image'),
 ('rLnnkr-projectImages-project',13,'Logo 2','451_greening_hollywood_logo.jpg','../WebDav/repository/ca1d8541/2e81/4238/872a/1623f4fcab27/451_greening_hollywood_logo.jpg','application/octet-stream','Image');
/*!40000 ALTER TABLE `projectassets` ENABLE KEYS */;


--
-- Definition of table `projectdata`
--

DROP TABLE IF EXISTS `projectdata`;
CREATE TABLE `projectdata` (
  `projectid` int(10) unsigned NOT NULL,
  `proj_member_name` varchar(45) DEFAULT NULL,
  `projectname` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `projectdata`
--

/*!40000 ALTER TABLE `projectdata` DISABLE KEYS */;
INSERT INTO `projectdata` (`projectid`,`proj_member_name`,`projectname`,`role`) VALUES 
 (12,'Sampath..!','Avatar..','Owner'),
 (13,'Sampath..!','2012','Owner'),
 (14,'Sampath..!','Taken','Owner'),
 (13,'Suneel..!','2012','Programmer');
/*!40000 ALTER TABLE `projectdata` ENABLE KEYS */;


--
-- Definition of table `projects`
--

DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `projectId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ScreenName` varchar(50) NOT NULL,
  `projname` varchar(100) NOT NULL,
  `projdesc` text NOT NULL,
  `projectimgname` varchar(45) NOT NULL,
  `imagesource` varchar(300) NOT NULL,
  `creationtime` varchar(50) NOT NULL,
  `closingdate` varchar(45) NOT NULL,
  `visibility` varchar(45) NOT NULL,
  `budgetType` varchar(45) NOT NULL,
  PRIMARY KEY (`projectId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `projects`
--

/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` (`projectId`,`ScreenName`,`projname`,`projdesc`,`projectimgname`,`imagesource`,`creationtime`,`closingdate`,`visibility`,`budgetType`) VALUES 
 (12,'Sampath..!','Avatar..','This is about Movie Avatar..','avatar.jpg','../WebDav/repository/a1bf4bb2/161f/4d77/a790/9eb216cc8e0b/avatar.jpg','2010/04/15 22:28:20','2012/04/27 24:00:00','Public','Time and Material'),
 (13,'Sampath..!','2012','This is about movie 2012..','2012 movie poster.jpg','../WebDav/repository/64758286/ca0c/4d88/bbc3/b8f2fc8c0b54/2012 movie poster.jpg','2010/04/15 22:33:24','2010/04/28 24:00:00','Private','Time and Material'),
 (14,'Sampath..!','Taken','This is about movie Taken..','taken_galleryposter.jpg','../WebDav/repository/d6664a55/2e95/4e5e/81b2/2612cc0f2965/taken_galleryposter.jpg','2010/04/15 22:34:26','2010/04/28 24:00:00','Private','Time and Material');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;


--
-- Definition of table `scraps`
--

DROP TABLE IF EXISTS `scraps`;
CREATE TABLE `scraps` (
  `MessageId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Message` varchar(2000) NOT NULL,
  `Posttime` varchar(70) NOT NULL,
  `fromuser` varchar(45) NOT NULL,
  `touser` varchar(45) NOT NULL,
  PRIMARY KEY (`MessageId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `scraps`
--

/*!40000 ALTER TABLE `scraps` DISABLE KEYS */;
INSERT INTO `scraps` (`MessageId`,`Message`,`Posttime`,`fromuser`,`touser`) VALUES 
 (14,'Hi..\r','2010/04/15 22:55:00','Sampath..!','Suneel..!'),
 (15,'hi.. how r u..','2010/04/15 22:56:15','Suneel..!','Sampath..!'),
 (16,'I am fine.. what about you..','2010/04/15 22:56:52','Sampath..!','Suneel..!'),
 (17,'me too fine..','2010/04/15 22:57:27','Suneel..!','Sampath..!'),
 (18,'hi','2010/04/17 21:52:43','Suneel..!','fatima'),
 (19,'hello','2010/04/17 21:53:32','fatima','Suneel..!'),
 (20,'how u doing???','2010/04/17 21:54:14','Suneel..!','fatima');
/*!40000 ALTER TABLE `scraps` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
