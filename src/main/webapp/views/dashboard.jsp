<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="listManager" scope="page"
	class="com.excilys.mviegas.speccdb.managers.ListManager" />
<%-- <%@page import="com.excilys.mviegas.speccdb.managers.ListManager"%> --%>
<%-- <%!ListManager listManager = new ListManager();%> --%>
<%-- <% --%>
<%-- 	listManager.init();--%>
<%-- %> --%>
<c:choose>
<c:when test="${pageContext.request.method=='POST'}">
</c:when>
<c:when test="${pageContext.request.method=='GET'}">
		<c:if test="${param.size != null && (listManager.size = param.size) >= 0}">
<%-- 			<c:set property="size" target="listManager" value="${param.size}"/> --%>
		</c:if>
		
		<c:if test="${param.page != null && (listManager.page = (param.page - 1)) >= 0}">
<%-- 			<c:set property="size" target="listManager" value="${param.size}"/> --%>
		</c:if>
	
</c:when>
<c:when test="${param.add!=null}">
</c:when></c:choose>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.jsp"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<%--suppress ELValidationInJSP --%>
		<c:if test="${computerAdded}">
			<c:remove var="computerAdded" scope="session" />
			<div class="alert alert-success alert-dismissible fade in" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
				</button>
				<h4>Computer successfully added into Database</h4>
			</div>
		</c:if>
		<div class="container">
			<h1 id="homeTitle">
				${listManager.nbComputers()} Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer.jsp">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach items="${listManager.displayedComputers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><jsp:element name="a">
								<jsp:attribute name="href">
									editComputer.html?id=${computer.id}
								</jsp:attribute>
								<jsp:body>
										<c:out value="${computer.name}" />
									</jsp:body>
							</jsp:element></td>
							<td><c:out value="${computer.introducedDate}" /></td>
							<td><c:out value="${computer.discontinuedDate}" /></td>
							<td><c:out value="${computer.manufacturer.name}" /></td>
						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="#" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a type="button" class="btn btn-default" href="?size=10">10</a>
				<a type="button" class="btn btn-default" href="?size=50">50</a>
				<a type="button" class="btn btn-default" href="?size=100">100</a>
			</div>
		</div>
	</footer>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>

</body>
</html>