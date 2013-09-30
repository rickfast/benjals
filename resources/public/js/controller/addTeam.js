define(['app'], function (app)
{
    app.controller('AddTeamController', function AddTeamController($scope, $resource, $location)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });

        $scope.teamForm = {name: ""};

        $scope.addTeam = function()
        {
            var newTeam = new Team($scope.teamForm);

            newTeam.$save(function()
            {
                $location.path("/teams");
            });
        };
    });
});