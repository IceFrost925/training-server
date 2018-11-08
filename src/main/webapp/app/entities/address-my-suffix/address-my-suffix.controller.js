(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AddressMySuffixController', AddressMySuffixController);

    AddressMySuffixController.$inject = ['Address'];

    function AddressMySuffixController(Address) {

        var vm = this;

        vm.addresses = [];

        loadAll();

        function loadAll() {
            Address.query(function(result) {
                vm.addresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
