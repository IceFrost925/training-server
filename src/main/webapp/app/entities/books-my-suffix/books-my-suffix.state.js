(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('books-my-suffix', {
            parent: 'entity',
            url: '/books-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.books.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/books-my-suffix/booksmySuffix.html',
                    controller: 'BooksMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('books');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('books-my-suffix-detail', {
            parent: 'books-my-suffix',
            url: '/books-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trainingApp.books.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/books-my-suffix/books-my-suffix-detail.html',
                    controller: 'BooksMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('books');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Books', function($stateParams, Books) {
                    return Books.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'books-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('books-my-suffix-detail.edit', {
            parent: 'books-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/books-my-suffix/books-my-suffix-dialog.html',
                    controller: 'BooksMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Books', function(Books) {
                            return Books.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('books-my-suffix.new', {
            parent: 'books-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/books-my-suffix/books-my-suffix-dialog.html',
                    controller: 'BooksMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                picture: null,
                                name: null,
                                type: null,
                                number: null,
                                count: null,
                                price: null,
                                describe: null,
                                extra1: null,
                                extra2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('books-my-suffix', null, { reload: 'books-my-suffix' });
                }, function() {
                    $state.go('books-my-suffix');
                });
            }]
        })
        .state('books-my-suffix.edit', {
            parent: 'books-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/books-my-suffix/books-my-suffix-dialog.html',
                    controller: 'BooksMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Books', function(Books) {
                            return Books.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('books-my-suffix', null, { reload: 'books-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('books-my-suffix.delete', {
            parent: 'books-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/books-my-suffix/books-my-suffix-delete-dialog.html',
                    controller: 'BooksMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Books', function(Books) {
                            return Books.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('books-my-suffix', null, { reload: 'books-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
