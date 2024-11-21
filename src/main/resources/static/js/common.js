$(function () {
    if (!window.env) window.env = {};
    try {
        $(".list img").lazyload({effect: "fadeIn", threshold: 350});
    } catch (err) {
    }

    // 下载
    $(".downpic a").click(function () {
       var id = $(this).attr("data-id");
       /*检查登录状态 下载状态（还剩几张）*/
        $.getJSON('/downoad/'+id, function (data) {
            console.log(data)
            if (data.msg == 0) {
            } else if (data.msg == 12){
                console.log("data.msg====>"+data.msg)
                $('<iframe style="display:none;"/>').appendTo('html').attr('src', data.pic);
            /*未登录*/
            }
        });
    });
});
/*检查登录状态，通过cookie 去redis数据库校验 注意 ajax方法是不会携带cookie的*/
function checkLogin(){
   /*cookie写入界面隐藏域*/
    $.getJSON('/checkLogin',function (data) {
        console.log(data)
        $(".hiddenssid").val(data.sessionID);
        $(".logstatus").val(data.status);
        if(data.status==0){

        }else if(data.status==1){
            /*已登录 隐藏登录条 并显示 用户名字 */
            $(".register").hide();
            $(".in").hide();
            $(".loginbar").text(data.userName);
            $(".loginbar").css("color","green");
        }else if (data.status==2){

        }
    })
}




$(function () {
    var $page = $(".page");
    if ($page.length > 0) {
        $next = $page.find(':contains(下一页)');
        var $last = $next.length > 0 ? $next.prev('a') : $page.find('b:last');
        // var maxPage = ~~$last.text() * 1;
        var maxPage = $last;
        var thisPage = ~~$page.find('b:first').text() * 1;
            var urlStr = getPageUrl();
        var code = '<span class="text">共' + maxPage + '页&nbsp;&nbsp;到第</span><input type="text" name="page" /><span>页</span><a href="javascript:;" id="jump-url">确定</a>';
        if (maxPage > 0) {
            $page.append(code);
        }
        $('#jump-url').click(function () {
            var value = $page.find('input[name="page"]').val();
            value = ~~value;
            if (value === 0) {
                alert('请输入数字');
            } else if (value > maxPage) {
                alert('超过最大页了');
            } else {
                var goUrl = urlStr.replace('[@page]', value === 1 ? '' : '_' + value);
                goUrl = goUrl.replace('[#page]', value - 1);
                window.location.href = goUrl;
            }
        });
        $page.find('input[name="page"]').keyup(function (event) {
            if (event.keyCode == 13) {
                $('#jump-url').click();
            }
        });
        if ($next.length > 0) {
            var nexturl = $next.attr("href");
            console.log(nexturl);
            $(".slist ul").append('<li class="nextpage"><a href="' + nexturl + '"><p>下一页<br>更多精彩</p></a></li>');
        }
    }

    function getPageUrl() {
        var url = window.location.href.split('://');  
        var arr = url[1].split('/page/');  
        if (url[1].indexOf('/e/search/result/') > 0) { 

        } else if (arr.length > 1) {
            arr[1] = '[@page].html';
        } else {
            url[1] += '/index[@page].html';
            url[1] = url[1].replace('//', '/');
        }
        var purl = url.join('://');
        return purl;
    }
});