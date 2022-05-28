$(function () {
    if (!window.env) window.env = {};
    try {
        $(".list img").lazyload({effect: "fadeIn", threshold: 350});
    } catch (err) {
    }

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
    $(".btn-phone,.btn-qq,.btn-group").hover(function () {
        $(this).find("div").fadeIn("fast");
    }, function () {
        $(this).find("div").fadeOut("fast");
    });
    $('.gotop .btn-top').click(function () {
        $('body,html').animate({scrollTop: '0px'}, 500);
    });
    $(window).scroll(function () {
        if ($(window).scrollTop() > 200) {
            $(".gotop .btn-top").css("display", "block");
        } else {
            $(".gotop .btn-top").hide();
        }
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





(function (a, b, c, d) {
    var e = a(b);
    a.fn.lazyload = function (c) {
        function i() {
            var b = 0;
            f.each(function () {
                var c = a(this);
                if (h.skip_invisible && !c.is(":visible")) return;
                if (!a.abovethetop(this, h) && !a.leftofbegin(this, h)) if (!a.belowthefold(this, h) && !a.rightoffold(this, h)) c.trigger("appear"), b = 0; else if (++b > h.failure_limit) return !1
            })
        }

        var f = this, g, h = {
            threshold: 0,
            failure_limit: 0,
            event: "scroll",
            effect: "show",
            container: b,
            data_attribute: "src",
            skip_invisible: !0,
            appear: null,
            load: null
        };
        return c && (d !== c.failurelimit && (c.failure_limit = c.failurelimit, delete c.failurelimit), d !== c.effectspeed && (c.effect_speed = c.effectspeed, delete c.effectspeed), a.extend(h, c)), g = h.container === d || h.container === b ? e : a(h.container), 0 === h.event.indexOf("scroll") && g.bind(h.event, function (a) {
            return i()
        }), this.each(function () {
            var b = this, c = a(b);
            b.loaded = !1, c.one("appear", function () {
                if (!this.loaded) {
                    if (h.appear) {
                        var d = f.length;
                        h.appear.call(b, d, h)
                    }
                    a("<img />").bind("load", function () {
                        c.hide().attr("src", c.data(h.data_attribute))[h.effect](h.effect_speed), b.loaded = !0;
                        var d = a.grep(f, function (a) {
                            return !a.loaded
                        });
                        f = a(d);
                        if (h.load) {
                            var e = f.length;
                            h.load.call(b, e, h)
                        }
                    }).attr("src", c.data(h.data_attribute))
                }
            }), 0 !== h.event.indexOf("scroll") && c.bind(h.event, function (a) {
                b.loaded || c.trigger("appear")
            })
        }), e.bind("resize", function (a) {
            i()
        }), /iphone|ipod|ipad.*os 5/gi.test(navigator.appVersion) && e.bind("pageshow", function (b) {
            b.originalEvent.persisted && f.each(function () {
                a(this).trigger("appear")
            })
        }), a(b).load(function () {
            i()
        }), this
    }, a.belowthefold = function (c, f) {
        var g;
        return f.container === d || f.container === b ? g = e.height() + e.scrollTop() : g = a(f.container).offset().top + a(f.container).height(), g <= a(c).offset().top - f.threshold
    }, a.rightoffold = function (c, f) {
        var g;
        return f.container === d || f.container === b ? g = e.width() + e.scrollLeft() : g = a(f.container).offset().left + a(f.container).width(), g <= a(c).offset().left - f.threshold
    }, a.abovethetop = function (c, f) {
        var g;
        return f.container === d || f.container === b ? g = e.scrollTop() : g = a(f.container).offset().top, g >= a(c).offset().top + f.threshold + a(c).height()
    }, a.leftofbegin = function (c, f) {
        var g;
        return f.container === d || f.container === b ? g = e.scrollLeft() : g = a(f.container).offset().left, g >= a(c).offset().left + f.threshold + a(c).width()
    }, a.inviewport = function (b, c) {
        return !a.rightoffold(b, c) && !a.leftofbegin(b, c) && !a.belowthefold(b, c) && !a.abovethetop(b, c)
    }, a.extend(a.expr[":"], {
        "below-the-fold": function (b) {
            return a.belowthefold(b, {threshold: 0})
        }, "above-the-top": function (b) {
            return !a.belowthefold(b, {threshold: 0})
        }, "right-of-screen": function (b) {
            return a.rightoffold(b, {threshold: 0})
        }, "left-of-screen": function (b) {
            return !a.rightoffold(b, {threshold: 0})
        }, "in-viewport": function (b) {
            return a.inviewport(b, {threshold: 0})
        }, "above-the-fold": function (b) {
            return !a.belowthefold(b, {threshold: 0})
        }, "right-of-fold": function (b) {
            return a.rightoffold(b, {threshold: 0})
        }, "left-of-fold": function (b) {
            return !a.rightoffold(b, {threshold: 0})
        }
    })
    // 分页
})(jQuery, window, document);
$(function () {
    var $page = $(".page");
    if ($page.length > 0) {
        $next = $page.find(':contains(下一页)');
        var $last = $next.length > 0 ? $next.prev('a') : $page.find('b:last');
        // var maxPage = ~~$last.text() * 1;
        var maxPage = last;
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