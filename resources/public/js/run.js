define([
	'angular',
    'angularRoute',
    'angularResource',
    'less',
    'controller/teams',
    'controller/addTeam',
    'controller/viewTeam',
    'controller/addUser',
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

