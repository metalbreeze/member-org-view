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
<%-- 
<div id="wrap" class="div_float">
	<div class="wrapIn">
	<div id="text1" class="div_float">
				男人:只会记得让他最疯狂的女人 女人:只记得让她到达巅峰的男人  你行不行，她说了算，你和她（他）之间，就差一套东革阿里！请别忽略性福生活                东革阿里，真正的植物伟哥！
	</div>
	         <div id="text2" class="div_float"></div>
	</div>
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

--%>


<div>
<img alt="" src="${pageContext.request.contextPath}/img/background.jpg">
</div>

<div style="padding: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
广西一路发生态科技有限公司是一家立足于生态健康领域的新兴企业，
致力于全球领先的生态健康产品的研发、生产、推广与销售，公司明确把“绿色、健康、环保”作为公司的产品理念，
以缔造“健康生态生活”作为品牌诉求，立志为全球65亿消费者提高健康生活品质服务！
目前，一路发生态科技上市产品有几百种单品，涵盖毛巾、袜子、日用品等系列。一路发生态科技将不断拓展和优化企业产品线，从而在产品领域建立竞争优势。
关爱生命、造福人类是一路发所有人一贯秉承的理念，一路发生态科技有限公司在发展实业的同时，
不忘回馈社会，为退伍军人、大学生、残疾人、资金短缺的人群提供一个零投资创业的好平台！
     
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
“不忘初心，广结善缘”，是我们一路发生态科技所有人的夙愿，“建良心企业，创民族品牌”是一路发生态科技有限公司坚定不移的战略目标，
我们深信:在我们所有伙伴的努力下，一路发生态科技将会迅速茁壮成长，实现宏伟目标！
</div>
<br/>
<script src="${pageContext.request.contextPath}/js/jssor.slider-25.1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">
        jQuery(document).ready(function ($) {

            var jssor_1_options = {
              $AutoPlay: 1,
              $AutoPlaySteps: 4,
              $SlideDuration: 160,
              $SlideWidth: 200,
              $SlideSpacing: 3,
              $Cols: 5,
              $Align: 390,
              $ArrowNavigatorOptions: {
                $Class: $JssorArrowNavigator$,
                $Steps: 5
              },
              $BulletNavigatorOptions: {
                $Class: $JssorBulletNavigator$
              }
            };

            var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

            /*#region responsive code begin*/
            /*remove responsive code if you don't want the slider scales while window resizing*/
            function ScaleSlider() {
                var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
                if (refSize) {
                    refSize = Math.min(refSize, 980);
                    jssor_1_slider.$ScaleWidth(refSize);
                }
                else {
                    window.setTimeout(ScaleSlider, 30);
                }
            }
            ScaleSlider();
            $(window).bind("load", ScaleSlider);
            $(window).bind("resize", ScaleSlider);
            $(window).bind("orientationchange", ScaleSlider);
            /*#endregion responsive code end*/
        });
    </script>
    <style>
        .jssorb057 .i {position:absolute;cursor:pointer;}
        .jssorb057 .i .b {fill:none;stroke:#fff;stroke-width:2000;stroke-miterlimit:10;stroke-opacity:0.4;}
        .jssorb057 .i:hover .b {stroke-opacity:.7;}
        .jssorb057 .iav .b {stroke-opacity: 1;}
        .jssorb057 .i.idn {opacity:.3;}

        .jssora073 {display:block;position:absolute;cursor:pointer;}
        .jssora073 .a {fill:#ddd;fill-opacity:.7;stroke:#000;stroke-width:160;stroke-miterlimit:10;stroke-opacity:.7;}
        .jssora073:hover {opacity:.8;}
        .jssora073.jssora073dn {opacity:.4;}
        .jssora073.jssora073ds {opacity:.3;pointer-events:none;}
    </style>

<table border="1" style="BORDER=30;BORDERCOLOR#fff;background-color: #fff">
<tr>
<td valign="top">
<img alt="" src="${pageContext.request.contextPath}/img/ke1.jpg">
</td>
<td width="180px">
<div style="background-color: #fff">
客服微信&nbsp;&nbsp;&nbsp;18587795798
<br/>
客服电话&nbsp;&nbsp;&nbsp;18587795798
</div>
</td>
<td>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
		<a href="http://mp.weixin.qq.com/s/-y2TueV6e4tGNEjMasu0jA">香港“健康卫视”报导归君堂"东革阿里”神奇功效</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="http://mp.weixin.qq.com/s/hS3SRK2W8jrY9BNw2LQdoA">女性圣品-力比滋卡琪花蒂玛宣传纪录片</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/images">公司证件</a>
</div>
</td>
</tr>
</table>
    <div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:980px;height:150px;overflow:hidden;visibility:hidden;">
        <!-- Loading Screen -->
        <div data-u="loading" style="position:absolute;top:0px;left:0px;background:url('img/loading.gif') no-repeat 50% 50%;background-color:rgba(0, 0, 0, 0.7);"></div>
        <div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:980px;height:150px;overflow:hidden;">
            <div>
                <img data-u="image" src="img/a1.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a2.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a3.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a5.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a6.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a7.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a8.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a9.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a10.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a11.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a12.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a13.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a14.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a15.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/a16.jpg" />
            </div>
            <a data-u="any" href="https://www.jssor.com" style="display:none">bootstrap slider</a>
        </div>
        <!-- Bullet Navigator -->
        <div data-u="navigator" class="jssorb057" style="position:absolute;bottom:12px;right:12px;" data-autocenter="1" data-scale="0.5" data-scale-bottom="0.75">
            <div data-u="prototype" class="i" style="width:16px;height:16px;">
                <svg viewbox="0 0 16000 16000" style="position:absolute;top:0;left:0;width:100%;height:100%;">
                    <circle class="b" cx="8000" cy="8000" r="5000"></circle>
                </svg>
            </div>
        </div>
        <!-- Arrow Navigator -->
        <div data-u="arrowleft" class="jssora073" style="width:50px;height:50px;top:0px;left:30px;" data-autocenter="2" data-scale="0.75" data-scale-left="0.75">
            <svg viewbox="0 0 16000 16000" style="position:absolute;top:0;left:0;width:100%;height:100%;">
                <path class="a" d="M4037.7,8357.3l5891.8,5891.8c100.6,100.6,219.7,150.9,357.3,150.9s256.7-50.3,357.3-150.9 l1318.1-1318.1c100.6-100.6,150.9-219.7,150.9-357.3c0-137.6-50.3-256.7-150.9-357.3L7745.9,8000l4216.4-4216.4 c100.6-100.6,150.9-219.7,150.9-357.3c0-137.6-50.3-256.7-150.9-357.3l-1318.1-1318.1c-100.6-100.6-219.7-150.9-357.3-150.9 s-256.7,50.3-357.3,150.9L4037.7,7642.7c-100.6,100.6-150.9,219.7-150.9,357.3C3886.8,8137.6,3937.1,8256.7,4037.7,8357.3 L4037.7,8357.3z"></path>
            </svg>
        </div>
        <div data-u="arrowright" class="jssora073" style="width:50px;height:50px;top:0px;right:30px;" data-autocenter="2" data-scale="0.75" data-scale-right="0.75">
            <svg viewbox="0 0 16000 16000" style="position:absolute;top:0;left:0;width:100%;height:100%;">
                <path class="a" d="M11962.3,8357.3l-5891.8,5891.8c-100.6,100.6-219.7,150.9-357.3,150.9s-256.7-50.3-357.3-150.9 L4037.7,12931c-100.6-100.6-150.9-219.7-150.9-357.3c0-137.6,50.3-256.7,150.9-357.3L8254.1,8000L4037.7,3783.6 c-100.6-100.6-150.9-219.7-150.9-357.3c0-137.6,50.3-256.7,150.9-357.3l1318.1-1318.1c100.6-100.6,219.7-150.9,357.3-150.9 s256.7,50.3,357.3,150.9l5891.8,5891.8c100.6,100.6,150.9,219.7,150.9,357.3C12113.2,8137.6,12062.9,8256.7,11962.3,8357.3 L11962.3,8357.3z"></path>
            </svg>
        </div>
    </div>
    
    


<div style="border: 1px solid #ccc; padding: 8px; margin-bottom: 20px;margin-top: 20px;background-color: #e4b26a;color: #fff;width:230px;" >
<span>中国-马来西亚钦州产业园区简介</span>
</div>
<div>
<img alt="" src="${pageContext.request.contextPath}/img/ma1.jpg">
</div>

<br/>

<div style="float:right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国-马来西亚钦州产业园区是一个集工业、商业、居住三位一体的产业新城。总体规划面积55平方公里，中马两国总理在2011年4月达成共识，温家宝总理提议 “广西钦州中马产业园区是双方在中国西部地区合作的第一个工业园，具有示范意义。”园区的产业定位以装备制造业、电子信息业、新能源及新材料、农副产品深加工、现代服务业为主导。
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2011年4月28日，在中国与东盟建立对话关系20周年之际，时任国务院总理温家宝访问马来西亚，与马来西亚总理纳吉布达成双方共建中马钦州产业园区的共识。同年10月21日第八届中国－东盟博览会期间，温家宝总理和纳吉布总理亲自见证两国商务部/贸工部签署中马钦州产业园区合作文件并为园区揭牌。2012年3月26日，国务院批复设立中马钦州产业园区。2012年4月1日，温家宝总理和纳吉布总理共同出席园区开园仪式并为园区奠基。2013年2月5日，时任全国政协主席贾庆林在马来西亚关丹与马来西亚总理纳吉布共同出席马中关丹产业园启动仪式。“两国双园”模式，开创了我国园区国际合作的先例。
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;依托临近东盟的优势区位，将中马钦州产业园区打造成服务中国—东盟自由贸易区的信息发布平台、贸易往来平台、项目展示及商务合作窗口。<br/>
<br/>
</div>

<svg height="20" width="1000">
  <line x1="0" y1="0" x2="1000" y2="0" style="stroke:rgb(0,0,0);stroke-width:2" />
</svg>
<div align="center">
广西一路发生态科技有限公司
<br>
www.gxlvkangkeji.com
</div>




   
</html>