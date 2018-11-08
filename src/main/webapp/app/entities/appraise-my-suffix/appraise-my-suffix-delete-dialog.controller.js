(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AppraiseMySuffixDeleteController',AppraiseMySuffixDeleteController);

    AppraiseMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Appraise'];

    function AppraiseMySuffixDeleteController($uibModalInstance, entity, Appraise) {
        var vm = this;

        vm.appraise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Appraise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
