(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SUserMySuffixController', SUserMySuffixController);

    SUserMySuffixController.$inject = ['SUser'];

    function SUserMySuffixController(SUser) {

        var vm = this;

        vm.sUsers = [];

        loadAll();

        function loadAll() {
            SUser.query(function(result) {
                vm.sUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
