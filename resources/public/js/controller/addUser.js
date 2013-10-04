define(['app'], function (app)
{
    app.controller('AddUserController', function AddUserController($scope, $resource, $location)
    {
        var User = $resource('/users/:userId', { teamId:'@id' });

        $scope.userForm = { first:"", last:"", email:"" };

        $scope.addUser = function()
        {
            var newUser = new User($scope.userForm);

            newUser.$save(function()
            {
                $location.path("/teams");
            });
        };
    });
});