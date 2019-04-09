<%--
  Created by IntelliJ IDEA.
  User: pdl
  Date: 2019/4/9
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>主页</title>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <link rel="SHORTCUT ICON" href="images/icon.ico">
    <link rel="BOOKMARK" href="images/icon.ico">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/head.css">
    <link rel="stylesheet" type="text/css" href="css/list_main.css">
    <script type="text/javascript" src="script/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script src="script/time.js"></script>
</head>
<body>
<!--头部-->
<jsp:include page="share/head.jsp"></jsp:include>

<!--中间主体部分-->
<div class="main">
    <%--<div ><img src="images/brain.png"></div>--%>
    <div style="text-align: center">
        <c:if test="${point>=57}">你的得分是${point}分,评级是优秀。</c:if>
        <c:if test="${point>=54 && point<57 }">你的得分是${point}分,评级是良好。</c:if>
        <c:if test="${point>=44 && point<54 }">你的得分是${point}分,评级是中等。</c:if>
        <c:if test="${point>=33 && point<44 }">你的得分是${point}分,评级是中下。</c:if>
        <c:if test="${point<33}">你的得分是${point}分,评级是低下。</c:if>
    </div>
</div>
</body>
</html>
