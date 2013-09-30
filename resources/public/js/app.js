define(['angular'], function (angular)
{
	var benjals = angular.module('benjals', ['ngResource']);

	benjals.config(['$routeProvider', function($routeProvider)
	{
		$routeProvider.
  			when('/teams', {templateUrl: 'html/partial/teams.html', controller: 'TeamsController'}).
			otherwise({redirectTo: '/teams'});
	}]);

    benjals.run();

	return benjals;
});