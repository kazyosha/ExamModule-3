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
    <form action="${pageContext.request.contextPath}/product/add" method="post" onsubmit="return validateForm()">
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
            <select name="color" id="color" class="form-select" required>
                <option value="">-- Chọn màu --</option>
                <c:forEach var="col" items="${listColor}">
                    <option value="${col}">${col}</option>
                </c:forEach>
            </select>
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
    <script>
        if ("${message}" === "add_success") {
            alert("Thêm sản phẩm thành công!");
        }
        if ("${message}" === "welcome_add_page") {
            alert("Bạn đang ở trang thêm sản phẩm.");
        }
    </script>
</c:if>

<script>
    function validateForm() {
        const name = document.getElementById("name").value.trim();
        const priceStr = document.getElementById("price").value.trim();
        const quantityStr = document.getElementById("quantity").value.trim();

        if (name === "") {
            alert("Tên sản phẩm không được để trống.");
            return false;
        }

        if (priceStr === "") {
            alert("Giá sản phẩm không được để trống.");
            return false;
        }
        const price = parseFloat(priceStr);
        if (isNaN(price) || price <= 10000000) {
            alert("Giá sản phẩm phải lớn hơn 10.000.000 VND.");
            return false;
        }

        if (quantityStr === "") {
            alert("Số lượng không được để trống.");
            return false;
        }
        const quantity = parseInt(quantityStr);
        if (isNaN(quantity) || quantity <= 0) {
            alert("Số lượng phải là số nguyên dương.");
            return false;
        }

        return true;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
