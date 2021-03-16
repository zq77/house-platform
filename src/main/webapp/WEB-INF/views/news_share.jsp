<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>${news.title}</title>
        <link rel="shortcut icon" href="/static/images/favicon.ico" type="image/x-icon">
        <link href="/static_page/plugins/lightslider/css/lightslider.min.css" rel="stylesheet">
    </head>
    <style>
        body {
            margin: 0;
            padding: 0;
        }
        .container {
            width: 100%;
            max-width: 677px;
            margin: 0 auto;
            padding: 20px 16px 12px;
            background: #fff;
            box-sizing: border-box;
        }
        .container.show-margin {
            margin-bottom: 120px;
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

        .lSSlideOuter {
            position: relative;
        }
        .lSSlideWrapper {
            width: 100%;
            max-width: 677px;
            height: 120px;
            margin: 0 auto;
        }

        .ad-row,
        .ad-row .ad-content{
            width: 100%;
            max-width: 100%;
            background: #fff;
        }
        .ad-row .ad-content .ad-img {
            float: left;
            width: 120px;
            height: 120px;
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
        }
        .ad-row .ad-content .ad-img img{
            width: 120px;
            height: 120px;
        }
        .ad-row .ad-content .ad-img.img-only {
            width: 100%;
        }
        .ad-row .ad-content .ad-img.img-only img{
            width: 100%;
            height: 120px;
        }
        .ad-row .ad-content .ad-text-list {
            float: left;
            height: 120px;
            box-sizing: border-box;
            padding: 10px;
            font-size: 14px;
            line-height: 20px;
        }
        .ad-row .ad-content .ad-text-list .ad-title {
            font-size: 16px;
            line-height: 22px;
        }
        @media (max-width: 640px) {
            .container.show-margin {
                margin-bottom: 80px;
            }
            .lSSlideWrapper {
                height: 80px;
            }
            .ad-row .ad-content .ad-img {
                width: 80px;
                height: 80px;
            }
            .ad-row .ad-content .ad-img img{
                width: 80px;
                height: 80px;
            }
            .ad-row .ad-content .ad-img.img-only img{
                height: 80px;
            }
            .ad-row .ad-content .ad-text-list {
                height: 80px;
            }
        }
        .show-fixed {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            z-index: 10000;
            width: 100%;
            max-width: 677px;
            margin: 0 auto;
        }
        .lSSlideOuter .lSPager.lSpg {
            position: absolute;
            bottom: 5px;
            left: 0;
            width: 100%;
        }
    </style>
    <body>
        <c:if test="${not empty topNewsAdList}">
            <ul id="imgListSlider">
                <c:forEach var="newsAd" items="${topNewsAdList}" varStatus="index">
                    <li class="ad-row">
                        <c:choose>
                            <c:when test="${newsAd.templateType=='TWLJ'||newsAd.templateType=='TWMS'}">
                                <div class="ad-content">
                                    <c:choose>
                                        <c:when test="${not empty newsAd.link}">
                                            <a href="${newsAd.link}">
                                                <div class="ad-img" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                </div>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="ad-img" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="ad-text-list">
                                        <div class="ad-title">${newsAd.title}</div>
                                        <div class="ad-intro">${newsAd.intro}</div>
                                        <c:if test="${newsAd.templateType=='TWMS'}">
                                            <div class="ad-text">${item.text}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="ad-content">
                                    <c:choose>
                                        <c:when test="${not empty newsAd.link}">
                                            <a href="${newsAd.link}">
                                                <div class="ad-img img-only" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                </div>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="ad-img img-only" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <div class="container ${not empty cssFixed ? "show-margin" : ""}">
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
        <c:if test="${not empty bottomNewsAdList}">
            <div class="${not empty cssFixed ? "show-fixed" : ""}">
                <ul id="imgListSliderBottom">
                    <c:forEach var="newsAd" items="${bottomNewsAdList}" varStatus="index">
                        <li class="ad-row">
                            <c:choose>
                                <c:when test="${newsAd.templateType=='TWLJ'||newsAd.templateType=='TWMS'}">
                                    <div class="ad-content">
                                        <c:choose>
                                            <c:when test="${not empty newsAd.link}">
                                                <a href="${newsAd.link}">
                                                    <div class="ad-img" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                    </div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="ad-img" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="ad-text-list">
                                            <div class="ad-title">${newsAd.title}</div>
                                            <div>${newsAd.intro}</div>
                                            <c:if test="${newsAd.templateType=='TWMS'}">
                                                <div>${item.text}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="ad-content">
                                        <c:choose>
                                            <c:when test="${not empty newsAd.link}">
                                                <a href="${newsAd.link}">
                                                    <div class="ad-img img-only" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                    </div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="ad-img img-only" style="background-image: url('${qiNiuImgUrl}${newsAd.imageUrl}')">
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
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
    <!--Core JavaScript file  -->
    <script src="/static_page/js/jquery-1.10.2.js"></script>
    <!--bootstrap JavaScript file  -->
    <script src="/static_page/plugins/lightslider/js/lightslider.min.js"></script>
    <script>
        $(function() {
            $('#imgListSlider, #imgListSliderBottom').lightSlider({
                item: 1,
                minSlide:1,
                maxSlide:1,
                slideMargin: 0,
                slideMove: 1,
                speed : 2000,
                pause: 8000,
                auto : true,
                loop : true,
                controls: false,
                mode:'fade'
            });
        });
    </script>
</html>
