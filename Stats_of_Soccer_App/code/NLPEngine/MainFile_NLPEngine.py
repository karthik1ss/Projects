__author__ = 'Karthik'

from db import db_common

import sys
import re
import SplitFile
import InvokeSenna
import ParseSennaOutput
import globals
import ProcessPlayers
import MatchSettings
import pusher
import time
from code.crawler import ParseHTMLData


reload(sys)
sys.setdefaultencoding('utf-8')


# Running Web Crawler Program


ParseHTMLData.browsePage(MatchSettings.pageURL)
ParseHTMLData.getTeamPlayers(MatchSettings.playersURL)


# Inserting players into database

ProcessPlayers.process_team_players()

# match_id = 32
# globals.match_id = match_id

match_id = globals.match_id
print match_id

# Initializing SplitFile and InvokeSenna init parameters from global.py
SplitFile.init()
InvokeSenna.init()

# Splitting Commentary file into minute by minute text
SplitFile.split_file()

# Invoking Senna 90 times to process minute by minute text
InvokeSenna.invoke_senna()

# Initializing ParseSennaOutput init parameters from global.py

ParseSennaOutput.init()

# Iterate over minute by minute to calculate statisitics such as no of Goals scored, No. of Yellow cards, no of red cards,
#  substitutions happening (player replaced by another player) and calculating ball possession for each player

with open('../../code/common/outfilesList.txt','r') as f:
        outfiles = [word.strip() for word in f]

print "Calculating Statistics"

# connecting to database to invoke pusher code minute by minute

cur = db_common.start_db_connection()

# deleting previously held match records in match_events table
result = cur.execute("delete from match_events")

# print globals.teamPlayersMap

# Main loop which iterates over minute by minute

for item in outfiles:

    m = re.search('.*minute_(\d+)_',item)
    minute = m.group(1)
    #print minute

    # functions to process goals, Yellow Cards, Red Cards, Substitution and ball possession in the commentary
    #  iterating over minutes present in the commentary

    ParseSennaOutput.processEventGoal(item, minute)
    ParseSennaOutput.processEventYellowCard(item, minute)
    ParseSennaOutput.processEventRedCard(item, minute)
    ParseSennaOutput.processEventSubstitution(item, minute)
    ParseSennaOutput.processBallPossession(item, minute)

# pusher function to push the data minute by minute

db_common.get_match_snapshot(match_id)


