<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
</fmt:bundle>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/css/main.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" rel="icon">
    <title>${title}</title>
</head>

<body class="ph3">

<jsp:include page="/WEB-INF/jsp/pageParts/header.jsp"/>

<br>


<div class="photos-machine">

    <%--Machines photo--%>
    <div id="machine-item">
        <img src="${pageContext.request.contextPath}/images/machines/treadmill.jpg" width="300" height="200" align="left"
             hspace="10" vspace="10"><br>
    </div>

    <div id="machine-item">
        <img src="${pageContext.request.contextPath}/images/machines/bicycle.jpg" width="300" height="200" align="left"
             hspace="10" vspace="10">
    </div>

    <div id="machine-item">
        <img src="${pageContext.request.contextPath}/images/machines/barbell.jpg" width="300" height="200" align="left"
             hspace="10" vspace="10">
    </div>

    <div id="machine-item">
        <img src="${pageContext.request.contextPath}/images/machines/bars.jpg" width="300" height="200" align="left"
             hspace="10" vspace="10">
    </div>

    <div id="machine-item">
        <img src="${pageContext.request.contextPath}/images/machines/dumbells.jpg" width="300" height="200" align="left"
             hspace="10" vspace="10">
    </div>

</div>

<%--Name and description list--%>
<div class="opacity6">
<c:forEach items="${requestScope.machines}" var="machine">
    <p class="abzac">
            ${machine.name}<br>
            ${machine.description}
    </p>
</c:forEach>


</body>
</html>
