(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CollectMySuffixController', CollectMySuffixController);

    CollectMySuffixController.$inject = ['Collect'];

    function CollectMySuffixController(Collect) {

        var vm = this;

        vm.collects = [];

        loadAll();

        function loadAll() {
            Collect.query(function(result) {
                vm.collects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
