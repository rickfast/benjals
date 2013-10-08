define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $resource, $routeParams, $http, uiReady)
    {
        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$routeParams.teamId, gameId:'@id' });

        $scope.refreshGame = function()
        {
            $scope.game = Game.get({ gameId:$routeParams.gameId }, function()
            {
                var game = $scope.game;

                game.start_time_date = new Date(game.start_time);
                uiReady.ready();
            });
        };

        $scope.setAttending = function(attending)
        {
            $http.post('/api/teams/' + $routeParams.teamId + '/games/' + $routeParams.gameId + '/' + (attending ? 'attending' : 'not-attending'),
                    $scope.loginForm).then(function()
            {
                $scope.refreshGame();
            });
        }

        $scope.refreshGame();
    });
});