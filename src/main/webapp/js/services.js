function resourceErrorHandler(response) {
    window.alert(response.status + " - " + response.statusText);
}


angular.module('authApp.services', []).factory('User', function($resource) {
    return $resource('http://localhost:8080/auth-playground/resources/users/:id', {
        id: '@id'
    }, {
        get: {
            method: 'GET',
            interceptor: {
                responseError: resourceErrorHandler
            }
        },
        save: {
            method: 'POST'
        },
        query: {
            method: 'GET',
            isArray: true,
            interceptor: {
                responseError: resourceErrorHandler
            }
        },
        remove: {
            method: 'DELETE'
        },
        delete: {
            method: 'DELETE'
        },
        update: {
            method: 'PUT'
        }
    });
}).service('popupService', function($window) {
    this.showPopup = function(message) {
        return $window.confirm(message);
    }
});