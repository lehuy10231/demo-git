<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p>Do you really want to assign product:
        <strong>${product.getProductName()}

        </strong> to other category ?
    </p>
    <form:form method="POST"
    action="/products/insertProduct"  
    modelAttribute="product">
    <form:input type="text"             
        placeholder="Enter product's name"
        value="${product.getProductName()}" 
        path="productName"
    /><br>    
    <form:errors path="productName" cssClass="error"/> <br>           
    <form:input type="number"     
        placeholder="Enter product's price"
        value="${product.getPrice()}" 
        path="price"
    /><br/>  
    <form:errors path="price" cssClass="error" /> <br>
    <form:input type="text"             
        placeholder="Enter description"
        value="${product.getDescription()}"
        path="description"
    /><br/>   

    <form:select path="categoryID">
        <c:forEach var="category" items="${categories}">
            <form:option value="${category.getCategoryID()}">
                ${category.getCategoryName()}
            </form:option>
        </c:forEach>                        
    </form:select>
    <input type="submit" value="Update">
</form:form>
<form:form 
method="POST"
action = "/products/deleteProduct/${product.getProductID()}"
onsubmit="return confirm('Are you sure delete this Product ?') ? true : false"
>
<input type="submit" value="Delete"/>
</form:form>
    
</body>
</html>