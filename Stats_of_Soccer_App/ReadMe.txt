Statistics for Soccer application using NLP
-------------------------------------------


Description:
------------
This project builds a statistic for a soccer match that either has been completed or ongoing.
The application crawls a website called goal.com and tries to read commentary between two teams.

Software Architecture
---------------------
The project has been built on Python.
MySQL db has been used for persistence.
The application makes use of Pusher, Senna and Beautiful Soup tools to build the statistics.

Requirements
------------
1.Download and install 'Senna' and copy the contents of senna folder into '/libraries/senna ' folder.
2.Download and pip-install Pusher app for python 2.7
3.Download and pip-install Beautiful Soup 4 for python 2.7
3.Download and pip-install MySQLDB package for python 2.7
4.Run nlp_db.sql and DB_Changes.sql file to create the necessary tables

Important
----------
The system from which this application is being executed must be connected to the internet.
The result can be seen in any system, connected to the internet, by running the index.html alone from the system.

Running the Application
-----------------------
1. Configure the URL in MatchSettings.py under the project root directory.
2. Enter the match commentary URL and Players URL
	Example
	-------
	pageURL = "http://www.goal.com/en/match/109640/manchester-united-vs-aston-villa/play-by-play"
	playersURL = "http://www.goal.com/en/match/109640/manchester-united-vs-aston-villa/lineup-stats"
3. Run MainFile_NLPEngine.py file located under the '/code/NLPEngine/' directory.
4. Launch the index.html located under '/ui/'

The page will get refershed automatically.
