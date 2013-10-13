define(['angular'], function (angular)
{
	var benjals = angular.module('benjals', ['ngRoute', 'ngResource']);

	benjals.config(function($routeProvider, $provide, $httpProvider)
	{
		$routeProvider.
            when('/signUp', {templateUrl: 'html/partial/signUp.html', controller: 'SignUpController'}).
  			when('/teams',
            {
                templateUrl: 'html/partial/teams.html',
                controller: 'TeamsController',
                resolve: {
                    'teams': function($resource)
                    {
                        var Team = $resource('/api/teams/:teamId', { teamId:'@id' });
                        var teams = Team.query();

                        return teams.$promise;
                    }
                }
            }).
            when('/teams/add',
            {
                templateUrl: 'html/partial/addTeam.html',
                controller: 'AddTeamController',
                resolve: {
                    'user': function($http)
                    {
                        return $http.get('/api/users/current').then(function(result)
                        {
                            return result.data;
                        });
                    }
                }
            }).
            when('/teams/:teamId',
            {
                templateUrl: 'html/partial/viewTeam.html',
                controller: 'ViewTeamController',
                resolve: {
                    'team': function($resource, $route)
                    {
                        var Team = $resource('/api/teams/:teamId', { teamId:$route.current.params.teamId });
                        var team = Team.get();

                        return team.$promise;
                    },
                    'players': function($resource, $route)
                    {
                        var Team = $resource('/api/teams/:teamId/:action', { teamId:$route.current.params.teamId, action:'@action' });
                        var players = Team.query({ action:"players" });

                        return players.$promise;
                    },
                    'games': function($resource, $route)
                    {
                        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$route.current.params.teamId, gameId:'@id' });
                        var games = Game.query();

                        return games.$promise;
                    }
                }
            }).
            when('/teams/:teamId/games/add', {templateUrl: 'html/partial/addGame.html', controller: 'AddGameController'}).
            when('/teams/:teamId/games/:gameId',
            {
                templateUrl: 'html/partial/viewGame.html',
                controller: 'ViewGameController',
                resolve: {
                    'user': function($http)
                    {
                        return $http.get('/api/users/current').then(function(result)
                        {
                            return result.data;
                        });
                    },
                    'game': function($resource, $route)
                    {
                        var Game = $resource('/api/teams/:teamId/games/:gameId', { teamId:$route.current.params.teamId, gameId:'@id' });
                        var game = Game.get({ gameId:$route.current.params.gameId });

                        return game.$promise.then(function(game)
                        {
                            game.start_time_date = new Date(game.start_time);

                            return game;
                        });
                    },
                    'players': function($resource, $route)
                    {
                        var params = $route.current.params;
                        var Game = $resource('/api/teams/:teamId/games/:gameId/:action', { teamId:params.teamId, gameId:params.gameId, action:'@action' });
                        var players = Game.query({ action:"players" });

                        return players.$promise;
                    }
                }
            }).
            when('/users/:userId',
            {
                templateUrl: 'html/partial/viewUser.html',
                controller: 'ViewUserController',
                resolve: {
                    'user': function($resource, $route)
                    {
                        var User = $resource('/api/users/:userId', { userId:'@id' });
                        var user = User.get({ userId:$route.current.params.userId });

                        return user.$promise;
                    }
                }
            }).
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