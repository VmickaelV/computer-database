<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="computerEditor" scope="request"
	class="com.excilys.mviegas.speccdb.managers.ComputerEditorBean">
	<jsp:setProperty name="computerEditor" property="*" />
	<jsp:setProperty name="computerEditor" property="idCompany"
		param="companyId" />
</jsp:useBean>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when
		test="${pageContext.request.method=='POST' && param.action == 'add'}">
		<c:if test="${computerEditor.addComputer()}" var="computerAdded"
			scope="session">
			<c:redirect url="dashboard.jsp" />
		</c:if>
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
<link href="../css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="../css/jquery-ui.structure.min.css" rel="stylesheet" media="screen">
<link href="../css/jquery-ui.theme.min.css" rel="stylesheet" media="screen">
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
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:choose>
						<c:when test="${not empty computerEditor.computer}">
							<div class="label label-default pull-right">
	                        	id: ${computerEditor.computer}
                    		</div>
                    		<h1>Edit Computer</h1>
                    	</c:when>
                    	<c:otherwise>
                    		<h1>Add Computer</h1>
                    	</c:otherwise>
					</c:choose>
					
					<form action="${not empty computerEditor.computer ? 'editComputer.jsp?id='+computerEditor.computer.id : 'addComputer.jsp'}" method="POST">
						<fieldset>
							<div
								class="form-group ${param.action == 'add' && !computerEditor.hasValidName() ? 'has-error' : ''}">
								<label for="name" class="control-label">Computer name</label> <input
									type="text" class="form-control" id="name" name="name"
									placeholder="Computer name" value="${computerEditor.name}"
									required="required">
								<c:if test="${!computerEditor.hasValidName()}">
									<span class="help-block">Computer's Name is compulsory.</span>
								</c:if>
							</div>
							<div
								class="form-group ${!computerEditor.hasValidIntroducedDate() ? 'has-error' : ''}">
								<label for="introducedDate" class="control-label">Introduced
									date</label> <input type="date" class="form-control"
									id="introducedDate" name="introducedDate"
									placeholder="Introduced date"
									value="${computerEditor.introducedDate}">
								<c:if test="${!computerEditor.hasValidIntroducedDate()}">
									<span class="help-block">Valid format Date : dd/mm/yyyy</span>
								</c:if>
							</div>
							<div
								class="form-group ${!computerEditor.hasValidDiscontinuedDate() ? 'has-error' : ''}">
								<label for="discontinuedDate" class="control-label">Discontinued
									date</label> <input type="date" class="form-control"
									id="discontinuedDate" name="discontinuedDate"
									placeholder="Discontinued date"
									value="${computerEditor.discontinuedDate}">
								<c:if test="${!computerEditor.hasValidDiscontinuedDate()}">
									<span class="help-block">Valid format Date : dd/mm/yyyy</span>
								</c:if>
							</div>
							<div class="form-group">
								<label for="companyId" class="control-label">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>

									<c:forEach items="${computerEditor.companies}" var="company">
										<!-- ${computerEditor.idCompany} ${company.id} -->
										<option value="${company.id}"
											${computerEditor.idCompany eq company.id ? 'selected=\"selected\"' : ''}>${company.name}</option>
									</c:forEach>

								</select>
								<c:if test="${!computerEditor.hasValidIdCompany()}">
									<span class="help-block">Select an company above.</span>
								</c:if>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" id="btnSubmit" name="action" value="add"
								class="btn btn-primary"> or <a href="dashboard.jsp"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.validate.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$("form").validate( {
			rules : {
				introducedDate : {
					date : true
				}
			}
		});
		
		$("#introducedDate").datepicker({
			changeMonth:true,
			changeYear:true,
			maxDate: "+0d",
			buttonText: "Choose"
		}).change(function () {

		});
		$("#discontinuedDate").datepicker({
			changeMonth:true,
			changeYear:true,
			maxDate: "+0d",
			buttonText: "Choose"
		});

	</script>
</body>
</html>