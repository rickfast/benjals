define(['app'], function (app)
{
    app.controller('ViewGameController', function ViewGameController($scope, $routeParams, $http, user, team, game, players)
    {
        $scope.user = user;
        $scope.team = team;
        $scope.game = game;
        $scope.players = players;
        $scope.attending = null;
        $scope.alternate = false;
        $scope.regCount = 0;
        $scope.deadline = new Date($scope.game.start_time_date.getTime() -
            ($scope.team.registration_deadline * (60 * 60 * 1000)));

        for(var i = 0;i < $scope.players.length;i++)
        {
            var player = $scope.players[i];

            $scope.regCount += player.attending ? 1 : 0;

            if(player.id === $scope.user.id)
            {
                $scope.attending = player.attending;
                $scope.alternate = player.alternate;
            }
        }

        $scope.setAttending = function(attending)
        {
            $http.post('/api/teams/' + $routeParams.teamId + '/games/' + $routeParams.gameId + '/' + (attending ? 'attending' : 'not-attending'),
                    $scope.loginForm).then(function()
            {
                $scope.attending = attending;

                for(var i = 0;i < $scope.players.length;i++)
                {
                    var player = $scope.players[i];

                    if(player.id === $scope.user.id)
                    {
                        player.attending = $scope.attending;
                        break;
                    }
                }
            });
        };

        $scope.headCountReached = function()
        {
            return $scope.regCount == $scope.team.required_head_count;
        };

        $scope.canRegister = function()
        {
             return !$scope.alternate || new Date().getTime() > $scope.deadline.getTime();
        };
    });
});