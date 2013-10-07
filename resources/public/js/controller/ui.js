define(['app'], function (app)
{
    app.controller('UIController', function UIController($scope, uiReady)
    {
        $scope.$on('$routeChangeSuccess', function(next, current)
        {
            $scope.showUI = false;

            uiReady.reset();
            uiReady.whenReady().then(function()
            {
                $scope.showUI = true;
            });
        });
    });
});