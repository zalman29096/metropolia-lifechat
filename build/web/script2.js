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

    $("#getRoomChats").click(function () {
        $(".global-chat").hide();
        $("#roomChats").show();
        $(".active").removeClass("active");
        $(this).addClass("active");
    });
    $("#getGlobalChat").click(function () {
        $(".global-chat").show();
        $("#roomChats").hide();
        $(".active").removeClass("active");
        $(this).addClass("active");
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
        $("#roomChats").append("<h3>Room " + data + "</h3> <textarea class= 'form-control' id = 'room" + data + "Messages' readonly = 'readonly' rows = '10' cols = '45'></textarea><br> \n\
                                        <input class= 'form-control' type = 'text' placeholder = 'Message' id = 'room" + data + "Message' / > \n\
                                        <button style = 'margin : 15px' type = 'button' class = 'btn btn-md btn-success sendRoomsMessage' id = '" + data + "' > Send </button><br>");
    }

    function webSocket() {
        var wsUri = "ws://" + document.location.host + "/Project/endpoint";
        socket = new WebSocket(wsUri);

        socket.onopen = function (msg) {
            // Logic for opened connection
            console.log('Connection successfully opened');

            webSocketSend(5, 0, 'test', '');
        };

        socket.onmessage = function (msg) {
            // Handle received data
            // flag!!!!!!!!!!!!!!!!!!!!!!!!
            // 0-standard message
            // 1-emergency message
            // 2-assignment inside room message
            // 3-file
            // 4-new assignment (assignmentId inside chatId)
            // 5-assignment accepted (assignmentId inside chatId)
            // 6-assignment done (assignmentId inside chatId)
            //orderly should only see new assignments and the ones that he has accepted or completed
            //doctor or nurse should see all assignments that he or she has created(all : new, accpeted, completed)
            console.log(msg);
            var json = JSON.parse(msg.data);
            if (json.chat === "global") {
                $("#globalMessages").append(json.username + ':' + json.message + '\n');
            } else if (json.chat === "room") {
                var textarea = "room" + json.room + "Messages";
                var id = "#" + textarea;
                $(id).append(json.username + ':' + json.message + '\n');
            }

        };

        socket.onclose = function () {
            // Logic for closed connection
            console.log('Connection was closed.');
            window.location.replace("index.html");
        };

        $(window).unload(function () {
            socket.close();
        });

        socket.error = function (err) {
            console.log(err);
            window.location.replace("index.html");
            // Write errors to console
        };
    }
    ;

    function webSocketSend(chatId, flag, message) {
        var json = JSON.stringify({'chatId': chatId,
            'flag': flag,
            'message': message});
        socket.send(json);
    }


    /*function webSocketSend(flag, chat, room, role, usernameTo, username, message) {
     var json = JSON.stringify({'flag': flag,
     'chat': chat,
     'room': room,
     'role': role,
     'usernameTo': usernameTo,
     'username': username,
     'message': message});
     socket.send(json);
     }*/

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

    $("#getGlobalChat").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/chats/global',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#getRoomChats").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/chats/rooms',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#getHistory").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/chats/history/5',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#createPrivateChat").click(function (event) {
        $.ajax({
            type: 'POST',
            url: '/Project/rest/chats/privateChat',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#getPrivateChats").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/chats/privateChats',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#createAssignment").click(function (event) {
        $.ajax({
            type: 'POST',
            url: '/Project/rest/assignments/testtesttest',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#getAssignment").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/assignments/0',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#getAssignments").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/assignments',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#acceptAssignment").click(function (event) {
        $.ajax({
            type: 'PUT',
            url: '/Project/rest/assignments/acceptAssignment/0'
        });
    });

    $("#assignmentDone").click(function (event) {
        $.ajax({
            type: 'PUT',
            url: '/Project/rest/assignments/assignmentDone/0'
        });
    });

    $("#leavePrivateChat").click(function (event) {
        $.ajax({
            type: 'DELETE',
            url: '/Project/rest/chats/privateChat/7',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#addToChat").click(function (event) {
        $.ajax({
            type: 'PUT',
            url: '/Project/rest/chats/privateChat/8/1234',
            success: function (data) {
                console.log(data);
            }
        });
    });
    
    $("#newMessages").click(function (event) {
        $.ajax({
            type: 'GET',
            url: '/Project/rest/chats/newMessagesCount/5',
            success: function (data) {
                console.log(data);
            }
        });
    });

    $("#uploadFile").click(function (event) {
        var file = $('input[name="file"').get(0).files[0];

        var formData = new FormData();
        formData.append('file', file);

        $.ajax({
            url: '/Project/rest/file/upload', //Server script to process data
            type: 'POST',
            data: formData,
            cache: false,
            contentType: false,
            dataType: false,
            processData: false,
            success: function (data, status) {
                console.log(data);
                console.log(status);
                //$("#imgTest").append("<img src='images/" + data +"'>");
                if (status === "success"){
                    //$("#imgTest").append("<a href='files/" + data +"' target='_blank' download>Download</a>");
                    webSocketSend(chatId, 4, data);
                }
                
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus);

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
