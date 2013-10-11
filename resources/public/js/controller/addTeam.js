define(['app'], function (app)
{
    app.controller('AddTeamController', function AddTeamController($scope, $resource, $http, $location, user)
    {
        var Team = $resource('/api/teams/:teamId', { teamId:'@id' });
        var User = $resource('/api/users/:email', { email:'@email' });

        $scope.user = user;
        $scope.teamForm = { name:"", registration_deadline: 12, required_head_count:5 };
        $scope.players = [];
        $scope.matchedEmails = [];
        $scope.watches = [];

        $scope.$watch('teamForm.required_head_count', function()
        {
            $scope.updatePlayers();
        });

        $scope.addTeam = function()
        {
            $scope.teamForm.players = [];

            for(var i = 0;i < $scope.players.length;i++)
            {
                var player = $scope.players[i];

                if(player.email)
                {
                    player.alternate = i > $scope.teamForm.required_head_count - 1;
                    $scope.teamForm.players.push(player);
                }
            }

            var newTeam = new Team($scope.teamForm);

            newTeam.$save(function()
            {
                $location.path("/teams");
            });
        };

        $scope.updatePlayers = function()
        {
            if($scope.user !== null)
            {
                while($scope.teamForm.required_head_count > $scope.players.length)
                {
                    $scope.addPlayer();
                }
            }
        };

        $scope.addPlayer = function(email)
        {
            var newPlayer = { email:email };
            var lastIndex = $scope.players.length;

            $scope.matchedEmails.push(false);
            $scope.players.push(newPlayer);
            $scope.addWatch(lastIndex, newPlayer);
        };

        $scope.removePlayer = function(index)
        {
            for(var i = 0;i < $scope.watches.length;i++)
            {
                $scope.watches[i]();
            }

            $scope.watches = [];
            $scope.matchedEmails.splice(index, 1);
            $scope.players.splice(index, 1);

            for(var i = 0;i < $scope.players.length;i++)
            {
                $scope.addWatch(i, $scope.players[i]);
            }
        }

        $scope.addWatch = function(index, player)
        {
            var watch = $scope.$watch('players[' + index + '].email', function()
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

        $scope.lastPlayer = function(index)
        {
            return index == $scope.players.length - 1;
        };

        $scope.emailMatched = function(index)
        {
            return $scope.matchedEmails[index];
        };

        $scope.alternate = function(index)
        {
            return index >= $scope.teamForm.required_head_count;
        }

        //Initialize.

        $scope.addPlayer($scope.user.email);
        $scope.updatePlayers();
    });
});