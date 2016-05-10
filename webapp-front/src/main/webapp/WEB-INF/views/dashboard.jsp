<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tagslib.tld" prefix="my" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="dashboardManager" type="com.excilys.mviegas.speccdb.managers.DashboardPage"--%>

<html lang="${pageContext.response.locale}">
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="/speccdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/speccdb/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="/speccdb/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard.html"><spring:message code="lbl.title"/></a>

        <div class="navbar-right">
            <form id="logoutForm" class="form-inline" method="POST" action="/speccdb/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn" type="submit" id="btnLogout" name="action" value="logout">
                    <spring:message text="Logout" code="lbl.logout"/>
                </button>
            </form>

            <a href="?lang=fr"><img src="/speccdb/images/fr.jpg"></a>
            <a href="?lang=en"><img src="/speccdb/images/en.jpg"></a>
        </div>
    </div>
</header>
<section id="main">
    
    <%-- Message de confirmations --%>
    <c:if test="${computerAdded || param.computerAdded}">
        <c:remove var="computerAdded" scope="session"/>
        <div class="alert alert-success alert-dismissible fade in" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">×</span>
            </button>
            <h4>Computer successfully added into Database</h4>
        </div>
    </c:if>
    
    <c:if test="${computerEdited}">
        <c:remove var="computerEdited" scope="session"/>
        <div class="alert alert-success alert-dismissible fade in" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">×</span>
            </button>
            <h4><spring:message code="lbl.modification_successful"/></h4>
        </div>
    </c:if>
    
    <c:if test="${not empty deleteSuccessful}">
        <c:choose>

            <c:when test="${deleteSuccessful}">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">×</span>
                    </button>
                    <h4><spring:message code="lbl.deleted_computers"/></h4>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">×</span>
                    </button>
                    <h4><spring:message code="lbl.error_deletion"/></h4>
                </div>
            </c:otherwise>
        </c:choose>

        <c:remove var="deleteSuccessful"/>
    </c:if>

    <%--<c:forEach items="${dashboardManager.messages}" var="message">--%>
        <%--<div class="alert alert-warning alert-dismissible fade in" role="alert">--%>
            <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span--%>
                    <%--aria-hidden="true">×</span>--%>
            <%--</button>--%>
            <%--<h4>${message.title}</h4>--%>
            <%--<div>${message.description}</div>--%>
        <%--</div>--%>
    <%--</c:forEach>--%>

	<%-- Début contenu de la page --%>
    <div class="container">
        <h1 id="homeTitle">
            <spring:message arguments="${dashboardManager.paginator.elementsCount}" code="lbl.computers_found"/>
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="${my:link('dashboard')}" method="GET" class="form-inline">
                    <c:if test="${not empty param.size}">
                        <input type="hidden" name="size" value="${param.size}">
                    </c:if>
                    <jsp:element name="input">
                        <jsp:attribute name="type">search</jsp:attribute>
                        <jsp:attribute name="id">searchbox</jsp:attribute>
                        <jsp:attribute name="name">search</jsp:attribute>
                        <jsp:attribute name="class">form-control</jsp:attribute>
                        <jsp:attribute name="placeholder"><spring:message code="lbl.help_search"/></jsp:attribute>
                        <jsp:attribute name="value">${param.search}</jsp:attribute>
                    </jsp:element>
                    <jsp:element name="input">
                        <jsp:attribute name="type">submit</jsp:attribute>
                        <jsp:attribute name="id">searchsubmit</jsp:attribute>
                        <jsp:attribute name="value"><spring:message code="lbl.filter_lbl"/></jsp:attribute>
                        <jsp:attribute name="class">btn btn-primary</jsp:attribute>
                    </jsp:element>

                </form>
            </div>
            <security:authorize access="hasRole('ADMIN')">
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer.html"><spring:message code="lbl.add_computer"/></a> <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="lbl.edit"/></a>
                </div>
            </security:authorize>
        </div>
    </div>

    <form id="deleteForm" method="POST">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th class="editMode" style="width: 60px; height: 22px;"><input
                        type="checkbox" id="selectall"/> <span
                        style="vertical-align: top;"> - <a href="#"
                                                           id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
                        class="fa fa-trash-o fa-lg"></i>
                </a>
						</span></th>
                <th><a href="${my:linkSort('dashboard', param, 'name', (dashboardManager.order == 'name' && dashboardManager.typeOrder != 'DESC' ? 'DESC' : null))}"><c:if test="${dashboardManager.order == 'name'}"><span class="glyphicon glyphicon-chevron-${dashboardManager.typeOrder == 'DESC' ? 'down': 'up'}" aria-hidden="true"></span></c:if> <spring:message code="lbl.computer_name"/> <span class="glyphicon glyphicon-sort-by-attributes${dashboardManager.order == 'name' && dashboardManager.typeOrder != 'DESC' ? '-alt': ''}" aria-hidden="true"></span></a></th>
                <th><a href="${my:linkSort('dashboard', param, 'introduced', (dashboardManager.order == 'introduced' && dashboardManager.typeOrder != 'DESC' ? 'DESC' : null))}"><c:if test="${dashboardManager.order == 'introduced'}"><span class="glyphicon glyphicon-chevron-${dashboardManager.typeOrder == 'DESC' ? 'down': 'up'}" aria-hidden="true"></span></c:if> <spring:message code="lbl.introduced_name"/> <span class="glyphicon glyphicon-sort-by-attributes${dashboardManager.order == 'introduced' && dashboardManager.typeOrder != 'DESC' ? '-alt': ''}" aria-hidden="true"></span></a></th>
                <th><a href="${my:linkSort('dashboard', param, 'discontinued', (dashboardManager.order == 'discontinued' && dashboardManager.typeOrder != 'DESC' ? 'DESC' : null))}"><c:if test="${dashboardManager.order == 'discontinued'}"><span class="glyphicon glyphicon-chevron-${dashboardManager.typeOrder == 'DESC' ? 'down': 'up'}" aria-hidden="true"></span></c:if> <spring:message code="lbl.discontinued_name"/> <span class="glyphicon glyphicon-sort-by-attributes${dashboardManager.order == 'discontinued' && dashboardManager.typeOrder != 'DESC' ? '-alt': ''}" aria-hidden="true"></span></a></th>
                <th><a href="${my:linkSort('dashboard', param, 'company_name', (dashboardManager.order == 'company_name' && dashboardManager.typeOrder != 'DESC' ? 'DESC' : null))}"><c:if test="${dashboardManager.order == 'company_name'}"><span class="glyphicon glyphicon-chevron-${dashboardManager.typeOrder == 'DESC' ? 'down': 'up'}" aria-hidden="true"></span></c:if> <spring:message code="lbl.company_name"/> <span class="glyphicon glyphicon-sort-by-attributes${dashboardManager.order == 'company_name' && dashboardManager.typeOrder != 'DESC' ? '-alt': ''}" aria-hidden="true"></span></a></th>
            </tr>
            </thead>

            <tbody id="results">

            <c:forEach items="${dashboardManager.paginator.values}" var="computer">
                <tr>
                    <td class="editMode"><input id="${computer.name}_id" type="checkbox" name="cb" class="cb" value="${computer.id}"></td>
                    <td>
                        <security:authorize access="hasRole('DEFAULT') && !hasRole('ADMIN')">
                            <c:out value="${computer.name}"/>
                        </security:authorize>

                        <%-- TODO revoir efficacité de cette partie --%>
                        <security:authorize access="hasRole('ADMIN')">
                            <jsp:element name="a">
                                <jsp:attribute name="href">
                                    editComputer.html?id=${computer.id}
                                </jsp:attribute>
                                <jsp:attribute name="id">
                                    ${computer.name}_name
                                </jsp:attribute>
                                <jsp:body>
                                    <c:out value="${computer.name}"/>
                                </jsp:body>
                            </jsp:element>
                        </security:authorize>
                    </td>
                    <td><c:out value="${computer.introducedDate}"/></td>
                    <td><c:out value="${computer.discontinuedDate}"/></td>
                    <td><c:out value="${computer.manufacturer.name}"/></td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <my:pagination paginator="${dashboardManager.paginator}"
                       parameters="${param}"/>

        <div class="btn-group btn-group-sm pull-right" role="group">
            <a type="button" class="btn btn-default ${param.size == 10 || empty param.size ? 'active' : ''}"
               href="${my:linkQE('dashboard', param, 'size', null)}">10</a>
            <a type="button" class="btn btn-default ${param.size == 50 ? 'active' : ''}" href="${my:linkQE('dashboard', param, 'size', 50)}">50</a>
            <a type="button" class="btn btn-default ${param.size == 100 ? 'active' : ''}" href="${my:linkQE('dashboard', param, 'size', 100)}">100</a>
        </div>
    </div>
</footer>
<script src="/speccdb/js/jquery.min.js"></script>
<script src="/speccdb/js/bootstrap.min.js"></script>
<script src="/speccdb/js/jquery.i18n.properties.min.js"></script>
<script src="/speccdb/js/dashboard.js"></script>

</body>
</html>