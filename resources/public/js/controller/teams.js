define(['app'], function (app)
{
    app.controller('TeamsController', function TeamsController($scope, $resource, uiReady)
    {
        var Team = $resource('/api/teams/:teamId', { teamId:'@id' });

        $scope.teams = Team.query(function()
        {
            uiReady.ready();
        });
    });
});