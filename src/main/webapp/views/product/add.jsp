<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm sản phẩm mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Thêm sản phẩm mới</h2>
    <form action="${pageContext.request.contextPath}/product/add" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Tên sản phẩm</label>
            <input type="text" name="name" id="name" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Giá</label>
            <input type="number" step="0.01" name="price" id="price" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="quantity" class="form-label">Số lượng</label>
            <input type="number" name="quantity" id="quantity" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="color" class="form-label">Màu sắc</label>
            <input type="text" name="color" id="color" class="form-control">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Mô tả</label>
            <textarea name="description" id="description" class="form-control"></textarea>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label">Danh mục</label>
            <select name="category" id="category" class="form-select" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${listCategory}">
                    <option value="${cat.id}">${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
        <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary">Quay lại</a>
    </form>
</div>
<c:if test="${not empty message}">
    <div id="notification" style="display:none; padding:10px; margin-bottom:10px; border: 1px solid green; color: green;">
            ${message}
    </div>
    <script>
        document.getElementById("notification").style.display = "block";
        setTimeout(() => {
            document.getElementById("notification").style.display = "none";
        }, 3000);
    </script>
</c:if>
<c:if test="${param.message == 'success'}">
    <script>
        alert("Thêm mới sản phẩm thành công!");
    </script>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
