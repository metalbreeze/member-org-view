<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>关于我们</title>
</head>
    
<body>
<jsp:include page="_menu.jsp" />
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
广西绿康【归君堂】生物科技有限公司是一家 “传承天然健康养生，弘扬原生态滋补”真谛的新兴生物科技型企业，
致力于打造东南亚纯正原生态草本植物滋补调理第一品牌，公司集【提炼·研发·加工·推广·销售】为一体，
明确把“绿色、生态、天然、健康”作为【归君堂】产品理念，以“缔造幸福和谐生活”为品牌诉求。
公司与中马产业园合作，立足东盟商圈，面向全世界，秉承正直·诚信·务实的经营理念，
为全球65亿消费者提供高品质纯正原生态绿色草本植物的养生产品而不懈努力！
公司顺应“大众创业，万众创新”的时代趋势，把握经济发展动脉，大力倡导：帮助他人·成就自己”的大爱文化！
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
【归君堂】这个正在迅速壮大崛起的民族骄子，正满怀信心，一步一个脚印走向全国，走向世界！！！
欢迎融入【归君堂】一起创造辉煌人生！！！ 
</div>

        <div id="wrap">
            <div class="wrapIn">
                <div id="text1">风起云涌，雨落无痕。曾记素衣否？白马跃九州。剑指金沙东，而今已四秋。卧听江湖雨，醉饮竹叶靑。倚剑盂溪上，凝雪夜成风。</div>
                <div id="text2"></div>
            </div>
        </div>
        
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
	<a href="http://mp.weixin.qq.com/s/-y2TueV6e4tGNEjMasu0jA">香港“健康卫视”报导归君堂"东革阿里”神奇功效</a><br/>
	<a href="http://mp.weixin.qq.com/s/hS3SRK2W8jrY9BNw2LQdoA">女性圣品-力比滋卡琪花蒂玛宣传纪录片</a><br/>
	<a href="${pageContext.request.contextPath}/images">公司证件</a><br/>
</div>

<script type="text/javascript">
var wrap = document.getElementById("wrap");
var text1 = document.getElementById("text1");
var text2 = document.getElementById("text2");
text2.innerHTML = document.getElementById("text1").innerHTML;

setInterval(function() {
    if (wrap.scrollLeft - text2.offsetWidth >= 0) {
        wrap.scrollLeft -= text1.offsetWidth;
    } else {
        wrap.scrollLeft++;
    }
}, 20);
</script>

   
</html>