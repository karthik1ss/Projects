
var pusher = new Pusher('4020e68dc8ff6cdc72f9');
var channel = pusher.subscribe('test_channel');
channel.bind('my_event', function(data_json) {
	var data = data_json.message;
	processData(data);
});


var data1 = '{"team1": {"players": { "player1" : {"position" : "defender", "yellow_cards": "0","red_cards": "0","pname": "Bradley Guzan","goals": "0"}, "player2" : {"position" : "forward" ,"yellow_cards": "0","red_cards": "0","pname": "Sancho Sebastine","goals": "3"}},"stats": {"tname": "MAN UTD","yellow_cards": "3",      "free_kicks": "3",      "corners": "3",      "penalties": "3",      "fouls": "3",      "shots_off_target": "3",      "goals": "3",      "shots_on_target": "3",      "red_cards": "3",      "offsides": "2"    }  },  "team2": {    "players": {      "player1": {    "position": "defender",    "yellow_cards": "1",        "red_cards": "0",        "pname": "Rooney",        "goals": "1"      },      "player2": {   "position": "forward",     "yellow_cards": "1",        "red_cards": "0",        "pname": "Rooney",        "goals": "1"      }    },    "stats": {      "tname": "ASTON VILLA",      "yellow_cards": "3",      "free_kicks": "3",      "corners": "3",      "penalties": "3",      "fouls": "3",      "shots_off_target": "3",      "goals": "3",      "shots_on_target": "3",      "red_cards": "3",      "offsides": "2"    }  },  "minute": "0"}';

function processData(data) {
	var json = JSON.parse(data);
	$('#minute').html(json.minute+'\'');
	$('#team1name').html(json.team1.stats.tname);
	$('#team2name').html(json.team2.stats.tname);
	$('#team1goals').html(json.team1.stats.goals);
	$('#team2goals').html(json.team2.stats.goals);
	$('#eventType').html(json.event);
	var team1Players = json.team1.players;
	var team2Players = json.team2.players;
	
	setupTeams('team1', team1Players);
	setupTeams('team2', team2Players);
}

function setupTeams(divName, teamPlayers) {

	$('.'+divName+' .forward .alignment').html('');
	$('.'+divName+' .midfielder .alignment').html('');
	$('.'+divName+' .defender .alignment').html('');
	$('.'+divName+' .goalie .alignment').html('');
	
	//key player1,...,value player1 redcards and so on ..
	$.each(teamPlayers, function(key, value){

	  console.log(key, value);
	  var position = value.position;
	  var positionDivName = '.'+divName+' .' + position+' .alignment';
	  var playerDivName = positionDivName + ' .' + key ;
	  
	  var divTeamPlayer = divName;
	  console.log(divTeamPlayer);
	  if ( value.hasball == 1 ) {
			divTeamPlayer = 'hasball';
			console.log(divTeamPlayer);
		} else {
			console.log(divTeamPlayer);
		}

	  $(positionDivName).append(getHtmlForPlayer(key,divTeamPlayer));
	  $(playerDivName +' .header').html(value.pname);
	  $(playerDivName +' .playercontent .goals').html('Goals: ' + value.goals);
	  $(playerDivName +' .playercontent .redcard').html('Red Cards: ' + value.red_cards);
	  $(playerDivName +' .playercontent .yellowcard').html('Yellow Cards: ' + value.yellow_cards);
	});
}


function getHtmlForPlayer(playerId,divTeamPlayer) {
	var html = '<div class="' + playerId + ' player'+divTeamPlayer+'"><div class="header"></div><div class="playercontent"><div class="goals"></div><div class="redcard"></div><div class="yellowcard"></div></div></div>';
	return html;
}