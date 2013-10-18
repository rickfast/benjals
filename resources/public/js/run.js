define([
	'angular',
    'angularRoute',
    'angularResource',
    'xeditable',
    'less',
    'sha1',
    'controller/nav',
    'controller/signUp',
    'controller/teams',
    'controller/addTeam',
    'controller/viewTeam',
    'controller/viewUser',
    'controller/addGame',
    'controller/viewGame'],
	function (angular)
	{
        angular.element(document).ready(function()
        {
            angular.bootstrap(document, ['benjals']);
		});
	});

