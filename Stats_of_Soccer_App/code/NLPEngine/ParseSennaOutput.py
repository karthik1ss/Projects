# coding=utf-8
__author__ = 'Rosemary'
# authors  - Karthik and Harish

import globals
import unicodedata
from db import db_common

global match_id
global home_team_id
global visiting_team_id

# initialization of global variables

def init():

    global match_id
    match_id = globals.match_id


def goalProcess():
    state = 0
    player_mutd_state = 0
    players_mutd = "(De Gea|Rafael|Jones|Vidic|Buttner|Young|Fellaini|Fletcher|Kagawa|Mata|Rooney|Hernandez)"
    players_aston = "(Guzan|Bacuna|Vlaar|Clark|Bertrand|Westwood|Delph|Albrighton|Weimann|Benteke|Agbonlahor)"
    player_westwood_goals = 0
    player_rooney_goals = 0
    player_hernandez_goals = 0
    player_mata_goals = 0
    goals_mutd = 0
    goals_aston = 0
    
    with open("../commentary.txt") as f:
        lines = f.readlines()
    lines.reverse()
    #print lines
    
    for line in lines:
        # for single line
        words = line.split();
        print words
        column_no = 0;
        state =0;
        for word in words:
            if state == 0:
                if column_no == 1 or column_no == 2:
                    if word == "Goal":
                        goal_line = line.split();
                        for goal_word in goal_line:
                            if goal_word in players_mutd:
                                if goal_word == "Rooney":
                                    player_rooney_goals = player_rooney_goals + 1;
                                    print "Wayne Rooney : "+str(player_rooney_goals)
                                elif goal_word == "Mata":
                                    player_mata_goals = player_mata_goals + 1;
                                    print "Juan Mata : "+str(player_mata_goals)
                                elif goal_word == "Hernandez":
                                    player_hernandez_goals = player_hernandez_goals + 1;
                                    print "Javier 'Chicharito' Hernandez : "+str(player_hernandez_goals)
                                # print goal_word+" Man Utd"
                                goals_mutd = goals_mutd + 1
                                print "Man Utd: "+str(goals_mutd)+" vs Aston  Villa: "+str(goals_aston)
                            elif goal_word in players_aston:
                                if goal_word == "Westwood":
                                    player_westwood_goals = player_westwood_goals + 1;
                                    print "Ashley Westwood : "+str(player_westwood_goals)
                                # print goal_word+" Aston"
                                goals_aston = goals_aston + 1
                                print "Man Utd: "+str(goals_mutd)+" vs Aston : "+str(goals_aston)
                    column_no = column_no + 1;
                else:
                    # print words[0]
                    column_no = column_no + 1;
    
        # Have to write a file at the end of reading each line

# function to process goal by parsing senna processed result for each minute.
# Senna uses NER to identify players and team names . using this information to capture player names and team names

def processEventGoal(infile, minute):

    #file = open("../../code/MatchRepo/Match_{0}/Sennaoutput/samplefile.txt".format(match_id))

    file = open(infile, 'r')
    fileContent = file.readlines()
    goalMatch=0
    FName = ''
    MName = ''
    LName=''
    #print str(fileContent[0].split()).strip()

    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Goal','GOAL'):
                goalMatch = 1
                FName = ''
                MName=''
                LName = ''
                #print 'inside'
            elif (goalMatch ==1):
                if (line[1] == 'B-PER'):
                    FName = line[0]
                elif(line[1]== 'I-PER'):
                    MName=line[0]
                elif(line[1]=='E-PER'):
                    LName = line[0]
                    goalMatch=0
                    name = FName,' ',MName,' ',LName
                    name = unicode_text(name)
                    print 'Goal By ',name
                    print "Updating in Database"
                    update_database(name, "goal", minute)
                    break;
                elif(line[1] == 'S-PER'):
                        FName = line[0]
                        goalMatch=0
                        name = FName
                        name = unicode_text(name)
                        print 'Goal By ',name
                        print "Updating in Database"
                        update_database(name, "goal", minute)
                        break;
    return

# function to process Yellow Card by parsing senna processed result for each minute.
# Senna uses NER to identify players and team names . using this information to capture player names and team names

