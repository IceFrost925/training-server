'use strict';

describe('Controller Tests', function() {

    describe('Appraise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAppraise, MockSUser, MockBooks;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAppraise = jasmine.createSpy('MockAppraise');
            MockSUser = jasmine.createSpy('MockSUser');
            MockBooks = jasmine.createSpy('MockBooks');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Appraise': MockAppraise,
                'SUser': MockSUser,
                'Books': MockBooks
            };
            createController = function() {
                $injector.get('$controller')("AppraiseMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'trainingApp:appraiseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
