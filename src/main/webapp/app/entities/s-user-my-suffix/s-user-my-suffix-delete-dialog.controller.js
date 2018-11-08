(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SUserMySuffixDeleteController',SUserMySuffixDeleteController);

    SUserMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'SUser'];

    function SUserMySuffixDeleteController($uibModalInstance, entity, SUser) {
        var vm = this;

        vm.sUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
