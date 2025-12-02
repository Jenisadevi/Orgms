<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Projects</title></head><body>
  <h2>Projects</h2>
  <a href="${pageContext.request.contextPath}/projects?action=create">Create New</a>
  <table border="1" cellpadding="5">
    <tr><th>Project ID</th><th>Job Name</th><th>Status</th><th>Total Hours</th><th>Total Cost</th></tr>
    <c:forEach var="p" items="${projects}">
      <tr>
        <td>${p.projectId}</td>
        <td>${p.jobName}</td>
        <td>${p.status}</td>
        <td>${p.totalHours}</td>
        <td>${p.totalCost}</td>
      </tr>
    </c:forEach>
  </table>
</body></html>
