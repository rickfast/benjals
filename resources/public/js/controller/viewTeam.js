define(['app'], function (app)
{
    app.controller('ViewTeamController', function ViewTeamController($scope, team, players, games)
    {
        $scope.team = team;
        $scope.players = players;
        $scope.games = games;

        for(var i = 0;i < $scope.games.length;i++)
        {
            var game = $scope.games[i];

            game.start_time_date = new Date(game.start_time);
        }
    });
});