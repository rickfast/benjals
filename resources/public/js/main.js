less = { async: true, fileAsync: true };

requirejs.config({
    paths: {
        angular: 'libs/angular',
        angularRoute: 'libs/angular-route',
        angularResource: 'libs/angular-resource',
        less: 'libs/less',
        selfish: 'libs/selfish'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angularRoute': {
            deps: ['angular']
        },
        'angularResource': {
            deps: ['angular']
        }
    }
});

requirejs(['run']);