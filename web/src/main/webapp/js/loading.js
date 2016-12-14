$(document).ready(function(){
    parent.frames[0].queryHintCallback("query_hint");
});

function show_query_hint(query_hint){
    var query_hint = document.getElementById(query_hint);
    query_hint.style.display="block";
}

function queryHintCallback(query_hint){
    var query_hint = document.getElementById(query_hint);
    query_hint.style.display="none";
}