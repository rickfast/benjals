define(['angular'], function (angular)
{
	var benjals = angular.module('benjals', ['ngRoute', 'ngResource']);

	benjals.config(['$routeProvider', function($route)
	{
		$route.
  			when('/teams', {templateUrl: 'html/partial/teams.html', controller: 'TeamsController'}).
            when('/teams/add', {templateUrl: 'html/partial/addTeam.html', controller: 'AddTeamController'}).
            when('/teams/:teamId', {templateUrl: 'html/partial/viewTeam.html', controller: 'ViewTeamController'}).
            when('/users/:userId', {templateUrl: 'html/partial/viewUser.html', controller: 'ViewUserController'}).
			otherwise({redirectTo: '/teams'});
	}]);

    benjals.run();

	return benjals;
});