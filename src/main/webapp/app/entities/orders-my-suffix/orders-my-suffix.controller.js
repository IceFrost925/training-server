(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersMySuffixController', OrdersMySuffixController);

    OrdersMySuffixController.$inject = ['Orders'];

    function OrdersMySuffixController(Orders) {

        var vm = this;

        vm.orders = [];

        loadAll();

        function loadAll() {
            Orders.query(function(result) {
                vm.orders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
