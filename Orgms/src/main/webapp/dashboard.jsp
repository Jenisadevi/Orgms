<%@ page session="true" %>
<html>
<head><title>Dashboard</title></head>
<body>
  <h2>Welcome, ${sessionScope.username}</h2>
  <ul>
    <li><a href="${pageContext.request.contextPath}/employees">Employees</a></li>
    <li><a href="${pageContext.request.contextPath}/customers">Customers</a></li>
    <li><a href="${pageContext.request.contextPath}/projects">Projects</a></li>
    <li><a href="${pageContext.request.contextPath}/timesheets">Timesheets</a></li>
    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
  </ul>
</body>
</html>
