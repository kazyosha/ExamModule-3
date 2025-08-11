<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Sửa sản phẩm</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/product/edit" method="post">
        <input type="hidden" name="id" value="${product.id}">
        <div class="mb-3">
            <label for="name" class="form-label">Tên sản phẩm</label>
            <input type="text" name="name" id="name" value="${product.name}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Giá</label>
            <input type="number" step="0.01" name="price" id="price" value="${product.price}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="quantity" class="form-label">Số lượng</label>
            <input type="number" name="quantity" id="quantity" value="${product.quantity}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="color" class="form-label">Màu sắc</label>
            <input type="text" name="color" id="color" value="${product.color}" class="form-control">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Mô tả</label>
            <textarea name="description" id="description" class="form-control">${product.description}</textarea>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label">Danh mục</label>
            <select name="category" id="category" class="form-select" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${listCategory}">
                    <option value="${cat.id}" <c:if test="${cat.id == product.category.id}">selected</c:if>>${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
        <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary">Hủy</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
