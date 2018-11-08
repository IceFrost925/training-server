(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CollectMySuffixDialogController', CollectMySuffixDialogController);

    CollectMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Collect', 'SUser', 'Books'];

    function CollectMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Collect, SUser, Books) {
        var vm = this;

        vm.collect = entity;
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
            if (vm.collect.id !== null) {
                Collect.update(vm.collect, onSaveSuccess, onSaveError);
            } else {
                Collect.save(vm.collect, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:collectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
