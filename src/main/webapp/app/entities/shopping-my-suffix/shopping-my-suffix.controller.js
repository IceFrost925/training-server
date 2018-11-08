(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ShoppingMySuffixController', ShoppingMySuffixController);

    ShoppingMySuffixController.$inject = ['Shopping'];

    function ShoppingMySuffixController(Shopping) {

        var vm = this;

        vm.shoppings = [];

        loadAll();

        function loadAll() {
            Shopping.query(function(result) {
                vm.shoppings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
