# coding=utf-8

# authors  - Vivek and Karthik

import sys, MySQLdb, json, datetime
import globals
import time,math
import pusher
import unicodedata

global cur
global p

# function to establish db connection to mysql database nlp_db using MySQLdb connector for python

def start_db_connection():
    global cur
    db = MySQLdb.connect(host='127.0.0.1', port=3306, user='root', passwd='', db='nlp_db')
    print "Successfully connected with Database"
    cur = db.cursor()
    return cur

# function to add events Goal, Yellow Card, Red Card and Substitution in match_events table

def add_match_event(event):
    global cur

    status = 1
    message = ''
    result_record = {}
    fields_datatype = {}

    insert_event_record = {}


    while 1:
        result = cur.execute("desc match_events")
        print result
        print cur
        print cur.description

        for x in range(cur.rowcount):
            row = cur.fetchone()
            fields_datatype[row[0]] = row[1]

        if event['event_type'] == 'substitution':
            if not ('player_id_out' in event.keys() and event['player_id_out'] > 0):
                message = 'substitution player out id not passed'
                status = 0
                break
        else:
            event['player_id_out'] = 0

        for key in event:

            if key in fields_datatype.keys():
                if fields_datatype[key].find("varchar") >= 0:
                    insert_event_record[key] = "'" + str(event[key]) + "'"
                else:
                    insert_event_record[key] = event[key]

        mandatory_fields = fields_datatype.keys()


        insert_event_record['id'] = 0
        insert_event_record['created'] = "'%s'"%(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
        insert_event_record['modified'] = "'%s'"%(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))

        for field in mandatory_fields:
            if field not in insert_event_record.keys():
                message = 'mandatory field %s not present in the event'%(field)
                status = 0
                break

        if status == 0:
            break

        match_stats_dict = get_match_stats(event['match_id'],0)

        if match_stats_dict['status'] == 0:
            status = match_stats_dict['status']
            message = match_stats_dict['message']
            break
        else:
            match_stats = match_stats_dict['data']

        if event['team_id'] == int(match_stats['home_team_id']):
            prefix = 'home_team_'
        elif event['team_id'] == int(match_stats['visiting_team_id']):
            prefix = 'visiting_team_'
        else:
            status = 0
            message = 'invalid team_id passed'
            break

        query = "insert into match_events " \
                "(" + ','.join(map(str, insert_event_record.keys())) + ") " \
                                                                       "values ("+ ','.join(map(str, insert_event_record.values())) + ")"
        result = cur.execute(query)

        edit_fields = {}

        if event['event_type'] == 'goal':
            field_name = prefix + 'goals'
        elif event['event_type'] == 'freekick':
            field_name = prefix + 'free_kicks'
        elif event['event_type'] == 'corner':
            field_name = prefix + 'conrners'
        elif event['event_type'] == 'offside':
            field_name = prefix + 'offsides'
        elif event['event_type'] == 'yellowcard':
            field_name = prefix + 'yellow_cards'
        elif event['event_type'] == 'redcard':
            field_name = prefix + 'red_cards'
        elif event['event_type'] == 'penalty':
            field_name = prefix + 'penalties'
        elif event['event_type'] == 'substitution':
            field_name = 'substitution'
        else:
            status = 0
            message = 'invalid event_type %s passed'%(event['event_type'])
            break

        edit_fields[field_name] = 1

        # edit_status = edit_match_stats(event['match_id'],edit_fields)
        # if edit_status['status'] == 0:
        #     status = edit_status['status']
        #     message = edit_status['message']
        #     break

        break
    # while loop ends

    result_record['status'] = status
    result_record['message'] = message
    return result_record

# function to add players and their position in the players table

