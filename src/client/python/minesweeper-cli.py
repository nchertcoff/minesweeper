import requests
import json

class GameClient:
    def __init__(self, host):
        self._host = host

    def get_games(self, username):
        resp = requests.get('http://' + self._host + '/games?username=' + username)
        # print(resp.content)
        resp_dict = json.loads(resp.content)
        return resp_dict

    def create(self, username, rows, cols, bombs):
        data = {
            'username': username,
            'rows': rows,
            'cols': cols,
            'bombs': bombs
        }
        headers = {
            'content-type': 'application/json'
        }
        resp = requests.post('http://' + self._host + '/games', data=json.dumps(data), headers=headers)
        resp_dict = json.loads(resp.content)
        return resp_dict

    def visit_cell(self, game_id, row, col):
        data = {
            'row': row,
            'col': col,
        }
        headers = {
            'content-type': 'application/json'
        }
        resp = requests.post('http://' + self._host + '/games/{game_id}/visited-cells'.format(game_id=game_id),
                             data=json.dumps(data), headers=headers)
        resp_dict = json.loads(resp.content)
        return resp_dict

    def flag_cell(self, game_id, row, col, flagged, question_mark=False):
        data = {
            'row': row,
            'col': col,
            'flagged': flagged,
            'questionMarked': question_mark,
        }
        headers = {
            'content-type': 'application/json'
        }
        resp = requests.put('http://' + self._host + '/games/{game_id}/cells'.format(game_id=game_id),
                             data=json.dumps(data), headers=headers)
        resp_dict = json.loads(resp.content)
        return resp_dict



# client = Game(host="afternoon-dawn-33480.herokuapp.com")
client = GameClient(host="localhost:8080")

print("Listing games for user nchert")
games = client.get_games(username="nchert")
for i in games:
    print("     Game id = {id}".format(id=i.get('id')))
print()

print("Create new game for user nchert")
game = client.create(username="nchert", cols=10, rows=10, bombs=10)
print("     Game id = {id}, status = {status}".format(id=game['id'], status=game['status']))
print()

print("Visit cel 3,3 on the newly created game")
visited_cells = client.visit_cell(game_id=game['id'], row=3, col=3)
print("Listing discovered cells (could be 1 or many depending if there is a mine in the adjacent squares)")
for cell in visited_cells:
    print(cell)
print()

print("Mark cel 2,0 as flagged")
cell = client.flag_cell(game_id=game['id'], row=2, col=0, flagged=True)
print(cell)


