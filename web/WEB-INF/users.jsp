    <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="Style.css" type="text/css"></link>
    </head>
    <body>
        <style>
            .container {
                width: 80%;
            }
            .add {
                width: 30%;
                float: left;
            }
            .edit {
                width: 30%;
                float: right;
/*            visibility: hidden;*/
            }
            .row {
                padding-top: 5%;
                width: 100%;
            }
        </style>
        
        <div class="container">
            
            <div class="add">
                <form action="user" method="POST">
                <h1>Add a user</h1>
                <input type="hidden" name="action" value="add">
                <table>
                    <tr><td><label for="addEmail">Email: </label></td><td><input type="email" name="addEmail"></td></tr>
                    <tr><td><label for="addFirst">First Name: </label></td><td><input type="text" name="addFirst" ></td></tr>
                    <tr><td><label for="addLast">Last Name: </label></td><td><input type="text" name="addLast" ></td></tr>
                    <tr><td><label for="addPassword">Password: </label></td><td><input type="text" name="addPassword" ></td></tr>
                    <tr><td><label for="addRole"></label></td>
                        <td>
                            
                        <select name="addRole">
                            <option>Select Role</option>
                            <c:forEach var="role" items="${roles}">
                            <option>${role.roleName}</option>
                            </c:forEach>
                        </select>
                            
                        </td>
                    </tr>
                </table>
                <button type="submit">Submit</button>
                </form>
            </div>

            
            <div class="edit">
                <form action="user" method="POST">
                <h1>Edit a user</h1>
                <input type="hidden" name="action" value="update">
                <table>
                    <tr><td><label for="editEmail">Email: </label></td><td><input type="text" name="editEmail" value="<%= request.getAttribute("emailToEdit")%>" readonly="true"></td></tr>
                    <tr><td><label for="editFirst">First Name: </label></td><td><input type="text" name="editFirst" value="<%= request.getAttribute("editFirstName")%>"></td></tr>
                    <tr><td><label for="editLast">Last Name: </label></td><td><input type="text" name="editLast" value="<%= request.getAttribute("editLastName")%>"></td></tr>
                    <tr><td><label for="editPassword">Password: </label></td><td><input type="text" name="editPassword" ></td></tr>
                    <tr><td><label for="editRole"></label></td>
                        <td>
                                <select name="editRole">
                                    <option>Select Role</option>
                                    <c:forEach var="role" items="${roles}">
                                    <option>${role.roleName}</option>
                                    </c:forEach>
                                </select>
                            
                        </td>
                    </tr>
                </table>
                <button type="submit">Submit</button>
                </form>
            </div>
            
            
            <br>
            <br>
            

            <div class="row">
                <div class="col">
                    <h1>User Management System</h1>
                    
                    <table class="table">
                        <thead>
                            <tr>
                                <th>E-mail</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <td>Active</td>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>${user.email}</td>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.active ? "Y" : "N"}</td>
                                    <td>
                                        <a href="user?action=edit&user=${user.email}">Edit</a>
                                        <a href="user?action=delete&user=${user.email}">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
    </body>
</html>
