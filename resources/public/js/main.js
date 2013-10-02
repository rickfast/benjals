less = { async: true, fileAsync: true };

requirejs.config({
    paths: {
        bootstrap: 'libs/bootstrap',
        angular: 'libs/angular',
        angularRoute: 'libs/angular-route',
        angularResource: 'libs/angular-resource',
        selfish: 'libs/selfish',
        less: 'libs/less'
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
        },
        'angularUI': {
            deps: ['angular']
        },
        'uiBootstrap': {
            deps: ['angular']
        }
    }
});

requirejs(['run']);