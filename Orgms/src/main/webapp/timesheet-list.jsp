<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Timesheets</title></head><body>
  <h2>Your Timesheets</h2>
  <a href="${pageContext.request.contextPath}/timesheets?action=create">Create New</a>
  <table border="1" cellpadding="5">
    <tr><th>ID</th><th>Project</th><th>Weekly Total</th><th>Status</th><th>Actions</th></tr>
    <c:forEach var="t" items="${timesheets}">
      <tr>
        <td>${t.timesheetId}</td>
        <td>${t.projectId}</td>
        <td>${t.weeklyTotal}</td>
        <td>${t.status}</td>
        <td>
          <c:if test="${t.status == 'Draft'}">
            <form method="post" action="${pageContext.request.contextPath}/timesheets" style="display:inline;">
              <input type="hidden" name="action" value="submit"/>
              <input type="hidden" name="timesheetId" value="${t.timesheetId}"/>
              <button type="submit">Submit</button>
            </form>
          </c:if>
        </td>
      </tr>
    </c:forEach>
  </table>
</body></html>
