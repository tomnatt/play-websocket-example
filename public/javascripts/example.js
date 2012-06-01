$(document).ready(function() {
    console.log("I'm about to connect");
    connect();
    
    connectionReady();
    
});

// wait until connection is established before setting up the rest of the page 
function connectionReady() {
    if (ws.readyState != 1) {
        setTimeout(connectionReady, 500);
        console.log("round again");
    } else {
        pageReady();
    }
}

function connect() {
    ws = new WebSocket("ws://localhost:9000/echoSocket");

    ws.onopen = function(evt) { 
        console.log("connected");
    }

    ws.onclose = function(evt) {
        console.log("disconnected");
    }

    ws.onmessage = function(evt) {
        console.log("response: " + evt.data);
        alert(evt.data);
    }

    ws.onerror = function(evt) {
        console.log("error: " + evt.data);
    }
    
}

function disconnect() {
    ws.close();
}

function send(words) {
    ws.send(words);
}

function pageReady() {
    
    $("#button").click(function() {
        send("bong");
    });
    
}
