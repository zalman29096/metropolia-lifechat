/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: '/Project/rest/auth',
        success: function (data) {
            if (data === "false") {
                alert("noSession");
                window.location.replace("index.html");
            } else {
                alert(data);
            }
        }
    });

    $('#logOut').click(function (event) {
        $.ajax({
        type: 'POST',
        url: '/Project/rest/auth',
        success: function () {
            window.location.replace("index.html");
        }
    });
    });
});
