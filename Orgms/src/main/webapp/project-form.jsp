<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Project Form</title></head><body>
  <h2>Create/Update Project</h2>
  <form method="post" action="${pageContext.request.contextPath}/projects">
    <input type="hidden" name="action" value="save"/>
    <input type="hidden" name="projectId" value="${project.projectId}"/>
    Job Name: <input name="jobName" value="${project.jobName}"/><br/>
    Customer:
    <select name="customerId">
      <c:forEach var="c" items="${customers}">
        <option value="${c.customerId}" ${project.customerId==c.customerId ? 'selected':''}>${c.custName}</option>
      </c:forEach>
    </select><br/>
    Total Hours: <input name="totalHours" value="${project.totalHours}"/><br/>
    Total Cost: <input name="totalCost" value="${project.totalCost}"/><br/>
    Status: <select name="status"><option>Open</option><option>In-progress</option><option>Completed</option></select><br/>
    <button type="submit">Save</button>
  </form>
</body></html>
