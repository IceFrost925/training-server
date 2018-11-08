(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shopping-my-suffix', {
            parent: 'entity',
            url: '/shopping-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.shopping.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shopping-my-suffix/shoppingsmySuffix.html',
                    controller: 'ShoppingMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('shopping');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('shopping-my-suffix-detail', {
            parent: 'shopping-my-suffix',
            url: '/shopping-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.shopping.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shopping-my-suffix/shopping-my-suffix-detail.html',
                    controller: 'ShoppingMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('shopping');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Shopping', function($stateParams, Shopping) {
                    return Shopping.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shopping-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shopping-my-suffix-detail.edit', {
            parent: 'shopping-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shopping-my-suffix/shopping-my-suffix-dialog.html',
                    controller: 'ShoppingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Shopping', function(Shopping) {
                            return Shopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shopping-my-suffix.new', {
            parent: 'shopping-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shopping-my-suffix/shopping-my-suffix-dialog.html',
                    controller: 'ShoppingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shopping-my-suffix', null, { reload: 'shopping-my-suffix' });
                }, function() {
                    $state.go('shopping-my-suffix');
                });
            }]
        })
        .state('shopping-my-suffix.edit', {
            parent: 'shopping-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shopping-my-suffix/shopping-my-suffix-dialog.html',
                    controller: 'ShoppingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Shopping', function(Shopping) {
                            return Shopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shopping-my-suffix', null, { reload: 'shopping-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shopping-my-suffix.delete', {
            parent: 'shopping-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shopping-my-suffix/shopping-my-suffix-delete-dialog.html',
                    controller: 'ShoppingMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Shopping', function(Shopping) {
                            return Shopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shopping-my-suffix', null, { reload: 'shopping-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
