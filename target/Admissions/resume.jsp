<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resume List</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #aaa; padding: 8px; }
        input[type=text] { width: 100%; }
    </style>
</head>
<body>

<h2>Resume Details</h2>

<table>
    <tr>
        <th>Name</th>
        <th>Mobile</th>
        <th>Post</th>
        <th>Qualification</th>
        <th>Experience</th>
        <th>Action</th>
    </tr>

<%
    List<Map<String,String>> list =
        (List<Map<String,String>>) request.getAttribute("resumeList");

    if (list != null && !list.isEmpty()) {
        for (Map<String,String> row : list) {
%>
    <tr>
        <td colspan="6">
            <form method="post" action="<%= request.getContextPath() %>/resume">
                <input type="hidden" name="sl_no" value="<%= row.get("sl_no") %>">

                <table style="width:100%; border:none;">
                    <tr>
                        <td><input type="text" name="name" value="<%= row.get("name") %>"></td>
                        <td><input type="text" name="mobile_no" value="<%= row.get("mobile_no") %>"></td>
                        <td><input type="text" name="post_applied_for" value="<%= row.get("post_applied_for") %>"></td>
                        <td><input type="text" name="qualification" value="<%= row.get("qualification") %>"></td>
                        <td><input type="text" name="experience" value="<%= row.get("experience") %>"></td>
                        <td><button type="submit">Update</button></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
<%
        }
    } else {
%>
    <tr>
        <td colspan="6" style="text-align:center;">No records found</td>
    </tr>
<%
    }
%>

</table>

</body>
</html>