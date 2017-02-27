<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 10-Feb-17
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
    <c:if test="${not empty trips}">


            <c:forEach var="listValue" items="${trips}">

                    <div class="card hovercard">
                        <div class="card-background">
                            <img class="card-bkimg" alt="" src="http://www.imaginationsound.com/images/sky-image-left.png">
                            <!-- http://lorempixel.com/850/280/people/9/ -->
                        </div>
                        <div class="useravatar">
                            <img alt="" src="http://www.freeiconspng.com/uploads/travel-icon-png-3.png">
                        </div>
                        <div class="card-info"> <span class="card-title">${listValue.title}</span>

                        </div>
                    </div>
                    <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
                        <div class="btn-group" role="group">
                            <button type="button" id="stars" class="btn btn-primary" href="#restaurants" data-toggle="tab"><span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
                                <div class="hidden-xs">Restaurants</div>
                            </button>
                        </div>
                        <div class="btn-group" role="group">
                            <button type="button" id="favorites" class="btn btn-default" href="#tab2" data-toggle="tab"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
                                <div class="hidden-xs">Favorites</div>
                            </button>
                        </div>
                        <div class="btn-group" role="group">
                            <button type="button" id="following" class="btn btn-default" href="#tab3" data-toggle="tab"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                <div class="hidden-xs">Following</div>
                            </button>
                        </div>
                    </div>

                    <div class="well">
                        <div class="tab-content">
                            <div class="tab-pane fade in active">
                                <div class="row" id="restaurants-${listValue.startPoint.id}"></div>
                                <c:forEach var="point" items="${listValue.placeStayings}">
                                    <div class="row" id="restaurants-${point.id}"></div>
                                </c:forEach>
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
                </p>
            </div>
        </div>

    </c:if>
</div>
<script src="<c:url value="/static/js/custom/user/userHome.js"/>"></script>
