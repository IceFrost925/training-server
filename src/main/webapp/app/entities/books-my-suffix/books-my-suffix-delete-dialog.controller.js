(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('BooksMySuffixDeleteController',BooksMySuffixDeleteController);

    BooksMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Books'];

    function BooksMySuffixDeleteController($uibModalInstance, entity, Books) {
        var vm = this;

        vm.books = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Books.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
