(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orders-my-suffix', {
            parent: 'entity',
            url: '/orders-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.orders.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orders-my-suffix/ordersmySuffix.html',
                    controller: 'OrdersMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orders');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('orders-my-suffix-detail', {
            parent: 'orders-my-suffix',
            url: '/orders-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.orders.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orders-my-suffix/orders-my-suffix-detail.html',
                    controller: 'OrdersMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orders');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Orders', function($stateParams, Orders) {
                    return Orders.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orders-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orders-my-suffix-detail.edit', {
            parent: 'orders-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders-my-suffix/orders-my-suffix-dialog.html',
                    controller: 'OrdersMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orders-my-suffix.new', {
            parent: 'orders-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders-my-suffix/orders-my-suffix-dialog.html',
                    controller: 'OrdersMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                books: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orders-my-suffix', null, { reload: 'orders-my-suffix' });
                }, function() {
                    $state.go('orders-my-suffix');
                });
            }]
        })
        .state('orders-my-suffix.edit', {
            parent: 'orders-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders-my-suffix/orders-my-suffix-dialog.html',
                    controller: 'OrdersMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orders-my-suffix', null, { reload: 'orders-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orders-my-suffix.delete', {
            parent: 'orders-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orders-my-suffix/orders-my-suffix-delete-dialog.html',
                    controller: 'OrdersMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Orders', function(Orders) {
                            return Orders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orders-my-suffix', null, { reload: 'orders-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
