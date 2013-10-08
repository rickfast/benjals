define(['app'], function (app)
{
    app.controller('SignUpController', function SignUpController($scope, $http, $location, uiReady)
    {
        uiReady.ready();

        $scope.signUpForm = { first:"", last:"", email:""};
        $scope.password = "";
        $scope.passwordConfirmation = "";

        $scope.signUp = function()
        {
            if($scope.password === $scope.passwordConfirmation)
            {
                $scope.signUpForm.password = CryptoJS.SHA1($scope.signUpForm.password).toString(CryptoJS.enc.Base64);

                $http.post('/api/unsecured/user/create', $scope.signUpForm).then(function(result)
                {
                    $location.path("/teams");
                });
            }
        };
    });
});