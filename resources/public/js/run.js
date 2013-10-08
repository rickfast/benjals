define([
	'angular',
    'angularRoute',
    'angularResource',
    'less',
    'sha1',
    'service/uiReady',
    'controller/nav',
    'controller/ui',
    'controller/signUp',
    'controller/teams',
    'controller/addTeam',
    'controller/viewTeam',
    'controller/viewUser',
    'controller/games',
    'controller/addGame',
    'controller/viewGame'],
	function (angular)
	{
        angular.element(document).ready(function()
        {
            angular.bootstrap(document, ['benjals']);
		});
	});

