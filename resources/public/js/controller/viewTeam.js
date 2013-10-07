define(['app'], function (app)
{
    app.controller('ViewTeamController', function ViewTeamController($scope, $resource, $routeParams, uiReady)
    {
        var Team = $resource('/teams/:teamId', { teamId:'@id' });

        $scope.team = Team.get({ teamId:$routeParams.teamId }, function()
        {
            uiReady.ready();
        });
    });
});