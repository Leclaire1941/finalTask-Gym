<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="CustomTags" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="trainerMain.clients" var="listOfClienst"/>
    <fmt:message key="trainerMain.edit" var="edit"/>
    <fmt:message key="clientMain.all_news" var="all_news"/>
    <fmt:message key="clientMain.all_news2" var="all_news2"/>
</fmt:bundle>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/css/main.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" rel="icon">
    <title>${pageScope.title}</title>
</head>

<body class="ph3">
<jsp:include page="/WEB-INF/jsp/pageParts/header.jsp"/>

<%--Tag--%>
<div class="tag">
    <div class="tag_opacity">
        <h3><ctg:welcome-message userRole="${sessionScope.user.userRole}"/></h3>
    </div>
</div>

<div id="lisa" class="container-fluid">
    <%--Clients label--%>
    <div class="blue">
        <h2>${pageScope.listOfClienst}</h2>
    </div>

    <div class="opacity5">
        <div class="left-tek">
            <c:forEach items="${requestScope.clients}" var="client">
                <p>
                    <a href="${pageContext.request.contextPath}/controller?command=trainer_editClientInfo&clientId=${client.id}">
                            ${client.firstName} ${ client.lastName}</a>
                </p>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Trainer News -->
<div id="trainer_all_news">
    <div class="news_opacity">
        <p style="text-indent:20px;">${pageScope.all_news}</p>
    </div>
</div>
<div id="trainer_all_news2">
    <div class="news2_opacity">
        <p style="text-indent:20px;">${pageScope.all_news2}</p>
    </div>
</div>


<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>