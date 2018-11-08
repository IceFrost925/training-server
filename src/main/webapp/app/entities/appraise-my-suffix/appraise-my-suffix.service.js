(function() {
    'use strict';
    angular
        .module('trainingApp')
        .factory('Appraise', Appraise);

    Appraise.$inject = ['$resource'];

    function Appraise ($resource) {
        var resourceUrl =  'api/appraises/:id';

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
