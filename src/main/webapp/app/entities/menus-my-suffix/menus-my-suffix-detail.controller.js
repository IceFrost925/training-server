(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('MenusMySuffixDetailController', MenusMySuffixDetailController);

    MenusMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Menus'];

    function MenusMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Menus) {
        var vm = this;

        vm.menus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:menusUpdate', function(event, result) {
            vm.menus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
