<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 22-Feb-17
  Time: 8:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div class="input-group">
                    <form:input cssClass="form-control" path="title"
                                type="text" placeholder="Title"/><span class="input-group-addon"><i class="glyphicon glyphicon-book"></i></span></div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label>Starting city</label>
                </div>
                <div class="control-group center-block" id="startPoint.id">
                    <form:input cssClass="form-control" path="startPoint.place_id" id="pac-input-start-place_id"
                                class="controls"
                                type="hidden"/>
                    <span class="help-inline"><form:errors path="startPoint.place_id"
                                                           cssclass="error"></form:errors></span>
                    <form:input cssClass="form-control" path="startPoint.lat" id="pac-input-start-lat"
                                class="controls"
                                type="hidden"/>
                    <form:input cssClass="form-control" path="startPoint.lon" id="pac-input-start-lon"
                                class="controls"
                                type="hidden"/>
                </div>
                <div class="col-md-5">
                    <div class="control-group center-block" id="startPoint.name">
                        <div class="input-group"><form:input cssClass="form-control center-block" path="startPoint.name"
                                    id="pac-input-start-name"
                                    class="controls" type="text"/><span class="input-group-addon"><i class="glyphicon glyphicon-plane"></i></span></div>
                        <span class="help-inline"><form:errors path="startPoint.name"
                                                               cssclass="error"></form:errors></span>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="control-group center-block" id="startPoint.date">
                        <div class="input-group"><form:input cssClass="form-control" path="startPoint.date" id="pac-input-start-date"
                                    class="controls" type="date"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>
                        <span class="help-inline"><form:errors path="startPoint.date"
                                                               cssclass="error"></form:errors></span>
                    </div>
                </div>

            </div>
        </div>


        <div id="middle_cities">

        </div>
        <br>
        <div class="row">
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label>Finishing city</label>
                </div>
                <form:input cssClass="form-control" path="endPoint.place_id" id="pac-input-end-place_id"
                            class="controls"
                            type="hidden"/>
                <form:input cssClass="form-control" path="endPoint.lat" id="pac-input-end-lat"
                            class="controls"
                            type="hidden"/>
                <form:input cssClass="form-control" path="endPoint.lon" id="pac-input-end-lon"
                            class="controls"
                            type="hidden"/>
                <div class="col-md-5">
                    <div class="input-group"><form:input cssClass="form-control" path="endPoint.name" id="pac-input-end-name"
                                class="controls" type="text"/><span class="input-group-addon"><i class="glyphicon glyphicon-plane"></i></span></div>

                    <span class="help-inline"> <form:errors path="endPoint.name" cssclass="error"></form:errors></span>
                </div>
                <div class="col-md-5">
                    <div class="input-group"><form:input cssClass="form-control" path="endPoint.date" id="pac-input-end-date"
                                class="controls" type="date"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>

                    <span class="help-inline"> <form:errors path="endPoint.date" cssclass="error"></form:errors></span>
                </div>

            </div>
        </div>
        <button type="submit" class="btn btn-success">Submit</button>
    </form:form>
    <button class="btn btn-info" id="new-city">Add new city</button>
    <script src="<c:url value="/static/js/custom/user/userNewTrip.js"/>"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdUOM7XKmfD56v-ROOGe-GmoTLT6LDrOY&libraries=places&callback=initMap"
            async defer></script>
</div>
