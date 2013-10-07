define(['app'], function (app)
{
    app.controller('NavController', function NavController($scope, $location)
    {
        $scope.home = function()
        {
            $location.path("/");
        };

        $scope.signUp = function()
        {
            $location.path("/signUp");
        };
    });
});