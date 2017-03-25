$(document).ready(function() {

    populate_page();

});

function populate_page()
{
    $.get('/place/randomplace.json', function(response) {
        build_recommended(response);
    }, 'json');
}

function build_recommended(response)
{
    $("#main_title").html(response.city);
    var i=0;
    var recommended_places=response.result;
    console.log=response.result;
    for (i=0;i<recommended_places.length;i=i+3)
    {
        var line="";
        line += "<div class='col-md-4 '><div class='card'><div class='image-heigth'><img class='card-img-top' src='" + recommended_places[i].photo + "' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>" + recommended_places[i].name + "</h4><p class=''>" + recommended_places[i].vicinity + "</p><p class='card-text'>Rating: " + recommended_places[i].rating + "</p></div></div></div>";
        if (i+1<recommended_places.length)
            line += "<div class='col-md-4 '><div class='card'><div class='image-heigth'><img class='card-img-top' src='" + recommended_places[i + 1].photo + "' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>" + recommended_places[i + 1].name + "</h4><p class=''>" + recommended_places[i + 1].vicinity + "</p><p class='card-text'>Rating: " + recommended_places[i + 1].rating + "</p></div></div></div>";
        if (i+2<recommended_places.length)
            line +="<div class='col-md-4 '><div class='card'><div class='image-heigth'><img class='card-img-top' src='"+recommended_places[i+2].photo+"' id='inside_pictures'/></div><div class='card-block'> <h4 class='card-title'>"+recommended_places[i+2].name+"</h4><p class=''>"+recommended_places[i+2].vicinity+"</p><p class='card-text'>Rating: "+recommended_places[i+2].rating+"</p></div></div></div>";

        $("#recommended").append("<div class='row'>"+line+"</div>");
    }
}

