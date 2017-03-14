<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 22-Feb-17
  Time: 8:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container">
    </br>
    <div class="row">
        <div id="map">

        </div>
    </div>
    </br>
    <form:form class="form-horizontal" modelAttribute="trip" action="/user/add_trip" id="trip_form">
        <div class="row">

            <div class="col-lg-4 col-lg-offset-4">
                <spring:message code='title' var="title"/>
                <div class="input-group">
                    <form:input cssClass="form-control" path="title"
                                type="text" placeholder="${title}"/><span class="input-group-addon"><i class="glyphicon glyphicon-book"></i></span></div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label><spring:message code="start_city"/></label>
                </div>
                <div class="control-group center-block" id="placeStayings[0].place.id">
                    <form:input cssClass="form-control" path="placeStayings[0].place.id" id="pac-input-start-place_id"
                                class="controls"
                                type="hidden"/>
                    <form:input cssClass="form-control" path="placeStayings[0].place.lat" id="pac-input-start-lat"
                                class="controls"
                                type="hidden"/>
                    <form:input cssClass="form-control" path="placeStayings[0].place.lon" id="pac-input-start-lon"
                                class="controls"
                                type="hidden"/>
                </div>
                <div class="col-md-5">
                    <div class="control-group center-block" id="placeStayings[0].place.name">
                        <div class="input-group"><form:input cssClass="form-control center-block" path="placeStayings[0].place.name"
                                    id="pac-input-start-name"
                                    class="controls" type="text"/><span class="input-group-addon"><i class="glyphicon glyphicon-plane"></i></span></div>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="control-group center-block" id="placeStayings[0].date">
                        <div class="input-group"><form:input cssClass="form-control" path="placeStayings[0].date" id="pac-input-start-date"
                                    class="controls" type="date"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label><spring:message code="end_city"/></label>
                </div>
                <form:input cssClass="form-control" path="placeStayings[1].place.id" id="pac-input-second-place_id"
                            class="controls"
                            type="hidden"/>
                <form:input cssClass="form-control" path="placeStayings[1].place.lat" id="pac-input-second-lat"
                            class="controls"
                            type="hidden"/>
                <form:input cssClass="form-control" path="placeStayings[1].place.lon" id="pac-input-second-lon"
                            class="controls"
                            type="hidden"/>
                <div class="col-md-5">
                    <div class="input-group"><form:input cssClass="form-control" path="placeStayings[1].place.name" id="pac-input-second-name"
                                                         class="controls" type="text"/><span class="input-group-addon"><i class="glyphicon glyphicon-plane"></i></span></div>


                </div>
                <div class="col-md-5">
                    <div class="input-group"><form:input cssClass="form-control" path="placeStayings[1].date" id="pac-input-second-date"
                                                         class="controls" type="date"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>

                </div>

            </div>
        </div>
        <div id="next_cities">

        </div>
        <br>

        <button type="submit" class="btn btn-success"><spring:message code="submit"/></button>
    </form:form>
    <button class="btn btn-info" id="new-city"><spring:message code="new_city"/></button>
    <script src="<c:url value="/static/js/custom/user/userNewTrip.js"/>"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdUOM7XKmfD56v-ROOGe-GmoTLT6LDrOY&libraries=places&callback=initMap&language=<c:out value="${ locale }"/>"
            async defer></script>
</div>
