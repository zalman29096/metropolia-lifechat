/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $('#signUp').click(function (event) {
        var username = $('#signUpUsername').val();
        var x2js = new X2JS();
        var form = $("#signUpForm").serializeJSON();
        var xmlDoc = x2js.json2xml(form);
        var url = '/Project/rest/auth/signUp/' + username;
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/xml',
            processData: false,
            data: xmlDoc
        });
    });

    $('#signIn').click(function (event) {
        var username = $('#signInUsername').val();
        var password = $('#signInPassword').val();
        var url = '/Project/rest/auth/signIn/' + username + '/' + password;
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data) {
                if (data === "true") {
                    window.location.replace("/Project/authenticated.html");
                } else {
                    alert("no");
                }
            }
        });
    });
});