def add_player(match_id = 0, player_record = {}):
    # player_record needs player name, position, team_id, substitute_flag
    global cur

    status = 1
    message = ''
    result_record = {}

    insert_player_record = {}

    while 1:
        if match_id == 0:
            status = 0
            message = 'add_player: match_id not passed - cannot proceed'
            break

        mandatory_fields = ['name', 'position', 'team_id', 'is_substitute']

        for field_name in mandatory_fields:
            if field_name not in player_record:
                status = 1
                message = 'add_player: mandatory field not present'
                break
            else:
                if field_name in ['name', 'position']:
                    insert_player_record[field_name] = "'"+player_record[field_name].replace("'","")+"'"
                else:
                    insert_player_record[field_name] = player_record[field_name]
        if status != 1:
            break

        insert_player_record['id'] = 0
        insert_player_record['match_id'] = match_id
        insert_player_record['created'] = "'%s'"%(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
        insert_player_record['modified'] = "'%s'"%(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))

        query = "insert into players " \
                "(" + ','.join(map(str, insert_player_record.keys())) + ") " \
                "values ("+ ','.join(map(str, insert_player_record.values())) + ")"
        print query
        result = cur.execute(query)

        break
    # while loop ends

    result_record['status'] = status
    result_record['message'] = message
    return result_record

def get_match_events(match_id=0, return_json = 1):
    global cur

    status = 1
    message = ''
    result_returned = {}

    lst_match_events = []
    result_match_events = {}


    while 1:

        if match_id == 0:
            status = 0
            message = 'match_id not passed'
            break

        result = cur.execute("desc match_events")

        for x in range(cur.rowcount):
            row = cur.fetchone()
            lst_match_events.append(row[0])

        str_match_events = ','.join(map(str, lst_match_events))
        query = "select " + str_match_events + " from match_events order by event_time asc"
        result = cur.execute(query)

        if result == 0:
            status = 0
            message = 'match_events empty'
            break

        for row_num in range(cur.rowcount):
            row = cur.fetchone()
            event = {}
            for index in range(len(lst_match_events)):
                event[lst_match_events[index]] = str(row[index])
            result_match_events[row_num] = event

        break
    # while ends

    result_returned['status'] = status
    result_returned['message'] = ''
    result_returned['data'] = result_match_events

    if return_json == 0:
        return result_returned
    else:
        json_result_match_events = json.dumps(result_match_events)
        return json_result_match_events
        #decoded_data =  json.loads(json_result_match_events)


# function to get teams

def get_teams():
    global cur
    query = "select id,name from teams"
    result = cur.execute(query)
    results_returned = []
    results_returned =  cur.fetchall()
    #print results_returned

    return results_returned


# function to retrieve match id of the current match

