define(['app'], function (app)
{
    app.controller('SignUpController', function SignUpController($scope, $resource, $location, uiReady)
    {
        uiReady.ready();

        var User = $resource('/users/:userId', { teamId:'@id' });

        $scope.signUpForm = { first:"", last:"", email:"" };

        $scope.signUp = function()
        {
            var newUser = new User($scope.signUpForm);

            newUser.$save(function()
            {
                $location.path("/teams");
            });
        };
    });
});