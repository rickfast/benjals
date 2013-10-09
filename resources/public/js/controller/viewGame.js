define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $resource, $routeParams, $http)
    {
        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$routeParams.teamId, gameId:'@id' });

        $scope.attendingDecided = false;

        $scope.refreshGame = function()
        {
            $scope.game = Game.get({ gameId:$routeParams.gameId }, function()
            {
                var game = $scope.game;

                game.start_time_date = new Date(game.start_time);
                $scope.attendingDecided = true;
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

        $scope.attendingSet = function()
        {
            return $scope.game !== null && $scope.game.hasOwnProperty('attending') && $scope.game.attending !== null;
        }

        $scope.attending = function()
        {
            return $scope.game.attending;
        }

        $scope.refreshGame();
    });
});