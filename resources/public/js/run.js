define([
	'angular',
    'angularRoute',
    'angularResource',
    'less',
    'controller/teams',
    'controller/addTeam',
    'controller/viewTeam',
    'controller/viewUser'],
	function (angular)
	{
        angular.element(document).ready(function()
        {
            angular.bootstrap(document, ['benjals']);
		});
	});

