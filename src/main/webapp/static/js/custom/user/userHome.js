$(document).ready(function() {
    $(".btn-pref .btn").click(function () {
        $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
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
                configure_restaurants(city_name,restaurants)
            }
        }
    }, 'json');

    $.get('/place/flightsAjax.json', function(response) {
        console.log(response);
    }, 'json');

});


function configure_restaurants(city_name,restaurants)
{
    console.log(city_name);
    console.log(restaurants);
    var index_restaurants;
    var len2= restaurants.length;
    if (len2>0) {
        for (index_restaurants = 0, len2 = restaurants.length;index_restaurants < len2; ++index_restaurants) {
            if (len2 > 0)
                $("#restaurants-" + city_name).append("<div class='col-md-"+(12/len2)+"'><div class='row'>"+restaurants[index_restaurants].name+"</div><div class='row'><img src='"+restaurants[index_restaurants].photo+"' id='inside_pictures'/></div> </div>");
        }
    }
    else
    {
        $("#restaurants-" + city_name).append("There are no restaurants for the specified city");
    }
}
/*
$(".timeline-icon").click(function () {
    $(this).closest('.timeline-entry-inner').find('.timeline-label').toggleClass('hidden');
});
*/
$('.main-toggle').change(function() {
    $(this).closest('.panel').find('.panel-body').toggleClass('hidden');
})