def get_match_id(home_team_name, visiting_team_name):
    global cur

    print home_team_name
    print visiting_team_name

    team_ids = []

    team_fields={}
    team_fields['position'] = 0
    team_fields['wins'] = 0
    team_fields['draws'] = 0
    team_fields['lost'] = 0
    team_fields['goals_for'] = 0
    team_fields['goals_against'] = 0
    team_fields['goals_difference'] = 0
    team_fields['points'] = 0
    team_fields['manager_id'] = 0
    team_fields['created'] = "'%s' "% (datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    team_fields['modified'] = "'%s' "% (datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))

    matches_fields = {}
    matches_fields['match_time'] = "'%s' "% (datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    matches_fields['created'] = "'%s'" % (datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    matches_fields['modified'] = "'%s' "% (datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))

    team_name = home_team_name

    for t in range(2):

        query = "select id from teams where name='"+team_name+"'"
        result = cur.execute(query)
        team_fields['name'] = "'" + team_name + "'"
        row = cur.fetchone()
        print team_fields.keys()
        print team_fields.values()
        if not result:
            query_insert = "insert into teams " \
                "(" + ','.join(map(str, team_fields.keys())) + ") " \
                "values (" + ','.join(map(str, team_fields.values())) + ")"
            results = cur.execute(query_insert)
            print query
            result_again =cur.execute(query)
            row = cur.fetchone()
            team_ids.append(row[0])

        else:
            print "Record Already Exists"
            team_ids.append(row[0])

        team_name = visiting_team_name



    print team_ids


    if len(team_ids) <> 0:

        query = " select count(*) from matches where home_team_id = "+ str(team_ids[0]) + " and visiting_team_id = " + str(team_ids[1])
        print query
        result1 = cur.execute(query)
        cnt = cur.fetchone()
        print cnt[0]

        if cnt[0] == 0:
            matches_fields['venue'] = "'home'"

        elif cnt[0] == 1:
            matches_fields['venue'] = "'away'"

        elif cnt[0] == 2:
            print "Record Already Exists"


        if cnt[0] == 0 or cnt[0] == 1:
            matches_fields['home_team_id'] = team_ids[0]
            matches_fields['visiting_team_id'] = team_ids[1]
            query = "insert into matches " \
                        "(" + ','.join(map(str, matches_fields.keys())) + ") " \
                        "values ("+ ','.join(map(str, matches_fields.values())) + ")"
            print query
            result = cur.execute(query)


        query = "select id from matches where home_team_id=" + str(team_ids[0]) + " and visiting_team_id=" + str(team_ids[1])
        result = cur.execute(query)
        row = cur.fetchone()
        if result:
            globals.match_id = row[0]
            return row[0]




def get_match_stats(match_id=0, return_json = 1):
    global cur

    status = 1
    message = ''
    result_returned = {}

    matches_fields = []
    stats = {}

    while 1:

        if match_id == 0:
            status = 0
            message = 'get_match_stats: match_id not passed'
            break

        result = cur.execute("desc matches")

        for x in range(cur.rowcount):
            row = cur.fetchone()
            matches_fields.append(row[0])

        pfx_match_fields = []
        for field_index in range(len(matches_fields)):
            pfx_match_fields.append('m.'+matches_fields[field_index])

        str_matches_fields = ','.join(map(str, pfx_match_fields))
        query = "select " + str_matches_fields + ", t1.name, t2.name from matches m , teams t1, teams t2 " \
                "where m.id = %d and m.home_team_id = t1.id and m.visiting_team_id = t2.id"%(match_id)

        #print query

        result = cur.execute(query)

        if result == 0:
            status = 0
            message = 'match_id not present in DB'
            break

        result_match_stats = {}
        for row_num in range(cur.rowcount):
            row = cur.fetchone()
            stats = {}
            for index in range(len(matches_fields)):
                stats[matches_fields[index]] = str(row[index])
            index += 1
            stats['home_team_name'] = str(row[index])
            index += 1
            stats['visiting_team_name'] = str(row[index])
            break

        break
    # while ends

    result_returned['status'] = status
    result_returned['message'] = message
    result_returned['data'] = stats

    #print result_returned

    if return_json == 1:
        json_result_match_stats = json.dumps(result_returned)
        return json_result_match_stats
        #decoded_data =  json.loads(json_result_match_stats)
    else:
        return result_returned



def edit_match_stats(match_id, edit_fields):

    print match_id
    message = ''
    status = 1
    result_returned = {}

    matches_fields = []

    while 1:

        if match_id == 0:
            status = 0
            message = 'match_id not passed'
            break

        #result_record = get_match_stats(1,0)

        result_record = get_match_stats(match_id,0)

        if result_record['status'] == 0:
            status = result_record['status']
            message = result_record['message']
            break
        else:
            prev_match_stats = result_record['data']

        incr_fields = ['home_team_goals','home_team_free_kicks','home_team_penalties','home_team_total_shots','home_team_shots_on_target',
                       'home_team_shots_off_target','home_team_corners','home_team_offsides','home_team_fouls','home_team_yellow_cards',
                       'home_team_red_cards','home_team_crosses','visiting_team_goals','visiting_team_free_kicks','visiting_team_penalties',
                       'visiting_team_total_shots','visiting_team_shots_on_target','visiting_team_shots_off_target','visiting_team_corners',
                       'visiting_team_offsides','visiting_team_fouls','visiting_team_yellow_cards','visiting_team_red_cards','visiting_team_crosses']

        new_match_stats_edit = {}
        for h,v in edit_fields.items():
            if h in prev_match_stats:
                if h in incr_fields:
                    new_match_stats_edit[h] = int(prev_match_stats[h]) + int(v)
                else:
                    new_match_stats_edit[h] = v

        loop_count = 1
        update_fields = ''
        for field, value in new_match_stats_edit.items():
            if loop_count == 1:
                update_fields = update_fields + field + '=' + str(value)
            else:
                update_fields = update_fields + ',' + field + '=' + str(value)
            loop_count += 1

        query = "update matches set %s where id = %d"%(update_fields,match_id)

        result = cur.execute(query)

        break
    # while ends

    result_record['status'] = status
    result_record['message'] = message

    print result_record
    return result_record


def get_players(match_id, return_json = 1):
    global cur

    status = 1
    message = ''
    result_returned = {}

    players_fields = []
    players = {}

    while 1:

        if match_id == 0:
            status = 0
            message = 'match_id not passed'
            break

        match_stats_dict = get_match_stats(match_id,0)

        if match_stats_dict['status'] == 0:
            status = match_stats_dict['status']
            message = match_stats_dict['message']
            break
        else:
            match_stats = match_stats_dict['data']

        result = cur.execute("desc players")

        for x in range(cur.rowcount):
            row = cur.fetchone()
            players_fields.append(row[0])

        str_players_fields = ','.join(map(str, players_fields))

        query = "select " + str_players_fields + " from players " \
                                                 "where match_id = %d and is_substitute = 0"%(match_id)

        result = cur.execute(query)

        team_id_index = players_fields.index('team_id')
        player_id_index = players_fields.index('id')
        name_index = players_fields.index('name')
        position_index = players_fields.index('position')
        substitute_index = players_fields.index('is_substitute')

        map_position = {'Goalkeeper':'goalie','Defender':'defender', 'Midfielder':'midfielder', 'Striker':'forward'}

        result_match_stats = {}
        player_index = 0
        players['team1'] = {}
        players['team2'] = {}
        for row_num in range(cur.rowcount):
            row = cur.fetchone()
            if row[team_id_index] == long(match_stats['home_team_id']):
                players['team1'][player_index] = {'player_id':row[player_id_index], 'pname': row[name_index],
                                               'position': map_position[row[position_index]], 'is_substitute': row[substitute_index]}
            elif row[team_id_index] == long(match_stats['visiting_team_id']):
                players['team2'][player_index] = {'player_id':row[player_id_index], 'pname': row[name_index],
                                               'position': map_position[row[position_index]], 'is_substitute': row[substitute_index]}
            player_index += 1

        break
    # while ends

    result_returned['status'] = status
    result_returned['message'] = ''
    result_returned['data'] = players

    if return_json == 1:
        json_result_match_stats = json.dumps(result_returned)
        return json_result_match_stats
        #decoded_data =  json.loads(json_result_match_stats)
    else:
        return result_returned


def get_player_by_id(match_id = 0, player_id = 0, return_json = 1):
    global cur

    status = 1
    message = ''
    result_returned = {}

    players_fields = []
    players = {}

    while 1:

        if match_id == 0:
            status = 0
            message = 'match_id not passed'
            break

        if player_id == 0:
            status = 0
            message = 'player_id not passed'
            break

        match_stats_dict = get_match_stats(match_id,0)

        if match_stats_dict['status'] == 0:
            status = match_stats_dict['status']
            message = match_stats_dict['message']
            break
        else:
            match_stats = match_stats_dict['data']

        result = cur.execute("desc players")

        for x in range(cur.rowcount):
            row = cur.fetchone()
            players_fields.append(row[0])

        str_players_fields = ','.join(map(str, players_fields))

        query = "select " + str_players_fields + " from players " \
                                                 "where match_id = %d and id = %d"%(int(match_id), int(player_id))

        result = cur.execute(query)

        team_id_index = players_fields.index('team_id')
        player_id_index = players_fields.index('id')
        name_index = players_fields.index('name')
        position_index = players_fields.index('position')
        substitute_index = players_fields.index('is_substitute')

        map_position = {'Goalkeeper':'goalie','Defender':'defender', 'Midfielder':'midfielder', 'Striker':'forward'}

        player_record = {}
        for row_num in range(cur.rowcount):
            row = cur.fetchone()

            player_record = {'player_id':row[player_id_index], 'pname': row[name_index],
                                           'position': map_position[row[position_index]], 'is_substitute': row[substitute_index]}
            break

        break
    # while ends

    result_returned['status'] = status
    result_returned['message'] = ''
    result_returned['data'] = player_record

    if return_json == 1:
        json_result_match_stats = json.dumps(result_returned)
        return json_result_match_stats
        #decoded_data =  json.loads(json_result_match_stats)
    else:
        return result_returned


def get_player_and_team_id(name):

    global cur
    print name

    f_name = name[0]

    if len(name) == 1:
        m_name = ''
        l_name = ''
    elif len(name) == 2:
        m_name = ''
        l_name = name[1]
    elif len(name) == 3:
        m_name = name[1]
        l_name = name[2]

    print f_name
    print m_name
    print l_name

    query = "select id, team_id from players where name like '%" + f_name.replace("'","") + "%" + l_name.replace("'","") + "%'"
    print query
    result = cur.execute(query)
    results_returned = []

    rows = cur.fetchall()
    #print rows
    if result:
        for item in rows[0]:
            results_returned.append(item)

    else:
        query = "select id, team_id from players where name like '%" + f_name.replace("'","") + "%" + m_name.replace("'","") + "%'"
        print query
        result1 = cur.execute(query)
        rows = cur.fetchall()

        if result1:
            for item in rows[0]:
                results_returned.append(item)
        else:
            query = query = "select id, team_id from players where name like '%" + f_name.replace("'","") + "%'"
            print query
            result2 = cur.execute(query)
            rows = cur.fetchall()

            if result2:
                for item in rows[0]:
                    results_returned.append(item)

    # print results_returned
    return results_returned

# function for the pusher code to push data minute by minute . All the events, player names , team names are extracted
# and sent in json format for the pusher API to publish the data in index.html page

def get_match_snapshot(match_id=0, end_time = 90):
    message = ''
    status = 1
    result_record = {}
    global p

    while 1:

        if match_id == 0:
            status = 0
            message = 'match_id not passed'
            break

        p = pusher.Pusher(
            app_id='71849',
            key='4020e68dc8ff6cdc72f9',
            secret='3a509d09f79ff891b098'
            )

        #match_stats_result = get_match_stats(1,0)

        match_stats_result = get_match_stats(match_id,0)
        if match_stats_result['status'] == 0:
            status = match_stats_result['status']
            message = match_stats_result['message']
            break
        else:
            match_stats = match_stats_result['data']

        match_snapshot = {}
        match_snapshot['team1'] = {}
        match_snapshot['team1']['stats'] = {'yellow_cards' : 0,
                                         'free_kicks': 0,
                                         'corners': 0,
                                         'penalties': 0,
                                         'fouls': 0,
                                         'shots_on_target': 0,
                                        'shots_off_target': 0,
                                        'goals': 0,
                                        'red_cards': 0,
                                        'offsides': 0,
                                        'id': match_stats['home_team_id'],
                                        'tname': match_stats['home_team_name']}
        match_snapshot['team1']['players'] = {}

        match_snapshot['team2'] = {}
        match_snapshot['team2']['stats'] = {'yellow_cards' : 0,
                                         'free_kicks': 0,
                                         'corners': 0,
                                         'penalties': 0,
                                         'fouls': 0,
                                         'shots_on_target': 0,
                                        'shots_off_target': 0,
                                        'goals': 0,
                                        'red_cards': 0,
                                        'offsides': 0,
                                        'id': match_stats['visiting_team_id'],
                                        'tname': match_stats['visiting_team_name']}
        match_snapshot['team2']['players'] = {}
        match_snapshot['event'] = ''

        t1_id = match_stats['home_team_id']
        t2_id = match_stats['visiting_team_id']

        match_players_result = get_players(match_id, 0)
        if match_players_result['status'] == 0:
            status = match_players_result['status']
            message = match_players_result['message']
            break
        else:
            match_players = match_players_result['data']

        for team_index in match_players.keys():
            if team_index in ['team1','team2']:
                # match_snapshot[team_index]['players'] = match_players[team_index]

                for player_ind,record in match_players[team_index].items():
                    player_id = record['player_id']
                    match_snapshot[team_index]['players'][player_id] = record

                    match_snapshot[team_index]['players'][player_id]['yellow_cards'] = 0
                    match_snapshot[team_index]['players'][player_id]['red_cards'] = 0
                    match_snapshot[team_index]['players'][player_id]['goals'] = 0
                    match_snapshot[team_index]['players'][player_id]['hasball'] = 0

        match_events_record = get_match_events(match_id,0)
        if match_events_record['status'] == 0:
            status = match_events_record['status']
            message = match_events_record['message']
            break
        else:
            match_events = match_events_record['data']

        snapshot_min = 0

        for index, event in match_events.items():

            if event['event_type'] == 'goal':
                field_name = 'goals'
            elif event['event_type'] == 'freekick':
                field_name = 'free_kicks'
            elif event['event_type'] == 'corner':
                field_name = 'corners'
            elif event['event_type'] == 'offside':
                field_name = 'offsides'
            elif event['event_type'] == 'yellowcard':
                field_name = 'yellow_cards'
            elif event['event_type'] == 'redcard':
                field_name = 'red_cards'
            elif event['event_type'] == 'penalty':
                field_name = 'penalties'
            elif event['event_type'] == 'substitution':
                field_name = 'substitution'
            else:
                continue

            print str(snapshot_min)

            match_snapshot['minute'] = snapshot_min
            call_pusher(match_snapshot)

            snapshot_min += 1

            loop_upper_limit = min(math.ceil(float(event['event_time'])), end_time)

            while snapshot_min < int(loop_upper_limit):
                print str(snapshot_min)+' repeated'
                match_snapshot['minute'] = snapshot_min
                call_pusher(match_snapshot)

                snapshot_min += 1

            if float(event['event_time']) <= float(end_time):

                if event['team_id'] == t1_id:
                    team_index = 'team1'
                elif event['team_id'] == t2_id:
                    team_index = 'team2'
                else:
                    break

                if field_name == 'substitution':
                    player_id_in = event['player_id']
                    player_id_out = event['player_id_out']

                    new_player_returned = get_player_by_id(match_id, player_id_in, 0)
                    print new_player_returned
                    if new_player_returned['status'] == 0:
                        status = 0
                        message = 'error in substitution'
                        print message
                    else:
                        new_player_record = new_player_returned['data']
                        new_player_record['yellow_cards'] = 0
                        new_player_record['red_cards'] = 0
                        new_player_record['goals'] = 0
                        new_player_record['hasball'] = 0

                        if int(player_id_out) in match_snapshot[team_index]['players'].keys():
                            match_snapshot[team_index]['players'][player_id_in] = new_player_record

                            print type(player_id_out)
                            print match_snapshot[team_index]['players'][int(player_id_out)]
                            del match_snapshot[team_index]['players'][int(player_id_out)]

                        print match_snapshot


                else:
                    if field_name in match_snapshot[team_index]['stats'].keys():
                        match_snapshot[team_index]['stats'][field_name] = match_snapshot[team_index]['stats'][field_name] + 1
                    else:
                        match_snapshot[team_index]['stats'][field_name] = 1

                    if int(event['player_id']) in match_snapshot[team_index]['players'].keys():
                        if field_name in match_snapshot[team_index]['players'][int(event['player_id'])]:
                            match_snapshot[team_index]['players'][int(event['player_id'])][field_name] = match_snapshot[team_index]['players'][int(event['player_id'])][field_name] + 1
                        else:
                            match_snapshot[team_index]['players'][int(event['player_id'])][field_name] = 1

                match_snapshot['event'] = str(snapshot_min) + "' " + event['event_type'].title()
                print match_snapshot['event']

            else:
                break

        print str(snapshot_min)

        match_snapshot['minute'] = snapshot_min

        call_pusher(match_snapshot)

        snapshot_min += 1

        while snapshot_min <= end_time:
            print str(snapshot_min)+' repeated'
            match_snapshot['minute'] = snapshot_min
            call_pusher(match_snapshot)
            snapshot_min += 1

        break
    # while ends

    result_record['status'] = status
    result_record['message'] = message
    return result_record

def call_pusher(match_snapshot):
    global p
    player_iter = 1
    match_snapshot1 = {}
    match_snapshot1['team1'] = {}
    match_snapshot1['team1']['players'] = {}
    match_snapshot1['team1']['stats'] = match_snapshot['team1']['stats']
    match_snapshot1['team2'] = {}
    match_snapshot1['team2']['players'] = {}
    match_snapshot1['team2']['stats'] = match_snapshot['team2']['stats']

    if str(match_snapshot['minute']) in globals.currentBallPossession.keys():
        player_name = globals.currentBallPossession[str(match_snapshot['minute'])]
        if isinstance(player_name,str):
            player_name = unicode(player_name,'utf-8')

        player_name = unicodedata.normalize('NFKD', player_name).encode('ascii','ignore')

        split_player_name = player_name.split()
        print split_player_name
        result = get_player_and_team_id(split_player_name)
        hasball_player_id = int(result[0])
        hasball_team_id = int(result[1])
        hasball_set = 1
    else:
        hasball_player_id = 0
        hasball_team_id = 0
        hasball_set = 0

    for player_id, player_record in match_snapshot['team1']['players'].items():
        if hasball_set:
            if hasball_player_id == player_id:
                player_record['hasball'] = 1
            else:
                player_record['hasball'] = 0
        match_snapshot1['team1']['players']['player'+str(player_iter)] = player_record
        # del match_snapshot1['team1']['players'][player_id]
        player_iter += 1

    player_iter = 1
    for player_id, player_record in match_snapshot['team2']['players'].items():
        if hasball_set:
            if hasball_player_id == player_id:
                player_record['hasball'] = 1
            else:
                player_record['hasball'] = 0
        match_snapshot1['team2']['players']['player'+str(player_iter)] = player_record
        # del match_snapshot1['team2']['players'][player_id]
        player_iter += 1

    match_snapshot1['minute'] = match_snapshot['minute']
    match_snapshot1['event'] = match_snapshot['event']

    p['test_channel'].trigger('my_event',{'message': json.dumps(match_snapshot1)})
    time.sleep(2)
    print json.dumps(match_snapshot1)



def main():
    start_db_connection()


    event = {}
    event['match_id'] = 1
    event['team_id'] = 2
    event['player_id'] = 1
    event['event_time'] = 70
    event['event_type'] = 'freekick'

    # result_record = add_match_event(event)
    # print result_record
    # if result_record['status'] == 0:
    #     print result_record['message']

    # result_record = edit_match_stats(1)
    # print result_record
    # if result_record['status'] == 0:
    #     print result_record['message']

    # result_record = get_match_events(1,1)
    # print result_record
    # if result_record['status'] == 0:
    #     print result_record['message']

    # result_record = get_match_stats(1,1)
    # print result_record
    # if result_record['status'] == 0:
    #     print result_record['message']

    # result_record = get_match_snapshot(1)
    # print result_record
    # if result_record['status'] == 0:
    #     print result_record['message']

    result_record = get_player_by_id(1, 432, 0)
    print result_record
    if result_record['status'] == 0:
        print result_record['message']


# if __name__ == "__main__":
#     main()