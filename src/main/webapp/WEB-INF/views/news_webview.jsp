<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8"%>
<!DOCTYPE  html>
<html>
	<jsp:include page="_common_header.jsp"></jsp:include>
	<title>今日好房</title>
</head>
<body class="body-news-webview">
	<div id="app">
		<news id="${newsId}" userid="${userId}" username="${username}"></news>
	</div>
	<script src="/static/js/vendor.js?ver=1"></script>
	<script src="/static/js/news.js?ver=1"></script>
</body>
</html>
