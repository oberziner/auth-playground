angular.module('authApp.services', []).factory('User', function($resource) {
	return $resource('http://localhost:8080/auth-playground/resources/users/:id', { id: '@id' }, {
		update: {
			method: 'PUT'
		}
		});
}).service('popupService',function($window){
	this.showPopup=function(message){
		return $window.confirm(message);
	}
});
