define(['app'], function (app)
{
    app.controller('AddTeamController', function AddTeamController($scope, $resource, $location)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });
        var User = $resource('/users/:email', { email:'@email' });

        $scope.teamForm = { name:"", players:[] };
        $scope.matchedEmails = [];

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

            $scope.matchedEmails.push[false];
            $scope.teamForm.players.push(newPlayer);
            $scope.$watch('teamForm.players[' + lastIndex + '].email', function()
            {
                User.get({ email:newPlayer.email }, function(user)
                {
                    $scope.matchedEmails[lastIndex] = true;
                }, function(response)
                {
                    $scope.matchedEmails[lastIndex] = false;
                });
            });
        };

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