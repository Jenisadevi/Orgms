<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Employees</title></head><body>
  <h2>Employees</h2>
  <a href="${pageContext.request.contextPath}/employees?action=create">Create New</a>
  <table border="1" cellpadding="5">
    <tr><th>Code</th><th>Name</th><th>Email</th><th>Designation</th><th>Mobile</th><th>Actions</th></tr>
    <c:forEach var="e" items="${employees}">
      <tr>
        <td>${e.employeeCode}</td>
        <td>${e.empName}</td>
        <td>${e.empCemail}</td>
        <td>${e.empDesignation}</td>
        <td>${e.empCmob}</td>
        <td><a href="${pageContext.request.contextPath}/employees?action=edit&id=${e.employeeId}">Edit</a></td>
      </tr>
    </c:forEach>
  </table>
  <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Back</a>
</body></html>
