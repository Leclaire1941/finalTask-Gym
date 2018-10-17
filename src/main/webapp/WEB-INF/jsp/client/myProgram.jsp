<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="program.your_trainer" var="your_trainer"/>
    <fmt:message key="program.your_nutrition" var="your_nutrition"/>
    <fmt:message key="program.program_start" var="program_start"/>
    <fmt:message key="program.program_end" var="program_end"/>
    <fmt:message key="program.current_program" var="current_program"/>
    <fmt:message key="clientMain.addComplex" var="addComplex"/>
    <fmt:message key="clientMain.edit" var="edit"/>
    <fmt:message key="program.leave_feedback" var="leave_feedback"/>
    <fmt:message key="program.enter_feedback" var="enter_feedback"/>
    <fmt:message key="program.machine_name" var="machineName"/>
    <fmt:message key="program.difficulty" var="difficulty"/>
    <fmt:message key="program.repeat_counts" var="repeatCounts"/>
    <fmt:message key="order.confirm" var="confirm"/>
    <fmt:message key="clientMain.clear" var="clear"/>
    <fmt:message key="clientMain.confirm" var="confirmClear"/>
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


<%--Program info--%>
<div class="program-info">
    <div class="opacity">
        <div class="left-tek">
            <h3>${your_trainer} - ${requestScope.trainerName}<br>
                ${your_nutrition} - ${requestScope.program.programNutritionName}<br>
                ${program_start} - ${requestScope.program.startDate}<br>
                ${program_end} - ${requestScope.program.endDate}<br>
        </div>
    </div>
</div>

<%--Leave feedback--%>
<div class="feedback">
    <h3>${leave_feedback}</h3>
    <form>
        <textarea rows="4" cols="50" name="feedback" placeholder="${enter_feedback}" class="message"
                  required></textarea>
        <input type="hidden" name="command" value="client_leaveFeedback"/>
        <input type="hidden" name="orderId" value="${requestScope.order.id}"/>
        <input type="hidden" name="userId" value="${requestScope.userId}"/>
        <input name="submit" class="button" type="submit" value="${confirm}"/>
    </form>

    <%--Feedback message--%>
    <div class="alert-danger" style="width: 343px;">
        ${requestScope.addingFeedback}
    </div>

</div>

<%--Current program--%>
<div class="program">
    <div class="opacity2">
        <div class="left-tek">
            <h3>${current_program}</h3>
        </div>
    </div>

    <table border="1">
        <tr>
            <th>${machineName}</th>
            <th>${difficulty}</th>
            <th>${repeatCounts}</th>
        </tr>

        <%--Add complex--%>
        <div class="add_complex">
            <form>
                <input type="hidden" name="command" value="joint_prepareAddComplex"/>
                <input type="hidden" name="userId" value="${requestScope.userId}"/>
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
                    <a class="changeComplex" href="${pageContext.request.contextPath}
                    /controller?command=joint_prepareEditComplex&complexId=${complex.id}&userId=${requestScope.userId}">
                            ${edit}</a>
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
            <input type="hidden" name="userId" value="${requestScope.userId}"/>
            <input type="hidden" name="programId" value="${requestScope.program.id}"/>
            <input name="submit" type="submit" value="${clear}"/>
        </form>
    </div>
</div>


<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>
