(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersMySuffixDetailController', OrdersMySuffixDetailController);

    OrdersMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'SUser', 'Shopping'];

    function OrdersMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, SUser, Shopping) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
