(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('appraise-my-suffix', {
            parent: 'entity',
            url: '/appraise-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.appraise.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appraise-my-suffix/appraisesmySuffix.html',
                    controller: 'AppraiseMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appraise');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('appraise-my-suffix-detail', {
            parent: 'appraise-my-suffix',
            url: '/appraise-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.appraise.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appraise-my-suffix/appraise-my-suffix-detail.html',
                    controller: 'AppraiseMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appraise');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Appraise', function($stateParams, Appraise) {
                    return Appraise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'appraise-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('appraise-my-suffix-detail.edit', {
            parent: 'appraise-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appraise-my-suffix/appraise-my-suffix-dialog.html',
                    controller: 'AppraiseMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Appraise', function(Appraise) {
                            return Appraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('appraise-my-suffix.new', {
            parent: 'appraise-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appraise-my-suffix/appraise-my-suffix-dialog.html',
                    controller: 'AppraiseMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                star: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('appraise-my-suffix', null, { reload: 'appraise-my-suffix' });
                }, function() {
                    $state.go('appraise-my-suffix');
                });
            }]
        })
        .state('appraise-my-suffix.edit', {
            parent: 'appraise-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appraise-my-suffix/appraise-my-suffix-dialog.html',
                    controller: 'AppraiseMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Appraise', function(Appraise) {
                            return Appraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appraise-my-suffix', null, { reload: 'appraise-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('appraise-my-suffix.delete', {
            parent: 'appraise-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appraise-my-suffix/appraise-my-suffix-delete-dialog.html',
                    controller: 'AppraiseMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Appraise', function(Appraise) {
                            return Appraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appraise-my-suffix', null, { reload: 'appraise-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
