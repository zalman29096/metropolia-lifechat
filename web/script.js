/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#role").change(function () {
        if ($(this).val() === "orderly")
            $("#rooms-select").hide();
        else
            $("#rooms-select").show();
    });

    $("#register-show").click(function () {
        $(".login-box").hide();
        $(".register-box").show();
    });

    $("#login-show").click(function () {
        $(".login-box").show();
        $(".register-box").hide();
    });

    $("#signup-form").submit(function (e) {
        e.preventDefault();
        var x2js = new X2JS();
        var form = $(this).serializeJSON();
        console.log(form);
        var xmlDoc = x2js.json2xml(form);
        var url = "/Project/rest/users/auth/signUp/" + $("#password").val();
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/xml',
            processData: false,
            data: xmlDoc,
            success: function (data) {
                if (data === "true") {
                    $(".login-box").show();
                    $(".register-box").hide();
                    $("#user-registered").hide();
                } else {
                    $("#user-registered").show();
                }
            }
        });
    });

    $("#login-form").submit(function (e) {
        e.preventDefault();
        var username = $("#signInUsername").val();
        var password = $("#signInPassword").val();
        var url = '/Project/rest/users/auth/signIn/' + username + '/' + password;
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data) {
                if (data === "true") {
                    window.location.replace("/Project/cabinet.html");
                } else {
                    alert("no");
                }
            }
        });
    });
});

