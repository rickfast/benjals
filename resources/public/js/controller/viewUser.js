define(['app'], function (app)
{
    app.controller('ViewUserController', function ViewUserController($scope, user)
    {
        $scope.user = user;
        $scope.name = user.first ? user.first + ' ' + user.last : "Unknown";
    });
});