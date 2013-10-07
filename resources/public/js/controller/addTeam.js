define(['app'], function (app)
{
    app.controller('AddTeamController', function AddTeamController($scope, $resource, $location)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });
        var User = $resource('/users/:email', { email:'@email' });

        $scope.teamForm = { name:"", players:[] };
        $scope.matchedEmails = [];
        $scope.watches = [];

        $scope.addTeam = function()
        {
            var newTeam = new Team($scope.teamForm);

            newTeam.$save(function()
            {
                $location.path("/teams");
            });
        };

        $scope.addPlayer = function()
        {
            var newPlayer = { email:"" };
            var lastIndex = $scope.teamForm.players.length;

            $scope.matchedEmails.push(false);
            $scope.teamForm.players.push(newPlayer);
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
            $scope.teamForm.players.splice(index, 1);

            for(var i = 0;i < $scope.teamForm.players.length;i++)
            {
                $scope.addWatch(i, $scope.teamForm.players[i]);
            }
        }

        $scope.addWatch = function(index, player)
        {
            var watch = $scope.$watch('teamForm.players[' + index + '].email', function()
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
            return index == $scope.teamForm.players.length - 1;
        };

        $scope.emailMatched = function(index)
        {
            return $scope.matchedEmails[index];
        };

        $scope.addPlayer();
    });
});