<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Customers</title></head><body>
  <h2>Customers</h2>
  <a href="${pageContext.request.contextPath}/customers?action=create">Create New</a>
  <table border="1" cellpadding="5">
    <tr><th>Code</th><th>Name</th><th>Email</th><th>Address</th></tr>
    <c:forEach var="c" items="${customers}">
      <tr>
        <td>${c.custCode}</td>
        <td>${c.custName}</td>
        <td>${c.custEmail}</td>
        <td>${c.custAddress}</td>
      </tr>
    </c:forEach>
  </table>
</body></html>
