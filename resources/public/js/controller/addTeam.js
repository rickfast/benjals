define(['app'], function (app)
{
    app.controller('AddTeamController', function AddTeamController($scope, $resource, $location)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });

        $scope.teamForm = {name: "", players: [{ email:"" }]};

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
            $scope.teamForm.players.push({ email:"" });
        };

        $scope.lastPlayer = function(index)
        {
            return index == $scope.teamForm.players.length - 1;
        }
    });
});