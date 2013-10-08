define(['app'], function (app)
{
    app.controller('ViewUserController', function ViewUserController($scope, $resource, $routeParams, uiReady)
    {
        var User = $resource('/api/users/:userId', { userId:'@id' });

        $scope.user = User.get({ userId:$routeParams.userId }, function(user)
        {
            $scope.name = user.first ? user.first + ' ' + user.last : "Unknown";
            uiReady.ready();
        });
    });
});