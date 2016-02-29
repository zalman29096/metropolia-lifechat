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

    $("#getUsers").click(function () {
        $(".global-chat").hide();
        $("#roomChats").hide();
        $("#")
        $(".active").removeClass("active");
        $(this).addClass("active");
    });
    /*$("#getGlobalChat").click(function () {
        $(".global-chat").show();
        $("#roomChats").hide();
        $(".active").removeClass("active");
        $(this).addClass("active");
    });*/

    //Get User
    /*$.ajax({
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
    });*/

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
           // console.log(msg);
            var json = JSON.parse(msg.data);
            console.log(json);
            /*if (json.chat === "global") {
                $("#globalMessages").append(json.username + ':' + json.message + '\n');
            } else if (json.chat === "room") {*/
                var id = "#" + json.chatId+"chat";
                $(id).append(json.username + ':' + json.message + '\n');
            //}

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

    /*$("#sendGlobalMessage").click(function (event) {
        var message = $("#globalMessage").val();
        webSocketSend(false, 'global', '', role, '', username, message);
    });*/

    $(document.body).on('click', '.sendMessage', function () {
        var chatId = $(this).data('chat');
        var message = $(this).prev(".msg-input").val();
        webSocketSend(chatId, 0, message);
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
    function createRoomChatHTML(data) {
        $("#roomChats").append("<h3>Room " + data.roomNumber + "</h3> <textarea class= 'form-control' id = '" + data.chatId + "chat' readonly = 'readonly' rows = '10' cols = '45'></textarea><br> \n\
                                        <input class= 'form-control msg-input'  type = 'text' placeholder = 'Message' / > \n\
                                        <button style = 'margin : 15px' type = 'button' data-chat='" + data.chatId + "' class = 'btn btn-md btn-success sendMessage' > Send </button><br>");
    }
    function createGlobalChatHTML(data) {
        $("#globalChat").append("<textarea class= 'form-control' id = '" + data.chatId + "chat' readonly = 'readonly' rows = '10' cols = '45'></textarea><br> \n\
                                        <input class= 'form-control msg-input'  type = 'text' placeholder = 'Message' / > \n\
                                        <button style = 'margin : 15px' type = 'button' data-chat='" + data.chatId + "' class = 'btn btn-md btn-success sendMessage' > Send </button><br>");
    }
    var makeRoomChats = function (data) {
            $("#globalChat").hide();
            $("#roomChats").show();
            $(".active").removeClass("active");
            $("#getRoomChats").addClass("active");
            console.log(data);
            var x2js = new X2JS();
            var chats = x2js.xml2json(data);
            console.log(chats);
            $("#roomChats").html("");
            chats.roomChats.roomChat.forEach(function(cht){
                $("#getHistory").data("add-to-url", cht.chatId);
                $("#getHistory").trigger("click");
                createRoomChatHTML(cht);
            });
        }
    var makeGlobalChat = function (data) {
            $("#globalChat").show();
            $("#roomChats").hide();
            $(".active").removeClass("active");
            $("#getGlobalChat").addClass("active");
            var x2js = new X2JS();
            var chat = x2js.xml2json(data);
            console.log(data)
            $("#globalChat").html("");
            $("#getHistory").data("add-to-url", chat.globalChat.chatId);
            $("#getHistory").trigger("click");
            createGlobalChatHTML(chat.globalChat);
        }
    var getHistory = function(data){
        var x2js = new X2JS();
        var hist = x2js.xml2json(data);
        if(hist.historyEntries.historyEntry){
            if(hist.historyEntries.historyEntry.constructor === Array){
                hist.historyEntries.historyEntry.forEach(function(ent){
                    var id = "#" + ent.chatId+"chat";
                    $(id).append(ent.username + ':' + ent.message + '\n');  
                });
            }
            if(typeof hist.historyEntries.historyEntry === "object"){
                var id = "#" + hist.historyEntries.historyEntry.chatId+"chat";
                $(id).append(hist.historyEntries.historyEntry.username + ':' + hist.historyEntries.historyEntry.message + '\n');  
            }
        } 
    }
    
    var callbacks = {
        justLog : function(data){
            console.log(data);
        },
        createRoomChats : makeRoomChats,
        createGlobalChat : makeGlobalChat,
        getHistory : getHistory

    };

    $(document.body).on("click", ".ajax-button", function(){
        var add_to_url =$(this).data("add-to-url");
        if(!add_to_url) add_to_url = "";
        var act = $(this).data("action");
        var callback = callbacks[$(this).data("callback")];
        var type = $(this).data("type");
        console.log(act)
        $.ajax({
            url : act+add_to_url,
            type : type,
            success : function(data){console.log(data);callback(data);}
        });
    });
    $("#getGlobalChat").trigger("click");
});