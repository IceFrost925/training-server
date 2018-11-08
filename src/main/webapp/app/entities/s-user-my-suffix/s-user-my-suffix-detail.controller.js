(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('SUserMySuffixDetailController', SUserMySuffixDetailController);

    SUserMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SUser'];

    function SUserMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, SUser) {
        var vm = this;

        vm.sUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:sUserUpdate', function(event, result) {
            vm.sUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
