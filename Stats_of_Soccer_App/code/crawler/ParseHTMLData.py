'''
Created on Apr 15, 2014

@author: Harish Babu Arunachalam
'''
from CrawlSite import get_all_links,get_page
from HTMLParser import HTMLParser 
#from bs4 import BeautifulSoup
from bs4 import BeautifulSoup
from collections import OrderedDict
import os,globals

class MyHtmlParser(HTMLParser):
    def handle_data(self,data):
        print data,"\n"
        return

# function to extract commentary data from the given url
# PageURL = http://www.goal.com/en/match/109640/man-utd-vs-aston-villa/play-by-play

def browsePage(pageURL):
    print "output"
    
    #pageURL = 
    #soup = BeautifulSoup(get_page("http://www.goal.com/en-us/match/109676/swansea-city-vs-chelsea/play-by-play"))
    soup = BeautifulSoup(get_page(pageURL))
    #print soup.text
    #for str1 in soup.strings:
     #print str1
    #print os.path.dirname(os.path.abspath(__file__))
    fp = open("../../code/MatchRepo/commentary.txt","wb")
    tag = soup.div
    var1 = "live_comments_minute"
    var2 = "live_comments_text"
    minute = ''
    commentary = ''
    totalCommentary = ''
    minuteCheck = ''
    minCommentaryMap = OrderedDict()
    for value in soup.find_all('div',{'class':(var1,var2)}):
        #print value
        if str(value) <> None:
            if var1 in str(value):
                #print value
                #print value.string," Var1 "
                minute = getMinuteFromCommentary(value)
                #print minute
                
            elif var2 in str(value):
                #print 
                if (value.span <> None):
                    if(value.br <> None):
                        for val in value.stripped_strings:
                            #print val
                            
                            commentary = commentary+val+" "
                        #print "inside BR"
                    else:
                        #print value.span.text," ------------ "
                        if(value.span.text == 'Substitution'):
#                                for imgs in value.find_all('img'):
#                                    print imgs['src']
                                index = 0
                                for val in value.stripped_strings:
                                    if (index==0):
                                        commentary = commentary+val+' '
                                        index= index+1
                                    elif(index==1):
                                        commentary = commentary + 'Out '+val+' '
                                        index = index+1
                                    elif (index == 2):
                                        commentary = commentary + 'In '+val+' '
                                        index = index+1
                        else:
                            commentary = value.text
                else:
                    commentary =value.text
                #commentary =value.string
                if minute is not None:
                    if commentary is not '':
                        minCommentaryMap[minute] = commentary
                        commentary = ''
                        minute =None 
                    else:
                        minCommentaryMap[minute] = ''
                        minute = None
                else:
                    commentary = '' 
    sorted(minCommentaryMap.keys())
    for key in reversed(minCommentaryMap.keys()):
        #print key," ",minCommentaryMap[key]
        writeVal = str("\n"+key+" "+str(minCommentaryMap[key]).replace('\n',''))
        fp.write(writeVal)
    fp.close()
    
#commandLineCall()

#method to get the minutes from the commentary html
def getMinuteFromCommentary(minuteVar):
    #print minuteVar.contents[1].string
    return str(minuteVar.contents[1].string)

# function to extract players information  from the given url
# PageURL = http://www.goal.com/en/match/109640/man-utd-vs-aston-villa/lineup-stats

def getTeamPlayers(pageURL):
    #soup = BeautifulSoup(get_page("http://www.goal.com/en-us/match/109384/aston-villa-vs-manchester-united/lineup-stats"))
    soup = BeautifulSoup(get_page(pageURL))
    #print soup.text
    teamFileEntry = open("../../code/MatchRepo/TeamPlayersList.txt",'wb')
    playerMap1 = OrderedDict()
    if(soup.find('div',{'id':('team1_players_lineup')}).h3 <> None):
        team1 = team1 = soup.find('div',{'id':('team1_players_lineup')}).h3.text
        print team1

    #print team1
    globals.team1Name = team1
    
    playerMap2 = OrderedDict()
    team2 = soup.find('div',{'id':('team2_players_lineup')}).h3.text
    #print team2
    globals.team2Name = team2
    index = 0
    sub=0
    #for idVal in ('team1_players_lineup','team2_players_lineup'):
    for val in soup.find_all('div',{'id':('team1_players_lineup','substitutes_team1')}):
        
        if(index==0):
                playerMap1['Players'] = ''
        elif(index == 1):
                playerMap1['Substitutes'] = ''
                sub=1
        index = index+1
        for val1 in val.find_all('div',{'class':('player_name_lineup')}):
            #print val1.a.text,' ',val1.strong.text
            val2 = val1.stripped_strings
            #print list(val2)
            role=0
            for val3 in list(val2):
                if(role==0):
                    playerMap1[val3] = ''
                    player = val3
                    role = 1
                else:
                    playerMap1[player] = val3
                    role = 0
                    #print playerMap1[player]
                    if(sub==0):
                        globals.teamPlayersMap[player] = [val3,team1]
        
    #print playerMap1
    teamFileEntry.writelines(team1+'\n')
    for playerName in playerMap1.keys():
        teamFileEntry.writelines(playerName+','+playerMap1[playerName]+'\n')
    sub=0
    for val in soup.find_all('div',{'id':('team2_players_lineup','substitutes_team2')}):
        #print val
        if(index==2):
            playerMap2['Players'] = ''
        elif(index == 3):
            playerMap2['Substitutes'] = ''
            sub=1
        #print index
        index = index+1
        for val1 in val.find_all('div',{'class':('player_name_lineup right-col')}):
            #print val1.a.text,' ',val1.strong.text
            val2 = val1.stripped_strings
            #print list(val2)
            role=0
            for val3 in list(val2):
                if(role==0):
                    playerMap2[val3] = ''
                    player = val3
                    role = 1
                else:
                    playerMap2[player] = val3
                    role = 0
                    if(sub==0):
                        globals.teamPlayersMap[player] = [val3,team2]
                    #print playerMap2[player]
        
    #print playerMap2
    print globals.teamPlayersMap
    
    teamFileEntry.write('\n'+team2+'\n')
    for playerName in playerMap2.keys():
        teamFileEntry.writelines(playerName+','+playerMap2[playerName]+'\n')
#    match=0
#    matchedPlayer = ''
#    matchedInfo = []
#    for keys in globals.teamPlayersMap.keys():
#        if('Rooney' in keys):
#            match = 1
#            matchedInfo = globals.teamPlayersMap[keys]
#            del globals.teamPlayersMap[keys]
#            InPlayer = 'Alexander'+' '+'Wilcox'
#            globals.teamPlayersMap[InPlayer] = matchedInfo
#    print globals.teamPlayersMap[InPlayer]
#    print globals.teamPlayersMap                      
    return
