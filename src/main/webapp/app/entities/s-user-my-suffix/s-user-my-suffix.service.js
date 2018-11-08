(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('SUser', SUser);

    SUser.$inject = ['$resource'];

    function SUser ($resource) {
        var resourceUrl =  'api/s-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
