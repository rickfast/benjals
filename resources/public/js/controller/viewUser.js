define(['app'], function (app)
{
    app.controller('ViewUserController', function ViewUserController($scope, $resource, $routeParams)
    {
        var User = $resource('/users/:userId', { userId:'@id' });

        $scope.user = User.get({ userId:$routeParams.userId }, function(user)
        {
            $scope.name = user.first ? user.first + ' ' + user.last : "Unknown";
        });
    });
});