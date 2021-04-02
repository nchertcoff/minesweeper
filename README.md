# Notes and decisions tt 

The app is develop using Java, Spring boot, JPA and connects to a Postresql database

The client library is develop in Python 

# REST API minesweeper api 

Examples of the rest api

## Get list of Games per user

### Request

`GET /games`

    curl -i -H 'Accept: application/json' http://localhost:8080/games?username=nchert

### Response

    [{"id":1,"username":"nchert","rows":10,"cols":10,"bombs":10,"status":null,"visitedCells":[]}]

## Create a Game

### Request

`POST /games`

    curl -i -X POST -H "Content-Type:application/json"  -d '{ "username": "nchert", "cols": 10, "rows": 10, "bombs": 10 }' http://localhost:8080/games

### Response

    {"id":102,"username":"nchert","rows":10,"cols":10,"bombs":10,"status":"IN_PROGRESS","visitedCells":[]}

## Get a Game

### Request

`GET /games/1`

    curl -i -H 'Accept: application/json' http://localhost:8080/games/1

### Response

    {"id":1,"username":"nchert","rows":10,"cols":10,"bombs":10,"status":null,"visitedCells":[]}
    
The visitedCells list contains all the cells already discovered. You can only see the bombsArround attribute for visited cells. 

## Visit a Cell

### Request

`POST /games/id/visited-cells`

    curl -i -X POST -H "Content-Type:application/json"  -d '{ "row": 0, "col": 0 }' http://localhost:8080/games/1/visited-cells

### Response

    {"id":102,"username":"nchert","rows":10,"cols":10,"bombs":10,"status":"IN_PROGRESS","visitedCells":[]}

## Flag a Cell / Set question mark to a Cell

### Request

`PUT /games/id/cells`

    curl -i -X PUT -H "Content-Type:application/json"  -d '{ "row": 3, "col": 6, "flagged": true, "questionMarked": false }' http://localhost:8080/games/1/cells

### Response

    {"id":102,"username":"nchert","rows":10,"cols":10,"bombs":10,"status":"IN_PROGRESS","visitedCells":[{"id":745,"row":3,"col":6,"bombsArround":2,"flagged":true,"visited":false,"questionMarked":false,"bomb":true}]}

