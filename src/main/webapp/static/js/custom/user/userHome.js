$(document).ready(function() {

    $(".btn-pref .btn").click(function () {
        $(this).closest('.btn-pref').find(".btn").removeClass("btn-primary").addClass("btn-default");
        // $(".tab").addClass("active"); // instead of this do the below
        $(this).removeClass("btn-default").addClass("btn-primary");
    });

    $.get('/place/googleAjax.json', function(response) {
        var index;
        for (index = 0, len = response.length; index < len; ++index) {
            trip_id=response[index].trip_id;
            array_cities=response[index].results;
            var index_cities;
            var len1;
            for (index_cities = 0, len1 = array_cities.length; index_cities < len1; ++index_cities) {
                var city_name= array_cities[index_cities].name;
                var restaurants=array_cities[index_cities].info.restaurants;
                var hotels=array_cities[index_cities].info.hotels;
                var lat=array_cities[index_cities].info.lat;
                var lon=array_cities[index_cities].info.lon;
                configure_restaurants(city_name,restaurants);
                configure_hotels(city_name,hotels);
                configure_maps(city_name,lat,lon);
            }
        }
    }, 'json');

    $.get('/place/flightsAjax.json', function(response) {
        console.log(response.result);
        showFlights(response.result);
    }, 'json');

});


function configure_restaurants(city_name,restaurants)
{

    var index_restaurants;
    var len2= restaurants.length;
    if (len2>0) {
        for (index_restaurants = 0, len2 = restaurants.length;index_restaurants < len2; ++index_restaurants) {
            if (len2 > 0)
                $("#restaurants-" + city_name).append("<div class='col-md-"+(12/len2)+"'><div class='card'><div class='image-heigth'><img class='card-img-top' src='"+restaurants[index_restaurants].photo+"' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>"+restaurants[index_restaurants].name+"</h4><p class='card-text'>"+restaurants[index_restaurants].vicinity+"</p><p class='card-text'>Rating: "+restaurants[index_restaurants].rating+"</p></div></div></div></div>");
        }
    }
    else
    {
        $("#restaurants-" + city_name).append("There are no restaurants for the specified city");
    }
}

function configure_hotels(city_name,hotels)
{

    var index_hotels;
    var len2= hotels.length;
    if (len2>0) {
        for (index_hotels = 0;index_hotels < len2; ++index_hotels) {

            if (len2 > 0)
                $("#hotels-" + city_name).append("<div class='col-md-"+(12/len2)+"'><div class='card'><div class='image-heigth'><img class='card-img-top' src='"+hotels[index_hotels].photo+"' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>"+hotels[index_hotels].name+"</h4><p class='card-text'>"+hotels[index_hotels].vicinity+"</p><p class='card-text'>Rating: "+hotels[index_hotels].rating+"</p></div></div></div></div>");
        }
    }
    else
    {
        $("#hotels-" + city_name).append("There are no hotels for the specified city");
    }
}

function configure_maps(city_name,lat,lon)
{
    $("#maps-" + city_name).append("<img src='https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lon+"&zoom=12&size=400x400&key=AIzaSyCy0EntAPTCPUdHKJX6PqjE33iaapvAyIs'/>");

}

function showFlights(result)
{
    var index_trips;
    for (index_trips=0;index_trips<result.length;index_trips++)
    {
        var all_flights=result[index_trips].flights;
        var trip_id=result[index_trips].trip_id;
        var index_flights;

        for (index_flights=0;index_flights<all_flights.length;index_flights++)
        {
            var flight=all_flights[index_flights];
            var text;
            if (flight.Result=="OK")
            {
                text="<div class='row'><h4>"+flight.startCity+"<span class='glyphicon glyphicon-arrow-right'></span>"+flight.endCity+"</h4></div>";
                text+="<div class='row'><h5>"+flight.origin+"<span class='glyphicon glyphicon-arrow-right'></span>"+flight.destination+"</h5></div><br>";
                text+="<div class='row flight-card'> Company:"+flight.Company+"</div>";
                text+="<div class='row flight-card'> Lowest Fare:"+flight.Fare/2+"</div>";
            }
            else
            {
                text="<div class='row'><h4>"+flight.startCity+"<span class='glyphicon glyphicon-arrow-right'></span>"+flight.endCity+"</h4></div><br>";
                text+="<div class='row flight-card'>"+flight.Result+"</div>";
                text+="<div class='row flight-card'> Origin airports tried:"+flight.origin_airports.filter(function(n){ return n != "\\N" }) +"</div>";
                text+="<div class='row flight-card'> Destination airports tried:"+flight.destination_airports.filter(function(n){ return n != "\\N" })+"</div>";

            }
            $("#flights-" + trip_id).append("<div class='row'><div class='col-md-3'><img src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkENZ3zBblMlTzJN22QEkTeD4xlfd0EsqA7K2k3hn6LzNaIPPBqZHzZWY' /></div><div class='col-md-9 ' >"+text+"</div></div>");

        }
    }
}



$(function () {
    $('.navbar-toggle').click(function () {
        $('.navbar-nav').toggleClass('slide-in');
        $('.side-body').toggleClass('body-slide-in');
        $('#search').removeClass('in').addClass('collapse').slideUp(200);

        /// uncomment code for absolute positioning tweek see top comment in css
        //$('.absolute-wrapper').toggleClass('slide-in');

    });

    // Remove menu for searching
    $('#search-trigger').click(function () {
        $('.navbar-nav').removeClass('slide-in');
        $('.side-body').removeClass('body-slide-in');

        /// uncomment code for absolute positioning tweek see top comment in css
        //$('.absolute-wrapper').removeClass('slide-in');

    });
});

$(".hidable").hide();

$(".panel-shower").click(function(){
    var id=$(this).attr("name");
    console.log(id);
    $(".hidable").hide();
    $("."+id).show();

});

$(".delete-trip").click(
  function()
  {
      var trip_id=$(this).attr("name");
      $.get('/place/deletetrip.json',{trip_id : trip_id}, function(response) {
          console.log(trip_id);
        $("."+trip_id).remove();
      }, 'json');
  }
);
