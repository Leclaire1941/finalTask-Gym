<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="home.welcome" var="welcome"/>
    <fmt:message key="home.login" var="login"/>
    <fmt:message key="home.password" var="password"/>
    <fmt:message key="home.log_in" var="log_in"/>
</fmt:bundle>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}css/main.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}images/favicon.ico" type="image/x-icon" rel="icon">
    <title>${title}</title>
</head>
<body class="home">

<%--Choose languages --%>
<div id="languages">
    <a href="${pageContext.request.contextPath}/controller?command=common_changeLanguage&locale=ru">RU</a>
    <a href="${pageContext.request.contextPath}/controller?command=common_changeLanguage&locale=en">EN</a>
</div>

<div class="coma">

    <%--Welcome words--%>
    <div id="wel" class="">
        ${welcome}
    </div>

    <%--Login and password form--%>
    <form name="login" method="post" action="${pageContext.request.contextPath}/controller">
        ${login}<br>
        <input type="text" name="login" required/><br>
        ${password}<br>
        <input type="password" name="password" required/><br>

        <%--Submit form--%>
        <input type="hidden" name="command" value="common_login"/>
        <input type="submit" class="btn-primary" value="${log_in}"/><br>

        <%--Error message--%>
        <div class="alert-danger">
            ${requestScope.loginError}
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>

</body>
</html>