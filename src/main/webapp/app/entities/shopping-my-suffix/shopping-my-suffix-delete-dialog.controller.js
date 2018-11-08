(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ShoppingMySuffixDeleteController',ShoppingMySuffixDeleteController);

    ShoppingMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Shopping'];

    function ShoppingMySuffixDeleteController($uibModalInstance, entity, Shopping) {
        var vm = this;

        vm.shopping = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Shopping.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
