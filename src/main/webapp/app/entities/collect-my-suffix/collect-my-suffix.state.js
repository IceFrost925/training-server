(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('collect-my-suffix', {
            parent: 'entity',
            url: '/collect-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.collect.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/collect-my-suffix/collectsmySuffix.html',
                    controller: 'CollectMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('collect');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('collect-my-suffix-detail', {
            parent: 'collect-my-suffix',
            url: '/collect-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.collect.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/collect-my-suffix/collect-my-suffix-detail.html',
                    controller: 'CollectMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('collect');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Collect', function($stateParams, Collect) {
                    return Collect.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'collect-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('collect-my-suffix-detail.edit', {
            parent: 'collect-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/collect-my-suffix/collect-my-suffix-dialog.html',
                    controller: 'CollectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Collect', function(Collect) {
                            return Collect.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('collect-my-suffix.new', {
            parent: 'collect-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/collect-my-suffix/collect-my-suffix-dialog.html',
                    controller: 'CollectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('collect-my-suffix', null, { reload: 'collect-my-suffix' });
                }, function() {
                    $state.go('collect-my-suffix');
                });
            }]
        })
        .state('collect-my-suffix.edit', {
            parent: 'collect-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/collect-my-suffix/collect-my-suffix-dialog.html',
                    controller: 'CollectMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Collect', function(Collect) {
                            return Collect.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('collect-my-suffix', null, { reload: 'collect-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('collect-my-suffix.delete', {
            parent: 'collect-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/collect-my-suffix/collect-my-suffix-delete-dialog.html',
                    controller: 'CollectMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Collect', function(Collect) {
                            return Collect.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('collect-my-suffix', null, { reload: 'collect-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
