__author__ = 'Karthik'

import db_common

import globals

#globals.match_id = 7

db_common.start_db_connection()
#
#
result = db_common.get_match_id("Everton", "Fulham")
print result
#
# #db_common.get_match_stats(1,0)
#
# match_fields = {"home_team_goals":1,"home_team_yellow_cards":1}
# #
# #
# #
# match = db_common.edit_match_stats(34, match_fields)
# #
# print match['data']['home_team_goals']
# #
# print match['data']['home_team_yellow_cards']

#db_common.get_teams()

#result = db_common.get_player_and_team_id("Wayne:Rooney")

#print result