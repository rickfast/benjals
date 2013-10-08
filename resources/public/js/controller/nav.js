define(['app'], function (app)
{
    app.controller('NavController', function NavController($scope, $location, $http)
    {
        $scope.loginForm = { email:"", password:"" };

        $scope.login = function()
        {
            $http.post('/login', $scope.loginForm).success($scope.onLoginSuccessful).error($scope.onLoginFailed);
        };

        $scope.home = function()
        {
            $location.path("/");
        };

        $scope.signUp = function()
        {
            $location.path("/signUp");
        };

        $scope.onLoginSuccessful = function()
        {
            $location.path("/teams");
        }

        $scope.onLoginFailed = function()
        {
            console.log("login failed.");
        };
    });
});