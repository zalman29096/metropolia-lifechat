/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    var username;
    var role;
    var socket;

    $.ajax({
        type: 'GET',
        url: '/Project/rest/users/auth',
        success: function (data) {
            if (data === undefined) {
                alert("noSession");
                window.location.replace("index.html");
            } else {
                $("#username").append(data);
                username = data;
                webSocket();
            }
        }
    });

    //Get User
    $.ajax({
        type: 'GET',
        url: '/Project/rest/users/user',
        success: function (data) {
            var x2js = new X2JS();
            var user = x2js.xml2json(data);
            role = user['user']['role'];
            if (role === "orderly") {
                $("#globalMessageBlock").remove();
            } else {
                $("#globalTitle").append(role + "s");
            }
            if (user['user'].hasOwnProperty("rooms")) {
                var rooms = user["user"].rooms;
                if (rooms instanceof Array) {
                    $.each(rooms, function () {
                        createRoomChats(this);
                    });
                } else
                    createRoomChats(rooms);
            }
        }
    });

    function createRoomChats(data) {
        $("#roomChats").append("room " + data + "<br> <textarea id = 'room" + data + "Messages' readonly = 'readonly' rows = '10' cols = '45'></textarea><br> \n\
                                        <input type = 'text' placeholder = 'message' id = 'room" + data + "Message' / > \n\
                                        <button type = 'button' class = 'sendRoomsMessage' id = '" + data + "' > send </button><br>");
    }

    function webSocket() {
        var wsUri = "ws://" + document.location.host + "/Project/endpoint";
        socket = new WebSocket(wsUri);

        socket.onopen = function (msg) {
            // Logic for opened connection
            console.log('Connection successfully opened');
            
            webSocketSend(true, '', '', '', '', username, '');
        };

        socket.onmessage = function (msg) {
            // Handle received data
            var json = JSON.parse(msg.data);
            if (json.chat === "global") {
                $("#globalMessages").append(json.username + ':' + json.message + '\n');
            } else if (json.chat === "room") {
                var textarea = "room" + json.room + "Messages";
                var id = "#" + textarea;
                $(id).append(json.username + ':' + json.message + '\n');
            }

        };

        $(window).unload(function () {
            socket.onclose = function () {
                // Logic for closed connection
                socket = new WebSocket(wsUri);
                console.log('Connection was closed.');
            };
            socket.close();
        });

        socket.error = function (err) {
            socket = new WebSocket(wsUri);
            console.log(err); // Write errors to console
        };
    }
    ;

    function webSocketSend(flag, chat, room, role, usernameTo, username, message) {
        var json = JSON.stringify({'flag': flag,
            'chat': chat,
            'room': room,
            'role': role,
            'usernameTo': usernameTo,
            'username': username,
            'message': message});
        socket.send(json);
    }

    $("#sendGlobalMessage").click(function (event) {
        var message = $("#globalMessage").val();
        webSocketSend(false, 'global', '', role, '', username, message);
    });

    $(document.body).on('click', '.sendRoomsMessage', function () {
        var room = $(this).attr('id');
        var inputId = "#room" + room + "Message";
        var message = $(inputId).val();
        webSocketSend(false, 'room', room, '', '', username, message);
    });

    $("#logOut").click(function (event) {
        $.ajax({
            type: 'POST',
            url: '/Project/rest/users/auth',
            success: function () {
                window.location.replace("index.html");
            }
        });
    });

    $("#getUsers").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/users',
            success: function (data) {
                var x2js = new X2JS();
                var users = x2js.xml2json(data)['users'];
                console.log(users);
                console.log(data);
                if (users.hasOwnProperty("user")) {
                    var arrayOfUsers = users.user;
                    if (arrayOfUsers instanceof Array) {
                        $.each(arrayOfUsers, function () {
                            writeUsersToView(this['username']);
                        });
                    } else
                        writeUsersToView(arrayOfUsers['username']);
                }
            }
        });
    });

    function writeUsersToView(data) {
        $("#users").append("<button  class='user' type='button' id=" + data + "> " + data + "</button><br>");
    }
});
