(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SUserMySuffixDialogController', SUserMySuffixDialogController);

    SUserMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SUser'];

    function SUserMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SUser) {
        var vm = this;

        vm.sUser = entity;
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
            if (vm.sUser.id !== null) {
                SUser.update(vm.sUser, onSaveSuccess, onSaveError);
            } else {
                SUser.save(vm.sUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:sUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
