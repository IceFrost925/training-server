(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('s-user-my-suffix', {
            parent: 'entity',
            url: '/s-user-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.sUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-user-my-suffix/s-usersmySuffix.html',
                    controller: 'SUserMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('s-user-my-suffix-detail', {
            parent: 's-user-my-suffix',
            url: '/s-user-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.sUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-user-my-suffix/s-user-my-suffix-detail.html',
                    controller: 'SUserMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SUser', function($stateParams, SUser) {
                    return SUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 's-user-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('s-user-my-suffix-detail.edit', {
            parent: 's-user-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-user-my-suffix/s-user-my-suffix-dialog.html',
                    controller: 'SUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SUser', function(SUser) {
                            return SUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-user-my-suffix.new', {
            parent: 's-user-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-user-my-suffix/s-user-my-suffix-dialog.html',
                    controller: 'SUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                image: null,
                                email: null,
                                passwd: null,
                                tell: null,
                                integral: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('s-user-my-suffix', null, { reload: 's-user-my-suffix' });
                }, function() {
                    $state.go('s-user-my-suffix');
                });
            }]
        })
        .state('s-user-my-suffix.edit', {
            parent: 's-user-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-user-my-suffix/s-user-my-suffix-dialog.html',
                    controller: 'SUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SUser', function(SUser) {
                            return SUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-user-my-suffix', null, { reload: 's-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-user-my-suffix.delete', {
            parent: 's-user-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-user-my-suffix/s-user-my-suffix-delete-dialog.html',
                    controller: 'SUserMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SUser', function(SUser) {
                            return SUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-user-my-suffix', null, { reload: 's-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
