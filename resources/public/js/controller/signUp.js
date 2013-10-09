define(['app'], function (app)
{
    app.controller('SignUpController', function SignUpController($scope, $rootScope, $http, $location)
    {
        $scope.signUpForm = { first:"", last:"", email:""};
        $scope.password = "";
        $scope.passwordConfirmation = "";

        $scope.signUp = function()
        {
            if($scope.password === $scope.passwordConfirmation)
            {
                $scope.signUpForm.password = CryptoJS.SHA1($scope.password).toString(CryptoJS.enc.Base64);

                $http.post('/api/unsecured/user/create', $scope.signUpForm).then(function(result)
                {
                    $rootScope.$broadcast('userUpdated');
                    $location.path("/teams");
                });
            }
        };
    });
});