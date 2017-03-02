<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 10-Feb-17
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
<c:if test="${not empty trips}">


    <c:forEach var="listValue" items="${trips}">
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">
                ${listValue.title}
                <input type="checkbox" class="main-toggle" data-toggle="toggle" data-on="General data" data-off="All cities">
            </div>
            <div class="panel-body" id="all-cities-panel">
            <div class="timeline-centered">
                <div class="row">
                <article class="timeline-entry">

                    <div class="timeline-entry-inner">
                        <time class="timeline-time" datetime="2014-01-10T03:45"><span>${listValue.startPoint.name}</span> <span>${listValue.startPoint.date}</span>
                        </time>
                        <div class="timeline-icon bg-info">
                            <i class="entypo-feather"></i>
                        </div>

                        <div class="timeline-label">

                            <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group"
                                 aria-label="...">
                                <div class="btn-group" role="group">
                                    <button type="button" id="stars" class="btn btn-primary" href="#restaurant-${listValue.startPoint.id}"
                                            data-toggle="tab"><span class="glyphicon glyphicon-menu-hamburger"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Restaurants</div>
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" id="favorites" class="btn btn-default" href="#tab2"
                                            data-toggle="tab"><span class="glyphicon glyphicon-heart"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Favorites</div>
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" id="following" class="btn btn-default" href="#tab3"
                                            data-toggle="tab"><span class="glyphicon glyphicon-user"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Following</div>
                                    </button>
                                </div>
                            </div>

                            <div class="well">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="restaurant-${listValue.startPoint.id}">
                                        <div class="row" id="restaurants-${listValue.startPoint.id}"></div>

                                    </div>
                                    <div class="tab-pane fade in" id="tab2">
                                        <h3>This is tab 2</h3>
                                    </div>
                                    <div class="tab-pane fade in" id="tab3">
                                        <h3>This is tab 3</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </article>
                </div>
                <c:forEach var="place" items="${listValue.placeStayings}">
                    <div class="row">
                    <article class="timeline-entry">

                        <div class="timeline-entry-inner">
                            <time class="timeline-time" datetime="2014-01-10T03:45"><span>${place.name}</span>
                                <span>${place.date}</span></time>
                            <div class="timeline-icon bg-info">
                                <i class="entypo-feather"></i>
                            </div>

                            <div class="timeline-label">

                                <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group"
                                     aria-label="...">
                                    <div class="btn-group" role="group">
                                        <button type="button" id="stars" class="btn btn-primary" href="#restaurant-${place.id}"
                                                data-toggle="tab"><span class="glyphicon glyphicon-menu-hamburger"
                                                                        aria-hidden="true"></span>
                                            <div class="hidden-xs">Restaurants</div>
                                        </button>
                                    </div>
                                    <div class="btn-group" role="group">
                                        <button type="button" id="favorites" class="btn btn-default" href="#tab2"
                                                data-toggle="tab"><span class="glyphicon glyphicon-heart"
                                                                        aria-hidden="true"></span>
                                            <div class="hidden-xs">Favorites</div>
                                        </button>
                                    </div>
                                    <div class="btn-group" role="group">
                                        <button type="button" id="following" class="btn btn-default" href="#tab3"
                                                data-toggle="tab"><span class="glyphicon glyphicon-user"
                                                                        aria-hidden="true"></span>
                                            <div class="hidden-xs">Following</div>
                                        </button>
                                    </div>
                                </div>

                                <div class="well">
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active" id="restaurant-${place.id}">
                                            <div class="row" id="restaurants-${place.id}"></div>

                                        </div>
                                        <div class="tab-pane fade in" id="tab2">
                                            <h3>This is tab 2</h3>
                                        </div>
                                        <div class="tab-pane fade in" id="tab3">
                                            <h3>This is tab 3</h3>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </article>
                    </div>
                </c:forEach>
                <div class="row">
                <article class="timeline-entry">

                    <div class="timeline-entry-inner">
                        <time class="timeline-time" datetime="2014-01-10T03:45"><span>${listValue.endPoint.name}</span> <span>${listValue.endPoint.date}</span>
                        </time>
                        <div class="timeline-icon bg-info">
                            <i class="entypo-feather"></i>
                        </div>

                        <div class="timeline-label">

                            <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group"
                                 aria-label="...">
                                <div class="btn-group" role="group">
                                    <button type="button" id="stars" class="btn btn-primary" href="#restaurant-${listValue.endPoint.id}"
                                            data-toggle="tab"><span class="glyphicon glyphicon-menu-hamburger"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Restaurants</div>
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" id="favorites" class="btn btn-default" href="#tab2"
                                            data-toggle="tab"><span class="glyphicon glyphicon-heart"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Favorites</div>
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" id="following" class="btn btn-default" href="#tab3"
                                            data-toggle="tab"><span class="glyphicon glyphicon-user"
                                                                    aria-hidden="true"></span>
                                        <div class="hidden-xs">Following</div>
                                    </button>
                                </div>
                            </div>

                            <div class="well">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="restaurant-${listValue.endPoint.id}">
                                        <div class="row" id="restaurants-${listValue.endPoint.id}"></div>

                                    </div>
                                    <div class="tab-pane fade in" id="tab2">
                                        <h3>This is tab 2</h3>
                                    </div>
                                    <div class="tab-pane fade in" id="tab3">
                                        <h3>This is tab 3</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </article>
                </div>

            </div>


            </div>
            <div class="panel-body" id="general-panel" hidden>
            </div>
        </div>
    </c:forEach>


</c:if>
<c:if test="${ empty trips}">

    <div class="row">
        <p class="text-center">
        <h2>You haven't created any trips</h2>
        </p>
    </div>
    <div class="row">
        <div class="col-lg-2 col-lg-offset-5">
            <p class="text-center">
                <div class="btn-group btn-group-justified">
                    <div class="btn-group">
                        <a href="/user/newtrip" type="button" class="btn btn-nav">
                            <span class="glyphicon glyphicon-plane"></span>
            <p>Create new trip</p>
            </a>
        </div>
    </div>

</c:if>
</div>
<script src="<c:url value="/static/js/custom/user/userHome.js"/>"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