def processEventYellowCard(infile, minute):

    file = open(infile, 'r')
    #file = open("../code/MatchRepo/Match1/Sennaoutput/sampleYC.txt")
    fileContent = file.readlines()
    Yellow=0
    Card=0
    FName = ''
    MName = ''
    LName=''
    #print str(fileContent[0].split()).strip()
    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Yellow','YELLOW'):
                Yellow = 1
                FName = ''
                MName=''
                LName = ''
                #print 'inside'
            elif (Yellow ==1):
                if(line[0] in ('Card','card','CARD')):
                    Card=1
                elif(Card==1):
                    if (line[1] == 'B-PER'):
                        FName = line[0]
                    elif(line[1]== 'I-PER'):
                        MName=line[0]
                    elif(line[1]=='E-PER'):
                        LName = line[0]
                        Yellow=0
                        Card=0
                        print 'Yellow card for ',FName,' ',MName,' ',LName
                        name = FName,' ',MName,' ',LName
                        name = unicode_text(name)
                        print "Updating in Database"
                        update_database(name, "yellowcard", minute)
                        break;
                    elif(line[1] == 'S-PER'):
                        FName = line[0]
                        Yellow=0
                        Card=0
                        print 'Yellow card for ',FName
                        name = FName
                        name = unicode_text(name)
                        print "Updating in Database"
                        update_database(name, "yellowcard", minute)
                        break;
                    
    
    return

# function to process Red Card by parsing senna processed result for each minute.
# Senna uses NER to identify players and team names . using this information to capture player names and team names

def processEventRedCard(infile, minute):

    file = open(infile, 'r')
    #file = open("../code/MatchRepo/Match1/Sennaoutput/sampleRC.txt")
    fileContent = file.readlines()
    Red=0
    Card=0
    FName = ''
    MName = ''
    LName=''
    #print str(fileContent[0].split()).strip()
    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Red','RED'):
                Red = 1
                FName = ''
                MName=''
                LName = ''
                #print 'inside'
            elif (Red ==1):
                if(line[0] in ('Card','card','CARD')):
                    Card=1
                elif(Card==1):
                    if (line[1] == 'B-PER'):
                        FName = line[0]
                    elif(line[1]== 'I-PER'):
                        MName=line[0]
                    elif(line[1]=='E-PER'):
                        LName = line[0]
                        Red=0
                        Card=0
                        print 'Red card for ',FName,' ',MName,' ',LName
                        name = FName,' ',MName,' ',LName
                        name = unicode_text(name)
                        print "Updating in Database"
                        update_database(name, "redcard", minute)
                        break;
                    elif(line[1] == 'S-PER'):
                        FName = line[0]
                        Red=0
                        Card=0
                        print 'Red card for ',FName
                        name = FName
                        name = unicode_text(name)
                        print "Updating in Database"
                        update_database(name, "redcard", minute)
                        break;
                    
    
    return

# function to process substitution by parsing senna processed result for each minute.
# Senna uses NER to identify players and team names . using this information to capture player names and team names
# it is achieved by iterating over the map of current team players and identifying player names which are not there in the
# map and exists in the commentary

def processEventSubstitution(infile, minute):

    file = open(infile, 'r')
    #openFile = open("../code/MatchRepo/Match1/Sennaoutput/sampleSubstitution.txt")
    fileContent = file.readlines()
    substitution = 0
    Out = 0
    In = 0
    OutFName = ''
    OutMName = ''
    OutLName=''
    InFName = ''
    InMName = ''
    InLName=''
    #print str(fileContent[0].split()).strip()
    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Substitution','SUBSTITUTION'):
                substitution = 1
                OutFName = ''
                OutMName=''
                OutLName = ''
                InFName = ''
                InMName=''
                InLName = ''
                #print 'inside'
            elif (substitution ==1):
                if(line[0] in ('Out','OUT','out')):
                    Out=1
                elif (line[0] in ('In','IN','in')):
                    In = 1
                    #print 'in Value '
                elif(Out==1):
                    if(In==1):
                        if (line[1] in ('B-PER','B-ORG')):
                            InFName = line[0]
                        elif(line[1] in ('I-PER','I-ORG')):
                            InMName=line[0]
                        elif(line[1] in ('E-PER','E-ORG')):
                            InLName = line[0]
                            Substitution=0
                            Out=0
                            In=0
                            
                            print 'Substitution for ',OutFName,' ',OutMName,' ',OutLName
                            print 'by ',InFName,' ',InMName,' ',InLName
                            outname = OutFName,' ',OutMName,' ',OutLName
                            inname = InFName,' ',InMName,' ',InLName

                            match=0
                            matchedPlayer = ''
                            matchedInfo = []
                            print globals.teamPlayersMap.keys()
                            #update the current players list by replacing playing 11 name
                            for keys in globals.teamPlayersMap.keys():
                                print (OutFName in keys)
                                if(OutFName in keys):
                                    match = 1
                                    matchedInfo = globals.teamPlayersMap[keys]
                                    print minute
                                    # print 'outName',OutLName,keys
                                    # print globals.teamPlayersMap[keys]
                                    del globals.teamPlayersMap[keys]
                                    InPlayer = InFName+' '+InMName+' '+InLName
                                    globals.teamPlayersMap[InPlayer] = matchedInfo
                                    # print globals.teamPlayersMap[InPlayer]
                                    # print globals.teamPlayersMap

                                    print "Updating in Database"
                                    print "Out Player:",outname
                                    print "In Player:",inname
                                    outname = unicode_text(outname)
                                    inname = unicode_text(inname)
                                    update_susbsitution_db(outname, inname, "substitution", minute)
                                    break;
                            break;
                        # print "in player processing"
                        # print line[0]
                        # print In
                            
                    elif(In==0):
                        if (line[1] in ('B-PER','B-ORG')):
                            OutFName = line[0]
                        elif(line[1] in ('I-PER','I-ORG')):
                            OutMName=line[0]
                        elif(line[1] in ('E-PER','E-ORG')):
                            OutLName = line[0]
                        # print "in Out player processing"
                        # print line[0]
                        # print In
    return

