(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('address-my-suffix', {
            parent: 'entity',
            url: '/address-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.address.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address-my-suffix/addressesmySuffix.html',
                    controller: 'AddressMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('address-my-suffix-detail', {
            parent: 'address-my-suffix',
            url: '/address-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.address.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address-my-suffix/address-my-suffix-detail.html',
                    controller: 'AddressMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Address', function($stateParams, Address) {
                    return Address.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'address-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('address-my-suffix-detail.edit', {
            parent: 'address-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-my-suffix/address-my-suffix-dialog.html',
                    controller: 'AddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('address-my-suffix.new', {
            parent: 'address-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-my-suffix/address-my-suffix-dialog.html',
                    controller: 'AddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                tell: null,
                                company: null,
                                email: null,
                                content: null,
                                flag: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('address-my-suffix', null, { reload: 'address-my-suffix' });
                }, function() {
                    $state.go('address-my-suffix');
                });
            }]
        })
        .state('address-my-suffix.edit', {
            parent: 'address-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-my-suffix/address-my-suffix-dialog.html',
                    controller: 'AddressMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('address-my-suffix', null, { reload: 'address-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('address-my-suffix.delete', {
            parent: 'address-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-my-suffix/address-my-suffix-delete-dialog.html',
                    controller: 'AddressMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('address-my-suffix', null, { reload: 'address-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
