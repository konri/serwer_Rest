<!DOCTYPE html>
<html>
<head>
    <title>REST Tester</title>
    <meta charset="UTF-8">
</head>
<body>
<div id="logMsgDiv"></div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript">
    var $ = jQuery.noConflict();

    $.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: "http://localhost:8080/artivact-test-1.0/rest-api/rest-cors-demo/get-method/",
        type: "GET",
        success: function( jsonObj, textStatus, xhr ) {
            var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>" + jsonObj.message + "</p>";
            $( "#logMsgDiv" ).html( htmlContent );
        },
        error: function( xhr, textStatus, errorThrown ) {
            console.log( "HTTP Status: " + xhr.status );
            console.log( "Error textStatus: " + textStatus );
            console.log( "Error thrown: " + errorThrown );
        }
    } );

    $.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: "http://localhost:8080/artivact-test-1.0/rest-api/rest-cors-demo/post-method/",
        type: "POST",
        success: function( jsonObj, textStatus, xhr ) {
            var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>" + jsonObj.message + "</p>";
            $( "#logMsgDiv" ).html( htmlContent );
        },
        error: function( xhr, textStatus, errorThrown ) {
            console.log( "HTTP Status: " + xhr.status );
            console.log( "Error textStatus: " + textStatus );
            console.log( "Error thrown: " + errorThrown );
        }
    } );

    $.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: "http://localhost:8080/artivact-test-1.0/rest-api/rest-cors-demo/put-method/",
        type: "PUT",
        success: function( jsonObj, textStatus, xhr ) {
            var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>" + jsonObj.message + "</p>";
            $( "#logMsgDiv" ).html( htmlContent );
        },
        error: function( xhr, textStatus, errorThrown ) {
            console.log( "HTTP Status: " + xhr.status );
            console.log( "Error textStatus: " + textStatus );
            console.log( "Error thrown: " + errorThrown );
        }
    } );

    $.ajax( {
        cache: false,
        crossDomain: true,
        dataType: "json",
        url: "http://localhost:8080/artivact-test-1.0/rest-api/rest-cors-demo/delete-method/",
        type: "DELETE",
        success: function( jsonObj, textStatus, xhr ) {
            var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>" + jsonObj.message + "</p>";
            $( "#logMsgDiv" ).html( htmlContent );
        },
        error: function( xhr, textStatus, errorThrown ) {
            console.log( "HTTP Status: " + xhr.status );
            console.log( "Error textStatus: " + textStatus );
            console.log( "Error thrown: " + errorThrown );
        }
    } );
</script>
</body>
</html>