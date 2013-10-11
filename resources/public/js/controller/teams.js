define(['app'], function (app)
{
    app.controller('TeamsController', function TeamsController($scope, teams)
    {
        $scope.teams = teams;
    });
});