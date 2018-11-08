(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('ShoppingMySuffixDetailController', ShoppingMySuffixDetailController);

    ShoppingMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Shopping', 'SUser', 'Books'];

    function ShoppingMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Shopping, SUser, Books) {
        var vm = this;

        vm.shopping = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:shoppingUpdate', function(event, result) {
            vm.shopping = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
