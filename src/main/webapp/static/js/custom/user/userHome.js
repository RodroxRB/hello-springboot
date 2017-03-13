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
                configure_restaurants(city_name,restaurants)
                configure_hotels(city_name,hotels)
            }
        }
    }, 'json');

    $.get('/place/flightsAjax.json', function(response) {
        console.log(response);
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
        for (index_hotels = 0, len2 = hotels.length;index_hotels < len2; ++index_hotels) {
            if (len2 > 0)
                $("#hotels-" + city_name).append("<div class='col-md-"+(12/len2)+"'><div class='card'><div class='image-heigth'><img class='card-img-top' src='"+hotels[index_hotels].photo+"' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>"+hotels[index_hotels].name+"</h4><p class='card-text'>"+hotels[index_hotels].vicinity+"</p><p class='card-text'>Rating: "+hotels[index_hotels].rating+"</p></div></div></div></div>");
        }
    }
    else
    {
        $("#hotels-" + city_name).append("There are no hotels for the specified city");
    }
}



$(".timeline-icon").click(function () {
    $(this).closest('.timeline-entry-inner').find('.timeline-label').toggleClass('hidden');
});

$('.main-toggle').change(function() {
    $(this).closest('.panel').find('.panel-body').toggleClass('hidden');
})
