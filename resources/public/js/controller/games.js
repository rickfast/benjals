define(['app'], function (app)
{
    app.controller('GamesController', function GamesController($scope, $resource, $routeParams, uiReady)
    {
        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$routeParams.teamId, gameId:'@id' });

        $scope.teamId = $routeParams.teamId;
        $scope.games = Game.query(function()
        {
            for(var i = 0;i < $scope.games.length;i++)
            {
                var game = $scope.games[i];

                game.start_time_date = new Date(game.start_time);
            }

            uiReady.ready();
        });
    });
});