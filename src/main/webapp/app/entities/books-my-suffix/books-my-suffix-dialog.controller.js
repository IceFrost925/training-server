(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('BooksMySuffixDialogController', BooksMySuffixDialogController);

    BooksMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Books'];

    function BooksMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Books) {
        var vm = this;

        vm.books = entity;
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
            if (vm.books.id !== null) {
                Books.update(vm.books, onSaveSuccess, onSaveError);
            } else {
                Books.save(vm.books, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:booksUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
