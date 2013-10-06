define(['app'], function (app)
{
    app.controller('TeamsController', function TeamsController($scope, $resource)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });

        $scope.teams = Team.query();
    });
});