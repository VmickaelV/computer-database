<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE html>
<%--@elvariable id="computerEditor" type="com.excilys.mviegas.speccdb.managers.ComputerEditorPage"--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html lang="${pageContext.response.locale}">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/speccdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/speccdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/speccdb/css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="/speccdb/css/jquery-ui.structure.min.css" rel="stylesheet" media="screen">
<link href="/speccdb/css/jquery-ui.theme.min.css" rel="stylesheet" media="screen">
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
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:choose>

						<c:when test="${not empty computerEditor.computer}">
							<div class="label label-default pull-right">
	                        	id: ${computerEditor.computer.id}
                    		</div>
                    		<h1><spring:message text="Edit Computer" code="lbl.editComputer"/></h1>
                    	</c:when>
                    	<c:otherwise>
                    		<h1><spring:message text="Add Computer" code="lbl.addComputer"/></h1>
                    	</c:otherwise>
					</c:choose>
					
					<form action="${not empty computerEditor.computer ? 'editComputer.html?id=' : 'addComputer.html'}${not empty computerEditor.computer ? computerEditor.computer.id : ''}" method="POST">

                        <input id="id" type="hidden" value="${computerEditor.id}"/>
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>

						<fieldset>
							<div
								class="form-group ${param.action == 'add' && !computerEditor.hasValidName() ? 'has-error' : ''}">
								<label for="name" class="control-label"><spring:message text="Computer Name" code="lbl.computer_name"/></label>
                                <input
									type="text" class="form-control" id="name" name="name"
									placeholder="Computer name" value="${computerEditor.name}"
									required="required">
								<c:if test="${!computerEditor.hasValidName()}">
									<span class="help-block"><spring:message text="Computer's Name is compulsory." code="lbl.error.computerNameCompulsory"/></span>
								</c:if>
							</div>
							<div
								class="form-group ${!computerEditor.hasValidIntroducedDate() ? 'has-error' : ''}">
								<label for="introducedDate" class="control-label"><spring:message text="Introduced date" code="lbl.introduced_name"/></label>
                                <input type="date" class="form-control" id="introducedDate" name="introducedDate" placeholder="Introduced date" value="${computerEditor.introducedDate}" />
								<c:if test="${!computerEditor.hasValidIntroducedDate()}">
									<span class="help-block"><spring:message text="Valid format Date : dd/mm/yyyy" code="lbl.help.validDate"/></span>
								</c:if>
							</div>
							<div
								class="form-group ${!computerEditor.hasValidDiscontinuedDate() ? 'has-error' : ''}">
								<label for="discontinuedDate" class="control-label"><spring:message text="Discontinued date" code="lbl.discontinued_name"/></label>
                                <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate" placeholder="Discontinued date" value="${computerEditor.discontinuedDate}">
								<c:if test="${!computerEditor.hasValidDiscontinuedDate()}">
									<span class="help-block"><spring:message text="Valid format Date : dd/mm/yyyy" code="lbl.help.validDate"/></span>
								</c:if>
							</div>
							<div class="form-group">
								<label for="idCompany" class="control-label"><spring:message text="Company" code="lbl.company_name"/></label> <select
									class="form-control" id="idCompany" name="idCompany">
									<option value="0">--</option>

									<c:forEach items="${listOfCompanies}" var="company">
										<!-- ${computerEditor.idCompany} ${company.id} -->
										<option value="${company.id}"
											${computerEditor.idCompany eq company.id ? 'selected=\"selected\"' : ''}>${company.name}</option>
									</c:forEach>

								</select>
								<c:if test="${!computerEditor.hasValidIdCompany()}">
									<span class="help-block"><spring:message text="Select an company above." code="lbl.selectionCompany"/></span>
								</c:if>
							</div>
						</fieldset>
						<div class="actions pull-right">
                            <button class="btn btn-primary" type="submit" id="btnSubmit" name="action" value="${computerEditor.isEditing() ? 'Edit' : 'Add'}">
                                <c:if test="${computerEditor.isEditing()}"><spring:message text="Add" code="lbl.add"/></c:if>
                                <c:if test="${!computerEditor.isEditing()}"><spring:message text="Edit" code="lbl.edit"/></c:if>
                            </button>
                            <spring:message text="or" code="word.or"/> <a href="dashboard.html"
								class="btn btn-default"><spring:message text="Cancel" code="lbl.cancel"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="/speccdb/js/jquery.min.js"></script>
	<script src="/speccdb/js/jquery.validate.min.js"></script>
	<script src="/speccdb/js/bootstrap.min.js"></script>
	<script src="/speccdb/js/jquery-ui.min.js"></script>
	<script src="/speccdb/js/localization/messages_${pageContext.response.locale}.js"></script>
	<script src="/speccdb/js/localization/datepicker-${pageContext.response.locale}.js"></script>
	<script src="/speccdb/js/addComputer.js"></script>
</body>
</html>