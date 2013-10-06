define(['app'], function (app)
{
    app.controller('AddGameController', function AddGameController($scope, $resource, $routeParams, $location, $timeout)
    {
        var Game = $resource('/teams/:teamId/games/:gameId', { teamId:$routeParams.teamId, gameId:'@id' });

        $scope.startTime = null;
        $scope.gameForm = { start_time:null };

        $scope.initializeTime = function()
        {
            var now = new Date();

            now.setHours(now.getHours() + 1);
            now.setMinutes(0);
            now.setSeconds(0);
            now.setMilliseconds(0);
            $scope.startTime = now;
        };

        $scope.subtractTime = function()
        {
            var startTime = $scope.startTime;

            startTime.setTime(startTime.getTime() - 1800000);
        };

        $scope.addTime = function()
        {
            var startTime = $scope.startTime;

            startTime.setTime(startTime.getTime() + 1800000);
        };

        $scope.addGame = function()
        {
            $scope.gameForm.start_time = $scope.startTime.getTime() / 1000;

            var newGame = new Game($scope.gameForm);

            newGame.$save(function()
            {
                $location.path("/teams/" + $routeParams.teamId + "/games");
            });
        };

        $scope.initializeTime();
    });
});