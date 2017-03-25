var max=2;
var map;
var options = {
    types: ['(cities)']
};

function collectFormData(fields) {
    var data = {};
    for (var i = 0; i < fields.length; i++) {
        var $item = $(fields[i]);
        data[$item.attr('name')] = $item.val();
    }
    return data;
}

function updateDisabled()
{
    $("input[id^='pac-input-dep']").attr("disabled",false);
    $("input[id^='pac-input-dep']").last().attr("disabled",true);
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
                console.log(response);
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

    var input_second = /** @type {!HTMLInputElement} */(
        document.getElementById('pac-input-second-name'));


    var autocomplete_second = new google.maps.places.Autocomplete(input_second,options);



    var marker = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    var marker_second = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    autocomplete.addListener('place_changed', function()
    {

        marker.setVisible(false);
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            $("#pac-input-start-place_id").val("");
            return;
        }
        $("#pac-input-start-place_id").val(place.id);

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
    });
    autocomplete_second.addListener('place_changed', function()
    {
        marker_second.setVisible(false);
        var place = autocomplete_second.getPlace();
        if (!place.geometry) {
            $("#pac-input-second-place_id").val("");
            return;
        }
        $("#pac-input-second-place_id").val(place.id);

        $("#pac-input-second-lat").val(place.geometry.location.lat())
        $("#pac-input-second-lon").val(place.geometry.location.lng())

        marker_second.setIcon(/** @type {google.maps.Icon} */({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker_second.setPosition(place.geometry.location);
        marker_second.setVisible(true);
    });




}

//Add new city to the route

$("#new-city").click(
    function()
    {
        $("#next_cities").append(
            "<div class='row'><div class='form-group'><div class='col-md-2 text-left'></div><input id='place-staying-"+max+"-place_id' name='placeStayings["+max+"].place.id' class='form-control' type='hidden'><input id='place-staying-"+max+"-lat' name='placeStayings["+max+"].place.lat' class='form-control' type='hidden'><input id='place-staying-"+max+"-lon' name='placeStayings["+max+"].place.lon' class='form-control' type='hidden'><div class='col-md-3'><div class='input-group'><input id='place-staying-"+max+"-name' name='placeStayings["+max+"].place.name' class='form-control' type='text'><span class='input-group-addon'><i class='glyphicon glyphicon-plane'></i></span></div></div><div class='col-md-3'><div class='input-group'><input id='place-staying-"+max+"-date_arrival' name='placeStayings["+max+"].date_arrival' class='form-control' type='date'><span class='input-group-addon'><i class='glyphicon glyphicon-calendar'></i></span></div></div><div class='col-md-3'><div class='input-group'><input id='pac-input-dep-staying-"+max+"-date_departure' name='placeStayings["+max+"].date_departure' class='form-control' type='date'><span class='input-group-addon'><i class='glyphicon glyphicon-calendar'></i></span></div></div><div class='col-md-1'><button type='button' class='btn btn-danger delete-row'><span class='glyphicon glyphicon-trash'></span></button></div></div></div>"
                );
        $(".delete-row").click(
            function () {
                $(this).closest(".row").remove();
                updateDisabled();
            }
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
        autocomplete.addListener('place_changed', function()
        {

            marker.setVisible(false);
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                $(local).val("");
                return;
            }
            $(local).val(place.id);

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
        });

        max=max+1;
        updateDisabled()
    }



);

$("input[type=text]").keyup(
    function()
    {
        $(this).closest(".form-group").children(".control-group").find("input").first().val("");

    }
);





