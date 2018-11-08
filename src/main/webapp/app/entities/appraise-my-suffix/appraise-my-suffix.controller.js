(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AppraiseMySuffixController', AppraiseMySuffixController);

    AppraiseMySuffixController.$inject = ['Appraise'];

    function AppraiseMySuffixController(Appraise) {

        var vm = this;

        vm.appraises = [];

        loadAll();

        function loadAll() {
            Appraise.query(function(result) {
                vm.appraises = result;
                vm.searchQuery = null;
            });
        }
    }
})();
