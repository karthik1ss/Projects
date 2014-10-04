# coding=utf-8

__author__ = 'Karthik'

from db import db_common
import unicodedata

# function to process players by scanning over TeamPlayersList.txt file (crawled from the web) and
# storing in players table

def process_team_players():
    global cur
    status = 1
    message = ''
    result_record = {}

    fh = open("../../code/MatchRepo/TeamPlayersList.txt", "rU")

    line_no = 1
    team_index = 0
    is_substitute = 0
    ind = 0

    prev_line = ''

    player_dict = {}


    for line in fh:

        stripped_line = line.strip()
        stripped_line = unicode(stripped_line,'utf-8')
        # print stripped_line

        stripped_line = unicodedata.normalize('NFKD', stripped_line).encode('ascii','ignore')

        # print stripped_line

        if stripped_line.find("Players") >= 0:
            team_index += 1
            if team_index == 1:
                home_team = prev_line
            elif team_index == 2:
                visiting_team = prev_line
            is_substitute = 0
            continue

        if stripped_line.find("Substitutes") >= 0:
            is_substitute = 1
            continue

        if team_index > 0:
            player_details = stripped_line.split(",")

            if len(player_details) > 1:
                player_dict[ind] = {'name': player_details[0], 'position': player_details[1], 'is_substitute': is_substitute,
                                'team_index': team_index}
                ind += 1

        line_no += 1
        prev_line = stripped_line

    #print player_dict

    print home_team
    print visiting_team

    cur = db_common.start_db_connection()

    match_id = db_common.get_match_id(home_team, visiting_team)
    print match_id
    match_stats_dict = db_common.get_match_stats(match_id,0)

    if match_stats_dict['status'] == 0:
        status = match_stats_dict['status']
        message = match_stats_dict['message']
    else:
        match_stats = match_stats_dict['data']

    result = cur.execute("delete from players")

    for index, player_details in player_dict.items():
        if player_details['team_index'] == 1:
            player_record = {'name': player_details['name'], 'position':player_details['position'], 'team_id': match_stats['home_team_id'],
                             'is_substitute': player_details['is_substitute']}
            db_common.add_player(match_id, player_record)
        elif player_details['team_index'] == 2:
            player_record = {'name': player_details['name'], 'position':player_details['position'], 'team_id': match_stats['visiting_team_id'],
                             'is_substitute': player_details['is_substitute']}
            db_common.add_player(match_id, player_record)


    fh.close()

    result_record['status'] = status
    result_record['message'] = message
    return