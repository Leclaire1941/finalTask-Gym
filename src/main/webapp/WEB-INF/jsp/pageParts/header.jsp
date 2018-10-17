<%@ page contentType="text/html;charset=UTF-8" language="JAVA" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:bundle basename="content">
    <fmt:message key="clientMain.log_out" var="log_out"/>
    <fmt:message key="clientMain.go_to_main" var="go_to_main"/>
    <fmt:message key="clientMain.my_program" var="my_program"/>
    <fmt:message key="clientMain.order_info" var="order_info"/>
    <fmt:message key="clientMain.help" var="help"/>
    <fmt:message key="trainerMain.goToAllClients" var="goToAllClients"/>
</fmt:bundle>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/css/main.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" rel="icon">
</head>

<body>
<div id="header">
    <div id="logo">
        <img src="${pageContext.request.contextPath}/images/header/logogo_2.png" width="100" height="80">
    </div>
</div>


<c:if test="${sessionScope.user.userRole eq 'CLIENT'}">
    <div class="flex-container">

        <!-- Go to main  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=client_goToMain&userId=${requestScope.userId}">
                ${go_to_main}</a>

        <!-- Show program button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=client_myProgram&userId=${requestScope.userId}">
                ${my_program}</a>

        <!-- Show orderInfo button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=client_orderInfo&userId=${requestScope.userId}">
                ${order_info}</a>

        <!-- Show machines button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=joint_machinesInfo&userId=${requestScope.userId}">
                ${help}</a>

        <!-- Log out button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=common_logOut">${log_out}</a>
    </div>
</c:if>

<c:if test="${sessionScope.user.userRole eq 'TRAINER'}">
    <div class="flex-container">

        <!-- Go to all clients  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=trainer_goToAllClients">${goToAllClients}</a>

        <!-- Show machines button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=joint_machinesInfo">${help}</a>

        <!-- Log out button  -->
        <a class="semi-transparent-button"
           href="${pageContext.request.contextPath}/controller?command=common_logOut">${log_out}</a>

    </div>
</c:if>

<div class="phones">

    <p>18959 E Warren Dr Aurora, CO 80013</p>
    +720-865-2049
</div>

<%--Choose languages --%>
<div class="head_language">
    <a href="${pageContext.request.contextPath}/controller?command=common_changeLanguage&locale=ru">RU</a>
    <a href="${pageContext.request.contextPath}/controller?command=common_changeLanguage&locale=en">EN</a>
</div>

</body>
</html>