<%@ page contentType="text/html; charset=UTF-8" language="JAVA" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="content">
    <fmt:message key="home.title" var="title"/>
    <fmt:message key="order.acc_balance" var="acc_balance"/>
    <fmt:message key="order.disc" var="disc"/>
    <fmt:message key="order.order_purchased" var="order_purchased"/>
    <fmt:message key="order.order_expiration" var="order_expiration"/>
    <fmt:message key="order.take_prepayment" var="take_prepayment"/>
    <fmt:message key="order.confirm" var="confirm"/>
    <fmt:message key="clientMain.extendOrder" var="extendOrder"/>
    <fmt:message key="clientMain.chooseExpiration" var="chooseExpiration"/>
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

<%--Main info about order--%>
<div id="order-data">
    <div class="opacity3">
        <div class="left-tek">
            ${acc_balance} : ${requestScope.accountBalance}<br>
            ${disc} : ${requestScope.discount}<br>
            ${order_purchased} : ${requestScope.order.purchaseDate}<br>
            ${order_expiration} : ${requestScope.order.expirationDate}
        </div>
    </div>
</div>


<%--Extend expiration time form--%>
<div class="newOrder">
    <div class="opacity7">
        <div class="left-tek">
            <h3>${chooseExpiration}</h3>
        </div>
    </div>
    <form>
        <input type="date" min="${requestScope.date}" name="extensionDate" required/>
        <input type="hidden" name="command" value="client_extendOrder"/>
        <input type="hidden" name="discount" value="${requestScope.discount}"/>
        <input type="hidden" name="userId" value="${requestScope.userId}"/>
        <input type="hidden" name="accountBalance" value="${requestScope.accountBalance}"/>
        <input type="submit" value="${extendOrder}">
    </form>
    <%--Prepayment message--%>
    <div class="alert-danger">
        ${requestScope.extensionMessage}
    </div>
</div>


<%--Take prepayment form--%>
<div class="prepay">
    <div class="opacity4">
        <div class="left-tek">
            <h3>${take_prepayment}</h3>
        </div>
    </div>
    <form class="pre-form">
        <input type="number" min="1" max="50" name="amount" required/>
        <input type="hidden" name="command" value="client_doPrepayment"/>
        <input type="hidden" name="userId" value="${requestScope.userId}"/>
        <%----%>
        <input name="submit" type="submit" value="${confirm}"/>
    </form>
    <%--Prepayment message--%>
    <div class="alert-danger">
        ${requestScope.prepaymentMessage}
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/pageParts/footer.jsp"/>
</body>
</html>
