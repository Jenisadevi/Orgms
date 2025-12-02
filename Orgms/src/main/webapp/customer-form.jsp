<%@ page contentType="text/html;charset=UTF-8" %>
<html><head><title>Customer Form</title></head><body>
  <h2>Create Customer</h2>
  <form method="post" action="${pageContext.request.contextPath}/customers">
    <input type="hidden" name="action" value="save"/>
    Name: <input name="name"/><br/>
    Email: <input name="email"/><br/>
    Address: <textarea name="address"></textarea><br/>
    <button type="submit">Save</button>
    <a href="${pageContext.request.contextPath}/customers">Cancel</a>
  </form>
</body></html>
