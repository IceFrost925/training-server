(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('Collect', Collect);

    Collect.$inject = ['$resource'];

    function Collect ($resource) {
        var resourceUrl =  'api/collects/:id';

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
