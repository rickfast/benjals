define(['selfish', 'app'], function (selfish, app)
{
    app.factory('uiReady', function($q)
    {
        var UIReadyService = selfish.Base.extend(
        {
            initialize: function()
            {
                this.readyDeferred = $q.defer();
            },

            reset: function()
            {
                this.readyDeferred = $q.defer();
            },

            whenReady: function()
            {
                return this.readyDeferred.promise;
            },

            ready: function()
            {
                this.readyDeferred.resolve();
            }
        });

        return UIReadyService.new();
    });
});