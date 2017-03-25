<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:getAsString name="title" /></title>
    <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
    <link href="<c:url value='/static/css/custom/index.css' />"  rel="stylesheet"></link>
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"  charset="UTF-8"></script>
    <script src="<c:url value="/static/js/bootstrap.js"/>"  charset="UTF-8"></script>
    <script src="<c:url value="/static/js/notify.js"/>"  charset="UTF-8"></script>
    <tiles:insertAttribute name="head" />
</head>


<body>
<section id="header">
    <tiles:insertAttribute name="header" />
</section>

<section id="site-content">
    <tiles:insertAttribute name="body" />
</section>

<footer id="footer">
    <tiles:insertAttribute name="footer" />
</footer>
</body>
</html>
