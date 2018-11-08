(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ShoppingMySuffixDialogController', ShoppingMySuffixDialogController);

    ShoppingMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Shopping', 'SUser', 'Books'];

    function ShoppingMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Shopping, SUser, Books) {
        var vm = this;

        vm.shopping = entity;
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
            if (vm.shopping.id !== null) {
                Shopping.update(vm.shopping, onSaveSuccess, onSaveError);
            } else {
                Shopping.save(vm.shopping, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:shoppingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
