define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $routeParams, $http, game)
    {
        $scope.game = game;

        $scope.setAttending = function(attending)
        {
            $http.post('/api/teams/' + $routeParams.teamId + '/games/' + $routeParams.gameId + '/' + (attending ? 'attending' : 'not-attending'),
                    $scope.loginForm).then(function()
            {
                $scope.game.attending = attending;
            });
        }
    });
});