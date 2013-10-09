define(['app'], function (app)
{
    app.controller('ViewTeamController', function ViewTeamController($scope, $resource, $routeParams)
    {
        var teamId = $routeParams.teamId;
        var Team = $resource('/api/teams/:teamId/:action', { teamId:teamId, action:'@action' });
        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:teamId, gameId:'@id' });

        $scope.showUI = false;

        $scope.team = Team.get(function()
        {
            $scope.showUI = true;
        });

        $scope.players = Team.query({ action:"players" });

        $scope.games = Game.query(function()
        {
            for(var i = 0;i < $scope.games.length;i++)
            {
                var game = $scope.games[i];

                game.start_time_date = new Date(game.start_time);
            }
        });
    });
});