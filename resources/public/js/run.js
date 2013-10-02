define([
	'angular',
    'angularRoute',
    'angularResource',
    'less',
    'controller/teams',
    'controller/addTeam'],
	function (angular)
	{
        angular.element(document).ready(function()
        {
            angular.bootstrap(document, ['benjals']);
		});
	});

