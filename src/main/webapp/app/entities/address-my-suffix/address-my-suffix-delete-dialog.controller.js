(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AddressMySuffixDeleteController',AddressMySuffixDeleteController);

    AddressMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Address'];

    function AddressMySuffixDeleteController($uibModalInstance, entity, Address) {
        var vm = this;

        vm.address = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
