<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<jsp:useBean id="computerEditor" scope="request"
             class="com.excilys.mviegas.speccdb.managers.ComputerEditor">
    <jsp:setProperty name="computerEditor" property="*"/>
    <jsp:setProperty name="computerEditor" property="idCompany" param="companyId"/>
</jsp:useBean>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${pageContext.request.method=='POST' && param.action == 'add'}">
        <c:if test="${computerEditor.addComputer()}" var="resultAdd"/>
    </c:when>
</c:choose>

<%-- Idées trovuées sur internet --%>

<%--<c:if test="${param.submitted}">--%>
<%--<c:if test="${empty param.name}" var="noName" />--%>
<%--<c:if test="${empty param.email}" var="noEmail" />--%>
<%--<c:if test="${empty param.age}" var="noAge" />--%>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard.jsp"> Application - Computer Database </a>
    </div>
</header>


<section id="main">
    <div class="container">
        <div class="row">
            <c:if test="${resultAdd}">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
                    </button>
                    <h4>Computer successfully added into Database</h4>
                </div>
            </c:if>
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form action="addComputer.jsp" method="POST">
                    <fieldset>
                        <div class="form-group ${param.action == 'add' && !computerEditor.hasValidName() ? 'has-error' : ''}">
                            <label for="name" class="control-label">Computer name</label>
                            <input type="text" class="form-control" id="name" name="name" placeholder="Computer name"
                                   value="${computerEditor.name}" required="required">
                            <c:if test="${!computerEditor.hasValidName()}">
                                <span class="help-block">Computer's Name is compulsory.</span>
                            </c:if>
                        </div>
                        <div class="form-group ${!computerEditor.hasValidIntroducedDate() ? 'has-error' : ''}">
                            <label for="introducedDate" class="control-label">Introduced date</label>
                            <input type="date" class="form-control" id="introducedDate" name="introducedDate"
                                   placeholder="Introduced date" value="${computerEditor.introducedDate}">
                            <c:if test="${!computerEditor.hasValidIntroducedDate()}">
                                <span class="help-block">Valid format Date : dd/mm/yyyy</span>
                            </c:if>
                        </div>
                        <div class="form-group ${!computerEditor.hasValidDiscontinuedDate() ? 'has-error' : ''}">
                            <label for="discontinuedDate" class="control-label">Discontinued date</label>
                            <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate"
                                   placeholder="Discontinued date" value="${computerEditor.discontinuedDate}">
                            <c:if test="${!computerEditor.hasValidDiscontinuedDate()}">
                                <span class="help-block">Valid format Date : dd/mm/yyyy</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="companyId" class="control-label">Company</label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="0">--</option>

                                <c:forEach items="${computerEditor.companies}" var="company">
                                    <!-- ${computerEditor.idCompany} ${company.id} -->
                                    <option value="${company.id}" ${computerEditor.idCompany eq company.id ? 'selected=\"selected\"' : ''}>${company.name}</option>
                                </c:forEach>

                            </select>
                            <c:if test="${!computerEditor.hasValidIdCompany()}">
                                <span class="help-block">Select an company above.</span>
                            </c:if>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" name="action" value="add" class="btn btn-primary">
                        or
                        <a href="dashboard.jsp" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>