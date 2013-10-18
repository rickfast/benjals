define(['app'], function (app)
{
    app.controller('ViewTeamController', function ViewTeamController($scope, $resource, team, players, games)
    {
        var User = $resource('/api/users/:email', { email:'@email' });

        $scope.team = team;
        $scope.players = players;
        $scope.games = games;
        $scope.updatedPlayers = [];
        $scope.updatedGames = [];
        $scope.matchedEmails = [];
        $scope.watches = [];

        for(var i = 0;i < $scope.games.length;i++)
        {
            var game = $scope.games[i];

            game.start_time_date = new Date(game.start_time);
        }

        $scope.startEditing = function()
        {
            $scope.updatedGames = $scope.games.slice(0);
            $scope.updatedPlayers = [];
            $scope.matchedEmails = [];
            $scope.watches = [];

            for(var i = 0;i < $scope.players.length;i++)
            {
                $scope.addPlayer($scope.players[i]);
            }
        }

        $scope.addPlayer = function(player)
        {
            var playerDefined = player !== undefined;
            var newPlayer = playerDefined ? player : { email:"" };
            var lastIndex = $scope.updatedPlayers.length;

            $scope.matchedEmails.push(playerDefined);
            $scope.updatedPlayers.push(newPlayer);

            if(!playerDefined)
            {
                $scope.addWatch(lastIndex, newPlayer);
            }
        };

        $scope.removePlayer = function(index)
        {
            for(var i = 0;i < $scope.watches.length;i++)
            {
                $scope.watches[i]();
            }

            $scope.watches = [];
            $scope.matchedEmails.splice(index, 1);
            $scope.updatedPlayers.splice(index, 1);

            for(var i = 0;i < $scope.updatedPlayers.length;i++)
            {
                if(!$scope.existingPlayer(i))
                {
                    $scope.addWatch(i, $scope.updatedPlayers[i]);
                }
            }
        }

        $scope.addWatch = function(index, player)
        {
            var watch = $scope.$watch('updatedPlayers[' + index + '].email', function()
            {
                User.get({ email:player.email }, function(user)
                {
                    $scope.matchedEmails[index] = true;
                }, function(response)
                {
                    $scope.matchedEmails[index] = false;
                });
            });

            $scope.watches.push(watch);
        }

        $scope.emailMatched = function(index)
        {
            return $scope.matchedEmails[index];
        };

        $scope.lastPlayer = function(index)
        {
            return index == $scope.updatedPlayers.length - 1;
        };

        $scope.alternate = function(index)
        {
            return index >= $scope.team.required_head_count;
        }

        $scope.existingPlayer = function(index)
        {
            return $scope.updatedPlayers[index].hasOwnProperty('id');
        }

        $scope.removeGame = function(index)
        {
            $scope.updatedGames.splice(index, 1);
        }

        $scope.saveEdits = function()
        {
            console.log('save edits');
        }
    });
});