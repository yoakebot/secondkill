let seckill = {
    URL: {
        now: function () {
            return '/getTime';
        },
        exposed: function (secKillId) {
            return '/exposed/' + secKillId;
        },
        execute: function () {
            return '/execute'
        }
    },
    performSecKill: function (secKillId, userPhone, node) {
        //正在进行 秒杀地址，执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposed(secKillId), {}, function (data) {
            let result = eval("(" + data + ")");
            if (result.flag) {
                //执行秒杀
                let md5 = result.md5;

                $("#killBtn").one('click', function () {
                    $(this).addClass('disabled');
                    $.post(seckill.URL.execute(), {
                        secKillId: secKillId,
                        userPhone: userPhone,
                        md5: md5
                    }, function (result) {
                        let data = eval("(" + result + ")");
                        let status = data.status;
                        let info = data.info;
                        if(status == 0){
                            node.html('<span class="label label-success">' + info + '</span>');
                        }else if(status == 1){
                            node.html('<span class="label label-danger">' + info + '</span>');
                        }else if(status == 2){
                            node.html('<span class="label label-danger">' + info + '</span>');
                        }else if(status == 3){
                            node.html('<span class="label label-danger">' + info + '</span>');
                        }else if(status == 4){
                            node.html('<span class="label label-danger">' + info + '</span>');
                        }else{
                            node.html('<span class="label label-danger">' + info + '</span>');
                        }
                    })
                })

            } else {
                //未开启
                seckill.countdown(userPhone, secKillId, result.nowTime, result.startTime, result.endTime)
            }
        })
        node.show();
    },
    verifyPhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (userPhone, secKillId, nowTime, startTime, endTime) {
        let box = $("#seckill-box");
        if (nowTime < startTime) {
            //未开始
            let killTime = new Date(startTime + 1000)
            box.countdown(killTime, function (event) {
                let format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                box.html(format);
            }).on('finish.countdown', function () {
                //获取秒杀地址，执行秒杀
                seckill.performSecKill(secKillId, userPhone, box);
            })
        } else if (nowTime > endTime) {
            //已经结束
            box.html("秒杀结束了");
        } else {
            //正在进行 秒杀地址，执行秒杀
            seckill.performSecKill(secKillId, userPhone, box);
        }
    },
    //详情页逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            let killPhone = $.cookie('killPhone');
            let secKillId = params['secKillId'];
            let startTime = params['startTime'];
            let endTime = params['endTime'];

            if (!seckill.verifyPhone(killPhone)) {
                let killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
                $("#killPhoneBtn").click(function () {
                    let inputPhone = $("#killPhoneKey").val();
                    if (seckill.verifyPhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: ''})
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号格式错误！</label>').show(300);
                    }
                });
            }
            $.get(seckill.URL.now(), {}, function (result) {
                if (result) {
                    seckill.countdown(killPhone, secKillId, result, startTime, endTime);
                } else {
                    console.info(result)
                }
            });
        }
    }
}