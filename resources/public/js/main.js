less = { async: true, fileAsync: true };

requirejs.config({
    paths: {
        bootstrap: 'libs/bootstrap',
        angular: 'libs/angular',
        angularResource: 'libs/angular-resource',
        selfish: 'libs/selfish',
        less: 'libs/less'
    },
    shim: {
        'angular': {
            exports: 'angular'
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