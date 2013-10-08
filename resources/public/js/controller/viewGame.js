define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $resource, $routeParams, uiReady)
    {
        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$routeParams.teamId, gameId:'@id' });

        $scope.game = Game.get({ gameId:$routeParams.gameId }, function()
        {
            var game = $scope.game;

            game.start_time_date = new Date(game.start_time);
            uiReady.ready();
        });
    });
});