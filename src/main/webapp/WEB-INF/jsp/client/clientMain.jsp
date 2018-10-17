<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="CustomTags" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
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
    <script src="${pageContext.request.contextPath}/js/images.js"></script>

    <title>${title}</title>
</head>

<body class="ph3">
<jsp:include page="/WEB-INF/jsp/pageParts/header.jsp"/>

<%--Tag--%>
<div class="tag">
    <div class="tag_opacity">
        <h3><ctg:welcome-message userRole="${sessionScope.user.userRole}"/></h3>
    </div>
</div>

<!-- Client News -->
<div id="all_news">
    <div class="news_opacity">
        <p style="text-indent:20px;">${all_news}</p>
    </div>
</div>
<div id="all_news2">
    <div class="news2_opacity">
        <p style="text-indent:20px;">${all_news2}</p>
    </div>
</div>

<div class="img1">
    <img src="${pageContext.request.contextPath}/images/fade/fade1.png" id="image_1" width="500" height="400"/>
</div>
<div class="img2">
    <img src="${pageContext.request.contextPath}/images/fade/fade2.png" id="image_2" width="500" height="400"
         style="opacity: 0; filter: alpha(opacity=0);"/>
</div>
<div class="img3">
    <img src="${pageContext.request.contextPath}/images/fade/fade3.png" id="image_3" width="500" height="400"
         style="opacity: 0; filter: alpha(opacity=0);"/>
</div>
<div class="img4">
    <img src="${pageContext.request.contextPath}/images/fade/fade4.png" id="image_4" width="500" height="400"
         style="opacity: 0; filter: alpha(opacity=0);"/>
</div>
<div class="img5">
    <img src="${pageContext.request.contextPath}/images/fade/fade5.png" id="image_5" width="500" height="400"
         style="opacity: 0; filter: alpha(opacity=0);"/>
</div>

<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>

</html>