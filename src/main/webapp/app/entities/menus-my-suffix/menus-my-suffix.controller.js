(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('MenusMySuffixController', MenusMySuffixController);

    MenusMySuffixController.$inject = ['Menus'];

    function MenusMySuffixController(Menus) {

        var vm = this;

        vm.menus = [];

        loadAll();

        function loadAll() {
            Menus.query(function(result) {
                vm.menus = result;
                vm.searchQuery = null;
            });
        }
    }
})();
