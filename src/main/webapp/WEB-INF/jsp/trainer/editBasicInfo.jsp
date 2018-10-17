<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="program.save" var="save"/>
    <fmt:message key="program.selectDiscount" var="selectDiscount"/>
    <fmt:message key="program.selectNutrition" var="selectNutrition"/>
    <fmt:message key="program.selectExpiration" var="selectExpiration"/>
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

<%--Discount form--%>
<div class="basicInfo">
    <div class="left-tek">

        <form name="doEdit">
            <div class="opacity7">
                <h3>${selectDiscount}</h3>
            </div>
            <input type="number" name="discount" min="0" max="30" required style="text-align: center;"/><br>

            <div class="opacity7">
                <h3>${selectNutrition}</h3>
            </div>
            <select name="nutrition">
                <c:forEach items="${requestScope.nutritionList}" var="nutrition">
                    <option>${nutrition}</option>
                </c:forEach>
            </select><br>

            <div class="opacity7">
                <h3>${selectExpiration}</h3>
            </div>
            <input type="date" min="${requestScope.date}" name="expirationDate" required/>
            <br>
            <div id="btn-save2">
                <input type="hidden" name="command" value="trainer_doEditBasicInfo"/>
                <input type="hidden" name="clientId" value="${requestScope.clientId}"/>
                <input type="hidden" name="programId" value="${requestScope.programId}"/>
                <input type="submit" value="${save}"/>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>
