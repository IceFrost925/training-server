(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CollectMySuffixDetailController', CollectMySuffixDetailController);

    CollectMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Collect', 'SUser', 'Books'];

    function CollectMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Collect, SUser, Books) {
        var vm = this;

        vm.collect = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:collectUpdate', function(event, result) {
            vm.collect = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
