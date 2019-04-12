$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/job"
    }).then(function(data, status, jqxhr) {
        $('.job-companyName').append(data.companyName);
        // $('.greeting-content').append(data.content);
        console.log(jqxhr);
    });
});