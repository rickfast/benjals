less = { async: true, fileAsync: true };

requirejs.config({
    paths: {
        angular: 'libs/angular',
        angularRoute: 'libs/angular-route',
        angularResource: 'libs/angular-resource',
        xeditable: 'libs/xeditable',
        less: 'libs/less',
        selfish: 'libs/selfish',
        sha1: 'libs/sha1'
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
        'xeditable': {
            deps: ['angular']
        }
    }
});

requirejs(['run']);