define([
	'angular',
    'angularResource',
    'less',
    'controller/teams'],
	function (angular)
	{
        angular.element(document).ready(function()
        {
            console.log('app ready');
			angular.bootstrap(document, ['benjals']);
		});
	});

