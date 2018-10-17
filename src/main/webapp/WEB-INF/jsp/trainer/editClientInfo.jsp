<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="program.program_start" var="program_start"/>
    <fmt:message key="program.program_end" var="program_end"/>
    <fmt:message key="clientMain.addComplex" var="addComplex"/>
    <fmt:message key="clientMain.edit" var="edit"/>
    <fmt:message key="trainerMain.edit" var="edit"/>
    <fmt:message key="trainerMain.discount" var="discount"/>
    <fmt:message key="trainerMain.nutrition" var="nutrition"/>
    <fmt:message key="trainerMain.listOfComplexes" var="listOfComplexes"/>
    <fmt:message key="trainerMain.machineLable" var="machineLable"/>
    <fmt:message key="trainerMain.difficultyLable" var="difficultyLable"/>
    <fmt:message key="trainerMain.repeatCountsLable" var="repeatCountsLable"/>
    <fmt:message key="clientMain.clear" var="clear"/>

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

<%--Information about--%>
<div class="edit_1">
    <div class="left-tek">
        <div class="opacity7">
            <div class="tex">
                <h1>${requestScope.client.firstName} ${requestScope.client.lastName}</h1>
                <h4>${discount} - ${requestScope.client.discount}</h4>
                <h4>${program_start} - ${requestScope.program.startDate}</h4>
                <h4>${program_end} - ${requestScope.program.endDate}</h4>
                <h4>${nutrition} - ${requestScope.program.programNutritionName}</h4>
            </div>
            <form name="editBasicInfo" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="trainer_editBasicInfo"/>
                <input type="hidden" name="clientId" value="${requestScope.client.id}"/>
                <input type="hidden" name="programId" value="${requestScope.program.id}"/>
                <input type="submit" class="save-btn" value="${edit}">

                <%--Save basic info message--%>
                <div class="alert-danger" style="text-align:  center;width: 100%;">
                    ${requestScope.saveBasicMessage}
                </div>
            </form>
        </div>
    </div>
</div>

<%--List of complexes (table)--%>
<div class="edit_c">
    <div class="opacity2">
        <div class="left-tek">
            <h3>${listOfComplexes}:</h3>
        </div>
    </div>

    <table border="1">
        <tr>
            <th>${machineLable}</th>
            <th>${difficultyLable}</th>
            <th>${repeatCountsLable}</th>
        </tr>

        <%--Add complex--%>
        <div class="add_complex">
            <form>
                <input type="hidden" name="command" value="joint_prepareAddComplex"/>
                <input type="hidden" name="userId" value="${requestScope.clientId}"/>
                <input type="hidden" name="programId" value="${requestScope.program.id}"/>
                <input name="submit" type="submit" value="${addComplex}"/>
            </form>
        </div>

        <c:forEach items="${requestScope.complexes}" var="complex">
            <tr>
                <td>${complex.machineName}</td>
                <td>${complex.complexDifficulty}</td>
                <td>${complex.repeatCounts}</td>
                <td>
                    <a class="editComplexForTrainer" href="${pageContext.request.contextPath}
                    /controller?command=joint_prepareEditComplex&complexId=${complex.id}&clientId=${requestScope.client.id}">${edit}</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%--Save compex message--%>
    <div class="alert-danger" style="text-align:  center;width: 540px;">
        ${requestScope.saveMessage}
    </div>

    <%--Delete all--%>
    <div id="clear_all">
        <form>
            <input type="hidden" name="command" value="joint_clearComplexes"/>
            <input type="hidden" name="userId" value="${requestScope.clientId}"/>
            <input type="hidden" name="programId" value="${requestScope.program.id}"/>
            <input name="submit" type="submit" value="${clear}"/>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>