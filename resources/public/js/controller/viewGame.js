define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $routeParams, $http, user, game, players)
    {
        $scope.user = user;
        $scope.game = game;
        $scope.players = players;
        $scope.attending = null;

        for(var i = 0;i < $scope.players.length;i++)
        {
            var player = $scope.players[i];

            if(player.id === $scope.user.id)
            {
                $scope.attending = player.attending;
                break;
            }
        }

        $scope.setAttending = function(attending)
        {
            $http.post('/api/teams/' + $routeParams.teamId + '/games/' + $routeParams.gameId + '/' + (attending ? 'attending' : 'not-attending'),
                    $scope.loginForm).then(function()
            {
                $scope.attending = attending;
            });
        }
    });
});