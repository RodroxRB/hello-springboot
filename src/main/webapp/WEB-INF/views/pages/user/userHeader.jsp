<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 10-Feb-17
  Time: 1:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<a href="${requestScope['javax.servlet.forward.request_uri']}?mylocale=en">English </a> | <a href="${requestScope['javax.servlet.forward.request_uri']}?mylocale=es">Espanol </a>
<div class="container">
    <div class="row">
        <img src="${connection.imageUrl}" class="img-circle"/>

    </div>
    <br/>
    <br/>
    <div class="row">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <a href="/user/home" type="button" class="btn btn-nav">
                    <span class="glyphicon glyphicon-home"></span>
                    <p><spring:message code="home"/></p>
                </a>
            </div>
            <div class="btn-group">
                <a href="/user/newtrip" type="button" class="btn btn-nav">
                    <span class="glyphicon glyphicon-plus"></span>
                    <p><spring:message code="new_trip"/></p>
                </a>
            </div>
            <div class="btn-group">
                <a href="/user/suggestedplaces" type="button" class="btn btn-nav">
                    <span class="glyphicon glyphicon-star"></span>
                    <p><spring:message code="suggested"/></p>
                </a>
            </div>
            <div class="btn-group">
                <a href="/user/logout" type="button" class="btn btn-nav">
                    <span class="glyphicon glyphicon-log-out"></span>
                    <p><spring:message code="logout"/></p>
                </a>
            </div>
        </div>
    </div>
</div>
