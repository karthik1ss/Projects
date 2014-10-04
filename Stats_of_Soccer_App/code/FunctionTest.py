'''
Created on Apr 9, 2014

@author: Amriga Maaplai
'''
from common.callSenna import commandLineCall
from crawler.ParseHTMLData import browsePage
from NLPEngine.ParseSennaOutput import processEventGoal
from NLPEngine.ParseSennaOutput import processEventYellowCard
from NLPEngine.ParseSennaOutput import processEventSubstitution
from db.db_common import *

browsePage()
#commandLineCall()
#processEventGoal()
#processEventYellowCard()
#processEventSubstitution()

#global cur
#start_db_connection()