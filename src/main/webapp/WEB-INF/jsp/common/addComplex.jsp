<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="program.machine_name" var="machineName"/>
    <fmt:message key="program.difficulty" var="difficulty"/>
    <fmt:message key="program.repeat_counts" var="repeatCounts"/>
    <fmt:message key="program.save" var="save"/>
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

<%--Complex edit form--%>
<div class="edit_c2">
    <form name="saveComplex">

        <div class="opacity8">
            <h3>${machineName}</h3>
        </div>

        <select name="machineName">
            <c:forEach items="${requestScope.machines}" var="machine">
                <option>${machine.name}</option>
            </c:forEach>
        </select>
        <br>
        <div class="opacity8">
            <h3>${difficulty}</h3>
        </div>
        <select name="difficulty">
            <c:forEach items="${requestScope.difficultyList}" var="difficulty">
                <option>${difficulty}</option>
            </c:forEach>
        </select>
        <br>
        <div class="opacity8">
            <h3>${repeatCounts}</h3>
        </div>
        <input type="number" name="repeatCounts" required style="text-align:center; width:25%;"/><br>

        <%--Submit form--%>
        <div id="btn-save">
            <input type="hidden" name="programId" value="${requestScope.programId}"/>
            <input type="hidden" name="userId" value="${requestScope.userId}"/>
            <input type="hidden" name="command" value="joint_addComplex"/>
            <input type="submit" value="${save}" style="margin-top: 10%;"/><br>
        </div>
    </form>
</div>


<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>
