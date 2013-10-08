define(['angular'], function (angular)
{
	var benjals = angular.module('benjals', ['ngRoute', 'ngResource']);

	benjals.config(function($routeProvider, $provide, $httpProvider)
	{
		$routeProvider.
            when('/signUp', {templateUrl: 'html/partial/signUp.html', controller: 'SignUpController'}).
  			when('/teams', {templateUrl: 'html/partial/teams.html', controller: 'TeamsController'}).
            when('/teams/add', {templateUrl: 'html/partial/addTeam.html', controller: 'AddTeamController'}).
            when('/teams/:teamId', {templateUrl: 'html/partial/viewTeam.html', controller: 'ViewTeamController'}).
            when('/teams/:teamId/games', {templateUrl: 'html/partial/games.html', controller: 'GamesController'}).
            when('/teams/:teamId/games/add', {templateUrl: 'html/partial/addGame.html', controller: 'AddGameController'}).
            when('/teams/:teamId/games/:gameId', {templateUrl: 'html/partial/viewGame.html', controller: 'ViewGameController'}).
            when('/users/:userId', {templateUrl: 'html/partial/viewUser.html', controller: 'ViewUserController'}).
			otherwise({redirectTo: '/teams'});

        $provide.factory('authInterceptor', function($q, $location)
        {
            return {
                'responseError': function(rejection)
                {
                    if(rejection.status === 403)
                    {
                        $location.path("/signUp");
                    }

                    return $q.reject(rejection);
                }
            };
        });

        $httpProvider.interceptors.push('authInterceptor');
	});

    benjals.run();

	return benjals;
});