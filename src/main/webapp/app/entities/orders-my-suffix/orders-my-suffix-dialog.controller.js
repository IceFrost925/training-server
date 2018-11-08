(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('OrdersMySuffixDialogController', OrdersMySuffixDialogController);

    OrdersMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'SUser', 'Address'];

    function OrdersMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orders, SUser, Address) {
        var vm = this;

        vm.orders = entity;
        vm.clear = clear;
        vm.save = save;
        vm.susers = SUser.query();
        vm.addresses = Address.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orders.id !== null) {
                Orders.update(vm.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save(vm.orders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trainingApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
