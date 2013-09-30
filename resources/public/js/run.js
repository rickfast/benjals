define([
	'angular',
    'angularResource',
    'less',
    'controller/teams'],
	function (angular)
	{
        //angular.bootstrap(document, ['benjals']);

        angular.element(document).ready(function()
        {
            console.log('app ready');
            angular.bootstrap(document, ['benjals']);
		});
	});

