(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CollectMySuffixDeleteController',CollectMySuffixDeleteController);

    CollectMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Collect'];

    function CollectMySuffixDeleteController($uibModalInstance, entity, Collect) {
        var vm = this;

        vm.collect = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Collect.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
