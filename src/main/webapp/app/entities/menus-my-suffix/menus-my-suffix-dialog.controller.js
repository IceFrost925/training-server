(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('MenusMySuffixDialogController', MenusMySuffixDialogController);

    MenusMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Menus'];

    function MenusMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Menus) {
        var vm = this;

        vm.menus = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.menus.id !== null) {
                Menus.update(vm.menus, onSaveSuccess, onSaveError);
            } else {
                Menus.save(vm.menus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:menusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
