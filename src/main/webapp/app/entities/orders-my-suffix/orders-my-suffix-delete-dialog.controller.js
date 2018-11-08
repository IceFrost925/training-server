(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersMySuffixDeleteController',OrdersMySuffixDeleteController);

    OrdersMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Orders'];

    function OrdersMySuffixDeleteController($uibModalInstance, entity, Orders) {
        var vm = this;

        vm.orders = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Orders.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
