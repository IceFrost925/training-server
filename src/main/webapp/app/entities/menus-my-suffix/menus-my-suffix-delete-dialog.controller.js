(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('MenusMySuffixDeleteController',MenusMySuffixDeleteController);

    MenusMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Menus'];

    function MenusMySuffixDeleteController($uibModalInstance, entity, Menus) {
        var vm = this;

        vm.menus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Menus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