# function to process Half time by parsing senna processed result for each minute.

def processEventHalfTime(infile, minute):

    file = open(infile, 'r')
    #openFile = open("../code/MatchRepo/Match1/Sennaoutput/sampleRC.txt")
    fileContent = file.readlines()
    Half=0
    Card=0
    print str(fileContent[0].split()).strip()
    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Half','HALF'):
                Half = 1
            elif (Half ==1):
                if(line[0] in ('Time','TIME')):
                    Half=0
                    #Call function to process statistics for half time
                    break;
                    
    return

# function to process Full time by parsing senna processed result for each minute.

def processEventFullTime(infile, minute):

    file = open(infile, 'r')
    #openFile = open("../code/MatchRepo/Match1/Sennaoutput/sampleRC.txt")
    fileContent = file.readlines()
    Full=0
    Card=0
    print str(fileContent[0].split()).strip()
    for line in fileContent:
        line = line.split()
        #print len(line)
        if len(line)>=2:
            if line[0] in ('Full','FULL'):
                Full = 1
            elif (Full ==1):
                if(line[0] in ('Time','TIME')):
                    Full=0
                    #Call function to process statistics for half time
                    break;
                    
    return


# function to process Ball possession of the player by parsing senna processed result for each minute.
# this is calculated by iterating over a map of current players in the match and incrementing currentBallPossession variable
# for the corresponding player(whoever has the ball)

def processBallPossession(inFile, minute):
    #open file
    file = open(inFile,'r')
    #read the content of the files
    fileContent = file.readlines()
    #print fileContent
    for line in fileContent:
        line = line.split()
        #print len(line)
        if(len(line)>1):
            #print line
            #if the statement if one of the below events, then break
            if (line[0] in ('Yellow','YELLOW','RED','Red','Substitution','SUBSTITUTION','Half','HALF','FULL','Full')):
                return
            elif(line[1] in ('B-PER','S-PER','E-PER')):
                    info = []
                    # print 'inside'
                    #iterate through the players list and take the ball possession details

                    for keys in globals.teamPlayersMap.keys():
                        #print keys
                        if(line[0] in keys):
                            #check for match and get the team name details for the player
                            info = globals.teamPlayersMap[keys]
                            globals.currentBallPossession[minute] = keys
                            if(globals.team1Name == info[1]):
                                globals.team1Possession = globals.team1Possession+1
                            elif(globals.team2Name == info[1]):
                                globals.team2Possession = globals.team2Possession+1
                            # print keys
                            print globals.currentBallPossession
                            break

    return


def unicode_text(name):

    name = ''.join(name)
    name = unicode(name,'utf-8')
    name = unicodedata.normalize('NFKD', name).encode('ascii','ignore')
    name = name.split()
    return name


# function to update events Goal, Yellow Card, Red Card in the database in match_events table

def update_database(name, evnt, minute):

    global match_id

    db_common.start_db_connection()

    result = db_common.get_player_and_team_id(name)

    print result

    event = {}
    event['match_id'] = match_id
    event['team_id'] = int(result[1])
    event['player_id'] = int(result[0])
    event['event_time'] = minute
    event['event_type'] = evnt

    result_record = db_common.add_match_event(event)
    #print result_record


# function to update events Substitution in the database in match_events table

def update_susbsitution_db(outname, inname, evnt, minute):
    global match_id

    db_common.start_db_connection()

    result = db_common.get_player_and_team_id(inname)

    result1 = db_common.get_player_and_team_id(outname)

    print result
    print result1

    event = {}
    event['match_id'] = match_id
    event['team_id'] = int(result[1])
    event['player_id'] = int(result[0])
    event['player_id_out'] = int(result1[0])
    event['event_time'] = minute
    event['event_type'] = evnt

    result_record = db_common.add_match_event(event)