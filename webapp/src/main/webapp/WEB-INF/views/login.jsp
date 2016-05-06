<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tagslib.tld" prefix="my" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

            <a href="?lang=fr"><img src="/speccdb/images/fr.jpg"></a>
            <a href="?lang=en"><img src="/speccdb/images/en.jpg"></a>
        </div>
    </div>
</header>
<section id="main">

	<%-- Début contenu de la page --%>
    <div class="container">
        <h1 id="homeTitle">
            <spring:message code="lbl.title_authentificate"/>
        </h1>

        <%-- Message de confirmations --%>
        <c:if test="${param.containsKey('error')}">
            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span>
                </button>
                <div><spring:message code="lbl.error.wrongAuthentication"/></div>
            </div>
        </c:if>

        <c:if test="${param.containsKey('logout')}">
            <div class="alert alert-success alert-dismissible fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span>
                </button>
                <div><spring:message code="lbl.successLogout"/></div>
            </div>
        </c:if>

        <%-- Formulaire de login--%>
        <form id="loginForm" class="form-horizontal" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="selection" value="">

            <fieldset>
                <div
                        class="form-group ${param.action == 'login' && !loginPage.hasValidUsername() ? 'has-error' : ''}">
                    <label for="username" class="control-label col-sm-2"><spring:message text="Username" code="lbl.username"/></label>
                    <div class="col-sm-10">
                        <input
                                type="text" class="form-control" id="username" name="username" value="${loginPage.username}"
                                required="required">
                    </div>
                    <c:if test="${!loginPage.hasValidUsername()}">
                        <span class="help-block"><spring:message text="Username can't be empty." code="lbl.error.usernameCompulsory"/></span>
                    </c:if>
                </div>

                <div
                        class="form-group ${param.action == 'add' && !loginPage.hasValidName() ? 'has-error' : ''}">
                    <label for="password" class="control-label col-sm-2"><spring:message text="Password" code="lbl.password"/></label>
                    <div class="col-sm-10">
                        <input
                            type="password" class="form-control" id="password" name="password" value="${loginPage.password}"
                            required="required">
                    </div>
                    <c:if test="${!loginPage.hasValidPassword()}">
                        <span class="help-block"><spring:message text="Password can't be empty" code="lbl.error.passwordCompulsory"/></span>
                    </c:if>
                </div>


            </fieldset>

            <div class="actions pull-right">
                <button class="btn btn-primary" type="submit" id="btnSubmit" name="action" value="login">
                    <spring:message text="Login" code="lbl.authentificate"/>
                </button>
            </div>
        </form>
    </div>
</section>

<script src="/speccdb/js/jquery.min.js"></script>
<script src="/speccdb/js/bootstrap.min.js"></script>
<%--<script src="/speccdb/js/login.js"></script>--%>

</body>
</html>