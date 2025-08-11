<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
        }

        .table thead {
            background-color: #343a40;
            color: white;
        }

        .action-btn {
            display: flex;
            gap: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">Danh sách sản phẩm</h2>
    <form class="row g-3 mb-4 align-items-center" method="get"
          action="${pageContext.request.contextPath}/product/search">
        <div class="col-md-3">
            <input type="text" name="name" class="form-control" placeholder="Tên sản phẩm" value="${param.name}">
        </div>
        <div class="col-md-2">
            <input type="number" name="price" class="form-control" placeholder="Giá" value="${param.price}">
        </div>
        <div class="col-md-3">
            <select name="category" class="form-select">
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${listCategory}">
                    <option value="${cat.id}"
                            <c:if test="${param.category == cat.id}">selected</c:if>>${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <input type="text" name="color" class="form-control" placeholder="Màu sắc" value="${param.color}">
        </div>
        <div class="col-md-1 d-flex gap-1">
            <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
        </div>
        <div class="col-md-1 d-flex justify-content-center">
            <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary btn-sm px-3">Clear</a>
        </div>
    </form>

    <div class="mb-3">
        <a href="/product/add" class="btn btn-success">+ Thêm sản phẩm</a>
    </div>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Màu sắc</th>
            <th>Số lượng</th>
            <th>Mô tả</th>
            <th>Danh mục</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${listProduct}" varStatus="stutus">
            <tr>
                <td>${status.index + 1}</td>
                <td>${product.name}</td>
                <td>
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                </td>
                <td>${product.color}</td>
                <td>${product.quantity}</td>
                <td>${product.description}</td>
                <td>${product.getCategory().getName()}</td>
                <td>
                    <div class="action-btn">
                        <a href="${pageContext.request.contextPath}/product/edit?id=${product.id}"
                           class="btn btn-warning btn-sm">Sửa</a>
                        <a href="${pageContext.request.contextPath}/product/delete?id=${product.id}"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>

                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
