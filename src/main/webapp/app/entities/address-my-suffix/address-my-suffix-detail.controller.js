(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AddressMySuffixDetailController', AddressMySuffixDetailController);

    AddressMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Address', 'SUser'];

    function AddressMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Address, SUser) {
        var vm = this;

        vm.address = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:addressUpdate', function(event, result) {
            vm.address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
