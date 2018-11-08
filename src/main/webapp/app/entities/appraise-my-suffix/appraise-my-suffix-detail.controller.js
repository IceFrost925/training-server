(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('AppraiseMySuffixDetailController', AppraiseMySuffixDetailController);

    AppraiseMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Appraise', 'SUser', 'Books'];

    function AppraiseMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Appraise, SUser, Books) {
        var vm = this;

        vm.appraise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:appraiseUpdate', function(event, result) {
            vm.appraise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
