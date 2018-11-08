(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('menus-my-suffix', {
            parent: 'entity',
            url: '/menus-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.menus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menus-my-suffix/menusmySuffix.html',
                    controller: 'MenusMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('menus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('menus-my-suffix-detail', {
            parent: 'menus-my-suffix',
            url: '/menus-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.menus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menus-my-suffix/menus-my-suffix-detail.html',
                    controller: 'MenusMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('menus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Menus', function($stateParams, Menus) {
                    return Menus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'menus-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('menus-my-suffix-detail.edit', {
            parent: 'menus-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menus-my-suffix/menus-my-suffix-dialog.html',
                    controller: 'MenusMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Menus', function(Menus) {
                            return Menus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menus-my-suffix.new', {
            parent: 'menus-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menus-my-suffix/menus-my-suffix-dialog.html',
                    controller: 'MenusMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                second: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('menus-my-suffix', null, { reload: 'menus-my-suffix' });
                }, function() {
                    $state.go('menus-my-suffix');
                });
            }]
        })
        .state('menus-my-suffix.edit', {
            parent: 'menus-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menus-my-suffix/menus-my-suffix-dialog.html',
                    controller: 'MenusMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Menus', function(Menus) {
                            return Menus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menus-my-suffix', null, { reload: 'menus-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menus-my-suffix.delete', {
            parent: 'menus-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menus-my-suffix/menus-my-suffix-delete-dialog.html',
                    controller: 'MenusMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Menus', function(Menus) {
                            return Menus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menus-my-suffix', null, { reload: 'menus-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
