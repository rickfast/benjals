define(['app'], function (app)
{
    app.controller('NavController', function NavController($scope, $location, $http)
    {
        $scope.loginForm = { email:"", password:"" };
        $scope.loginDecided = false;
        $scope.user = null;

        $http.get('/api/users/current').then(function(result)
        {
            $scope.user = result.data;
            $scope.loginDecided = true;
        }, function(error)
        {
            $scope.loginDecided = true;
        });

        $scope.login = function()
        {
            $http.post('/api/login', $scope.loginForm).then(function(result)
            {
                $scope.user = result.data;
                $location.path("/teams");
            });
        };

        $scope.isLoggedIn = function()
        {
            return $scope.user !== null;
        }

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