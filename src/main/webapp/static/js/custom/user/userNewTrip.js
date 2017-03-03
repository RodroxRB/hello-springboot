var max=0;
var map;
var options = {
    types: ['(cities)']
};
var markers=[];
var end_marker=[];
var flightPlanCoordinates = [];
var flightPath;

function collectFormData(fields) {
    var data = {};
    for (var i = 0; i < fields.length; i++) {
        var $item = $(fields[i]);
        data[$item.attr('name')] = $item.val();
    }
    return data;
}

function updateLine() {
    flightPlanCoordinates = [];
    if (flightPath!=null)
        flightPath.setMap(null);
    for (i = 0; i < markers.length; i++) {
        flightPlanCoordinates.push({"lat":markers[i].getPosition().lat(),lng:markers[i].getPosition().lng()});
    }
    console.log(flightPlanCoordinates);
    flightPath = new google.maps.Polyline({
        path: flightPlanCoordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });

    flightPath.setMap(map);
}

$(document).ready(function() {

    var $form = $('#trip_form');
    $form.bind('submit', function(e) {
        // Ajax validation
        var $inputs = $form.find('input');
        var data = collectFormData($inputs);

        $.post('/user/tripAjax.json', data, function(response) {
            if (response.status == 'FAIL') {
                $.notify.defaults({ className: "error" });
                var item = response.errorMessageList[0];
                console.log(item.fieldName);
                $.notify(item.message, { position:"bottom left" });

            } else {
                $form.unbind('submit');
                $form.submit();
            }
        }, 'json');

        e.preventDefault();
        return false;
    });
});


//GOOGLE MAP CAPABILITIES


function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -33.8688, lng: 151.2195},
        zoom: 2
    });

    //Add elements to
    var input = /** @type {!HTMLInputElement} */(
        document.getElementById('pac-input-start-name'));


    var autocomplete = new google.maps.places.Autocomplete(input,options);

    var input_end = /** @type {!HTMLInputElement} */(
        document.getElementById('pac-input-end-name'));


    var autocomplete_end = new google.maps.places.Autocomplete(input_end,options);


    var marker = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });
    markers.push(marker);

    var marker_end = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });
    markers.push(marker_end);

    autocomplete.addListener('place_changed', function()
    {
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        $("#pac-input-start-place_id").val(place.id);
        if (!place.geometry) {
            return;
        }
        $("#pac-input-start-lat").val(place.geometry.location.lat())
        $("#pac-input-start-lon").val(place.geometry.location.lng())
        // If the place has a geometry, then present it on a map.

        marker.setIcon(/** @type {google.maps.Icon} */({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
        updateLine();
    });
    autocomplete_end.addListener('place_changed', function()
    {
        marker_end.setVisible(false);
        var place = autocomplete_end.getPlace();
        $("#pac-input-end-place_id").val(place.id);
        if (!place.geometry) {
            return;
        }
        $("#pac-input-end-lat").val(place.geometry.location.lat())
        $("#pac-input-end-lon").val(place.geometry.location.lng())

        marker_end.setIcon(/** @type {google.maps.Icon} */({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker_end.setPosition(place.geometry.location);
        marker_end.setVisible(true);
        updateLine();
    });



}

//Add new city to the route

$("#new-city").click(
    function()
    {
        $("#middle_cities").append(
            "<br/><div class='row'><div class='form-group' id='placeStayings["+max+"]'><div class='col-md-2 text-left'></div><input id='place-staying-"+max+"-place_id' name='placeStayings["+max+"].place_id' class='form-control' type='hidden'><input id='place-staying-"+max+"-lat' name='placeStayings["+max+"].lat' class='form-control' type='hidden'><input id='place-staying-"+max+"-lon' name='placeStayings["+max+"].lon' class='form-control' type='hidden'><div class='col-md-5'><div class='input-group'><input id='place-staying-"+max+"-name' name='placeStayings["+max+"].name' class='form-control' type='text'><span class='input-group-addon'><i class='glyphicon glyphicon-plane'></i></span></div></div><div class='col-md-5'><div class='input-group'><input id='place-staying-"+max+"-date' name='placeStayings["+max+"].date' class='form-control' type='date'><span class='input-group-addon'><i class='glyphicon glyphicon-calendar'></i></span></div></div></div></div>"
                );
        var input = /** @type {!HTMLInputElement} */(
            document.getElementById("place-staying-"+max+"-name"));

        var local= "#place-staying-"+max+"-place_id";
        var lat= "#place-staying-"+max+"-lat";
        var lon= "#place-staying-"+max+"-lon";
        var autocomplete = new google.maps.places.Autocomplete(input,options);

        var marker = new google.maps.Marker({
            map: map,
            anchorPoint: new google.maps.Point(0, -29)
        });
        var em=markers.pop();
        markers.push(marker);
        markers.push(em);
        autocomplete.addListener('place_changed', function()
        {

            marker.setVisible(false);
            var place = autocomplete.getPlace();
            $(local).val(place.id);
            if (!place.geometry) {
                return;
            }
            $(lat).val(place.geometry.location.lat())
            $(lon).val(place.geometry.location.lng())
            // If the place has a geometry, then present it on a map.
                        marker.setIcon(/** @type {google.maps.Icon} */({
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(35, 35)
            }));
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            updateLine();
        });

        max=max+1;
    }


);



