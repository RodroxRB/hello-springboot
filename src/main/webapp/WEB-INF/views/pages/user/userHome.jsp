<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 10-Feb-17
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<br>
<div class="container">
<c:if test="${not empty trips}">
    <div class="col-md-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                <spring:message code="trips"/>
            </div>
            <div class="panel-body">
                <c:forEach var="listValue" items="${trips}">
                    <div class="row">
                    <div class="panel-shower" name="${listValue.id}">
                        <li><a href="#"><span class="glyphicon glyphicon-send"></span> ${listValue.title}</a></li>
                    </div>
                        <button type="button" class="btn btn-default delete-trip" aria-label="Left Align" name="${listValue.id}">
                            <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
                        </button>
                    </div>
                </c:forEach>
            </div>


        </div>
    </div>


    <div class="col-md-9">


    <c:forEach var="listValue" items="${trips}">
        <div class="row">
            <div class="panel panel-default hidable ${listValue.id}">
                <div class="panel-heading">
                        ${listValue.title} - <spring:message code="general_info"/>
                </div>
                <div class="panel-body" id="flights-${listValue.id}">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-default hidable ${listValue.id}">
                <div class="panel-heading">
                        ${listValue.title} - <spring:message code="all_cities"/>
                </div>
                <div class="panel-body" id="all-cities-panel">
                    <div class="timeline-centered">
                        <c:forEach var="placeStaying" items="${listValue.placeStayings}">
                            <div class="row">


                                <time class="timeline-time" datetime="2014-01-10T03:45">
                                    <span>${placeStaying.place.name}</span>
                                    <span>${placeStaying.date_arrival}</span><span>${placeStaying.date_departure}</span>
                                </time>


                                <div class="btn-pref btn-group btn-group-justified btn-group-lg"
                                     role="group"
                                     aria-label="...">
                                    <div class="btn-group" role="group">
                                        <button type="button" class="btn btn-primary"
                                                href="#restaurant-${placeStaying.place.id}"
                                                data-toggle="tab"><span
                                                class="glyphicon glyphicon-menu-hamburger"
                                                aria-hidden="true"></span>
                                            <div class="hidden-xs"><spring:message
                                                    code="restaurants"/></div>
                                        </button>
                                    </div>
                                    <div class="btn-group" role="group">
                                        <button type="button" class="btn btn-default"
                                                href="#hotel-${placeStaying.place.id}"
                                                data-toggle="tab"><span
                                                class="glyphicon glyphicon-home"
                                                aria-hidden="true"></span>
                                            <div class="hidden-xs"><spring:message
                                                    code="hotels"/></div>
                                        </button>
                                    </div>
                                    <div class="btn-group" role="group">
                                        <button type="button" class="btn btn-default"
                                                href="#map-${placeStaying.place.id}"
                                                data-toggle="tab"><span
                                                class="glyphicon glyphicon-map-marker"
                                                aria-hidden="true"></span>
                                            <div class="hidden-xs"><spring:message
                                                    code="maps"/></div>
                                        </button>
                                    </div>
                                </div>

                                <div class="well">
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active"
                                             id="restaurant-${placeStaying.place.id}">
                                            <div class="row" id="restaurants-${placeStaying.place.id}"></div>

                                        </div>
                                        <div class="tab-pane fade in" id="hotel-${placeStaying.place.id}">
                                            <div class="row" id="hotels-${placeStaying.place.id}"></div>
                                        </div>
                                        <div class="tab-pane fade in" id="map-${placeStaying.place.id}">
                                            <div class="row" id="maps-${placeStaying.place.id}"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>


                    </div>


                </div>


            </div>
        </div>

    </c:forEach>


</c:if>
<c:if test="${ empty trips}">

    <div class="row">
        <p class="text-center">
        <h2><spring:message code="no_trips"/></h2>
        </p>
    </div>
    <div class="row">
        <div class="col-lg-2 col-lg-offset-5">
            <p class="text-center">
                <div class="btn-group btn-group-justified">
                    <div class="btn-group">
                        <a href="/user/newtrip" type="button" class="btn btn-nav">
                            <span class="glyphicon glyphicon-plane"></span>
            <p><spring:message code="create_trip"/></p>
            </a>
        </div>
    </div>
    </div>
    </div>
</c:if>

<script src="<c:url value="/static/js/custom/user/userHome.js"/>" charset="UTF-8"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js" charset="UTF-8"></script>
