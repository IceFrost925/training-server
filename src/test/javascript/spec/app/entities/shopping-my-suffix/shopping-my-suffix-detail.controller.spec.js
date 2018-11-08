'use strict';

describe('Controller Tests', function() {

    describe('Shopping Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShopping, MockSUser, MockBooks;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShopping = jasmine.createSpy('MockShopping');
            MockSUser = jasmine.createSpy('MockSUser');
            MockBooks = jasmine.createSpy('MockBooks');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Shopping': MockShopping,
                'SUser': MockSUser,
                'Books': MockBooks
            };
            createController = function() {
                $injector.get('$controller')("ShoppingMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'trainingApp:shoppingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
