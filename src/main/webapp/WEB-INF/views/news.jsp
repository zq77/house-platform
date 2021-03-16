<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>${news.title}</title>
        <link rel="shortcut icon" href="/static/images/favicon.ico" type="image/x-icon">
    </head>
    <style>
        .container {
            width: 100%;
            max-width: 677px;
            margin: 0 auto;
            padding: 20px 16px 12px;
            background: #fff;
            box-sizing: border-box;
        }
        .news-detail .news-title {
            font-size: 22px;
            line-height: 1.4;
            margin-bottom: 14px;
        }
        .news-detail .news-intro {
            margin-bottom: 15px;
            line-height: 20px;
            font-size: 15px;
            word-wrap: break-word;
            word-break: break-all;
            color: rgba(0,0,0,0.3);
        }
        .bdsharebuttonbox {
            margin-bottom: 22px;
        }
        .news-detail .news-full-text {
            overflow: hidden;
            color: #333;
            font-size: 17px;
            word-wrap: break-word;
            hyphens: auto;
            text-align: justify;
            position: relative;
            z-index: 0;
        }
        .news-detail .news-full-text img {
            max-width: 100%!important;
            height: auto!important;
        }
        .news-detail .news-full-text * {
            max-width: 100%!important;
            box-sizing: border-box!important;
            -webkit-box-sizing: border-box!important;
            word-wrap: break-word!important;
        }
    </style>
    <body>
        <div class="container">
            <div class="news-detail">
                <h1 class="news-title">${news.title}</h1>
                <div class="news-intro">
                    <span class="intro-item">${news.original}</span>
                    <span class="intro-item">${news.authorNick}</span>
                    <span class="intro-item">${news.author}</span>
                    <span class="intro-item">${news.publishDate}</span>
                </div>
                <div class="bdsharebuttonbox" data-tag="share_1">
                    <a class="bds_weixin" data-cmd="weixin"></a>
                    <a class="bds_tsina" data-cmd="tsina"></a>
                    <a class="bds_qzone" data-cmd="qzone" href="#"></a>
                    <a class="bds_tqq" data-cmd="tqq" href="#"></a>
                    <a class="bds_tieba" data-cmd="tieba"></a>
                    <a class="bds_more" data-cmd="more">更多</a>
                </div>
                <div class="news-full-text">
                    ${news.content}
                </div>
            </div>
        </div>
    <!-- /.container -->
    </body>
    <script>
        window._bd_share_config = {
            share : [{
                    "tag" : "share_1",
                    "bdSize" : 24,
            }],
        }
        with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
    </script>
</html>
