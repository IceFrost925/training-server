(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('BooksMySuffixDetailController', BooksMySuffixDetailController);

    BooksMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Books'];

    function BooksMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Books) {
        var vm = this;

        vm.books = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trainingApp:booksUpdate', function(event, result) {
            vm.books = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
