(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AppraiseMySuffixDialogController', AppraiseMySuffixDialogController);

    AppraiseMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Appraise', 'SUser', 'Books'];

    function AppraiseMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Appraise, SUser, Books) {
        var vm = this;

        vm.appraise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.susers = SUser.query();
        vm.books = Books.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.appraise.id !== null) {
                Appraise.update(vm.appraise, onSaveSuccess, onSaveError);
            } else {
                Appraise.save(vm.appraise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:appraiseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
