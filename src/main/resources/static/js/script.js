$(function () {
    //单选点击
    var falg = true;
    $(".dark-check").on('click',function () {
        $(this).toggleClass('action');
        if (falg) {
            cart(1);
        } else {
            cart(1);
        }
    })

    //全选点击
    $(".gap-check").click(function () {
        if (falg) {
            cart(2);
        } else {
            cart(2);
        }

    })
    $(".add").click(function(){
        var num = parseInt($(this).siblings('.num').text());
        var price = parseInt($(this).parents('.m-num').siblings('.price').find('.qian').text());
        var sum = parseInt(num+1);
        $(this).siblings('.num').text(sum);
        var sumprice=price/num;
        var all = parseInt(sum *sumprice);
        $(this).parents('.m-num').siblings('.price').find('.qian').text(all);
        var goodsId = parseInt($(this).siblings('#goodsId').text());
        var emplyNoThree = parseInt($(this).siblings('#emplyNoThree').text());
        $.ajax({
            cache:false,
            url:'http://localhost:8080/syzg/emp/addGoodslistById?goodsid='+goodsId+'&emplyNo='+emplyNoThree,
            type:'POST',
            error:function () {
                console.log("系统发生异常,请联系管理员");
            },
            success:function (str) {
            }
        })
    })
    $(".jian").click(function(){
        var num = parseInt($(this).siblings('.num').text());
        var price = parseInt($(this).parents('.m-num').siblings('.price').find('.qian').text());
        if(num >1){
            var sum = parseInt(num-1);
            $(this).siblings('.num').text(sum);
            var sumprice=price/num;
            var all = parseInt(sum *sumprice);
            $(this).parents('.m-num').siblings('.price').find('.qian').text(all);
            var goodsId = parseInt($(this).siblings('#goodsId').text());
            var emplyNoThree = parseInt($(this).siblings('#emplyNoThree').text());
            $.ajax({
            cache:false,
            url:'http://localhost:8080/syzg/emp/RemoveGoodslistById?goodsid='+goodsId+'&emplyNo='+emplyNoThree,
            type:'POST',
            error:function () {
                console.log("系统发生异常,请联系管理员");
            },
            success:function (str) {
            }
        })
        }else{
            alert('购买件数不能小于1')
        }
    })

//公用方法
    function cart(id) {
        //选择总长度
        var Alldark = $(".dark-check").length;
        //已选择长度
        var dark = $(".dark-check.action").length;
        console.log(dark)
        //其实全选和单选都一样 2是全选 1是单选
        if (id == 2 || id == 1) {
            if (Alldark != dark && id == 2) { //判断是否全选
                $(".dark-check,.gap-check").addClass('action');
                $("#del").show();
            } else if (Alldark == dark && id == 2) { //全选了后去掉全选
                $(".dark-check,.gap-check").removeClass('action');
                $("#del").hide();
            } else if (dark == 0 && id == 1) {//判断单选没有一个选中
                $("#del").hide();
            } else if (Alldark == dark && id == 1) {//如果单选按钮全亮就点亮全选按钮
                $("#del").show();
                $(".gap-check").addClass('action');
            } else if (Alldark != dark && id == 1) {//如果单选按钮未全亮就取消全选按钮的点亮
                $("#del").show();
                $(".gap-check").removeClass('action');
            }
        }
    }

    /**
     * 删除购物车方法
     */
    $("#del").click(function () {
        var strlist = "";
        var num = "";
        $(".list ul li").each(function (item, ind) {
            if ($(this).find('.dark-check').hasClass('action')) {
                $(this).remove();
                num = $(this).find('.bh').text();
                if (num != "") {
                    strlist += num + ",";
                }
            }
        })
        removeCart(strlist)
    })

    /**
     * 下单订单交互方法
     * @param list
     */
    function addOrder(list) {
        $.ajax({
            cache: false,
            url: 'http://localhost:8080/syzg/emp/addOrder?strlist=' + list,
            type: 'POST',
            error: function () {
                alert("系统发生异常，请联系管理员!");
            },
            success: function (str) {
                if (str == "order001") {
                    removeCart(list);
                    alert("下单成功!");
                } else if (str == "order002") {
                    alert("下单失败，请重新下单!");
                } else if (str == "order004") {
                    alert("余额不足!");
                }
            }
        })
    }

    /**
     * 删除购物车交互方法
     * @param list
     */
    function removeCart(list) {
        $.ajax({
            cache: false,
            url: 'http://localhost:8080/syzg/emp/removeCartById?strlist=' + list,
            type: 'POST',
            error: function () {
                alert("系统发生异常，请联系管理员!");
            },
            success: function (str) {
            }
        })
    }

    /***
     * 下单方法
     */
    $(".itme").click(function () {
        var strlist = "";
        var num = "";
        $(".list ul li").each(function (item, ind) {
            if ($(this).find('.dark-check').hasClass('action')) {
                $(this).remove();
                num = $(this).find('.bh').text();
                if (num != "") {
                    strlist += num + ",";
                }
            }
        })
        addOrder(strlist);
    })
})