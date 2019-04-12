/*
angular.module('demo', [])
    .controller('Hello', function($scope, $http) {
        $http.get('/job').
        then(function(response) {
            $scope.job = response.data;
        });
    });*/
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/job"
    }).then(function(data, status, jqxhr) {
        $('.job-companyName').append(data.companyName);
        // $('.greeting-content').append(data.content);
        console.log(jqxhr);
    });
});