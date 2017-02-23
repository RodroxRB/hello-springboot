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
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label>Starting city</label>
                </div>
                <div class="control-group center-block" id="startPoint.id">
                    <form:input cssClass="form-control" path="startPoint.id" id="pac-input-start-id" class="controls"
                                type="hidden"/>
                        <span class="help-inline"><form:errors path="startPoint.id" cssclass="error"></form:errors></span>
                </div>
                <div class="col-md-5">
                    <div class="control-group center-block" id="startPoint.name">
                        <form:input cssClass="form-control center-block" path="startPoint.name" id="pac-input-start-name"
                                    class="controls" type="text"/>
                        <span class="help-inline"><form:errors path="startPoint.name" cssclass="error"></form:errors></span>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="control-group center-block" id="startPoint.date">
                        <form:input cssClass="form-control" path="startPoint.date" id="pac-input-start-date"
                                    class="controls" type="date"/>
                        <span class="help-inline"><form:errors path="startPoint.date" cssclass="error"></form:errors></span>
                    </div>
                </div>

            </div>
        </div>
        <br>
        <div class="row">
            <div class="form-group">
                <div class="col-md-2 text-left">
                    <label>Finishing city</label>
                </div>
                <form:input cssClass="form-control" path="endPoint.id" id="pac-input-end-id" class="controls"
                            type="hidden"/>
                <div class="col-md-5">
                    <form:input cssClass="form-control" path="endPoint.name" id="pac-input-end-name"
                                class="controls" type="text"/>

                    <span class="help-inline"> <form:errors path="endPoint.name" cssclass="error"></form:errors></span>
                </div>
                <div class="col-md-5">
                    <form:input cssClass="form-control" path="endPoint.date" id="pac-input-end-date"
                                class="controls" type="date"/>

                    <span class="help-inline"> <form:errors path="endPoint.date" cssclass="error"></form:errors></span>
                </div>

            </div>
        </div>
        <div id="middle_cities">

        </div>
        <button type="submit" class="btn btn-success">Submit</button>
    </form:form>
    <button class="btn btn-info" id="new-city">Add new city</button>
    <script src="<c:url value="/static/js/custom/user/index.js"/>"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdUOM7XKmfD56v-ROOGe-GmoTLT6LDrOY&libraries=places&callback=initMap"
            async defer></script>
</div>
