(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('Shopping', Shopping);

    Shopping.$inject = ['$resource'];

    function Shopping ($resource) {
        var resourceUrl =  'api/shoppings/:id';

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
