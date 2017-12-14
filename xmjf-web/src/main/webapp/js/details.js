/**
 * Created by lp on 2017/12/11.
 */
$(function () {
    $('#rate').radialIndicator();
    var val=$("#rate").attr("data-val");
    var radialObj=$("#rate").data('radialIndicator');
    radialObj.option("barColor","orange");
    radialObj.option("percentage",true);
    radialObj.option("barWidth",10);
    radialObj.option("radius",40);
    radialObj.value(val);


    $('#tabs div').click(function () {
        $(this).addClass('tab_active');
        var show=$('#contents .tab_content').eq($(this).index());
        show.show();
        $('#tabs div').not($(this)).removeClass('tab_active');
        $('#contents .tab_content').not(show).hide();
        if($(this).index()==2){
            /**
             * 获取项目投资记录
             *   ajax 拼接tr
             *    追加tr 到 recordList
             */
           // alert("投资用户列表");
            //loadInvestRecodesList($("#itemId").val());
        }
    });


});


function toRecharge() {
    $.ajax({
        type:"post",
        url:ctx+"/user/userAuthCheck",
        dataType:"json",
        success:function(data){
            if(data.code==200){
                window.location.href=ctx+"/account/rechargePage";
            }else{
                layer.confirm(data.msg, {
                    btn: ['执行认证','稍后认证'] //按钮
                }, function(){
                        window.location.href=ctx+"/user/auth";
                });
            }
        }
    })
}