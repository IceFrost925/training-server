(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('BooksMySuffixController', BooksMySuffixController);

    BooksMySuffixController.$inject = ['Books'];

    function BooksMySuffixController(Books) {

        var vm = this;

        vm.books = [];

        loadAll();

        function loadAll() {
            Books.query(function(result) {
                vm.books = result;
                vm.searchQuery = null;
            });
        }
    }
})();
