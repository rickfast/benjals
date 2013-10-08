define(['app'], function (app)
{
    app.controller('NavController', function NavController($scope, $location, $http)
    {
        $scope.loginForm = { email:"" };
        $scope.loginPassword = "";
        $scope.loginDecided = false;
        $scope.user = null;

        $scope.checkCurrentUser = function()
        {
            $http.get('/api/users/current').then(function(result)
            {
                $scope.user = result.data;
                $scope.loginForm = { email:"" };
                $scope.loginPassword = "";
                $scope.loginDecided = true;
            }, function(error)
            {
                $scope.loginDecided = true;
            });
        };

        $scope.$on('userUpdated', function()
        {
            $scope.checkCurrentUser()
        });

        $scope.login = function()
        {
            $scope.loginForm.password = CryptoJS.SHA1($scope.loginPassword).toString(CryptoJS.enc.Base64);

            $http.post('/api/unsecured/user/login', $scope.loginForm).then(function(result)
            {
                $scope.user = result.data;
                $location.path("/teams");
            });
        };

        $scope.logout = function()
        {
            $http.get('/api/unsecured/user/logout').then(function(result)
            {
                $scope.user = null;
                $location.path("/signUp");
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

        $scope.checkCurrentUser();
    });
});