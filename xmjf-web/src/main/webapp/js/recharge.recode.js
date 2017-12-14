$(function () {
    loadRechargeRecodesData();
});



function loadRechargeRecodesData() {
    $.ajax({
        type:"post",
        url:ctx+"/account/queryRechargeRecodesByUserId",
        dataType:"json",
        success:function (data) {
            var paginator=data.paginator;// 分页信息
            var list=data.list;
            if(list.length>0){

                initDivsHtml(list);
            }else{
                alert("记录不存在!");
            }
        }
    })

}

function initDivsHtml(list) {
    /**
     *  <div class="table-content-first">
     2017-12-01
     </div>
     <div class="table-content-center">
     1元
     </div>
     <div class="table-content-first">
     成功
     </div>
     */
    if(list.length>0){
        var divs="";
        for(var j=0;j<list.length;j++){
            var tempData=list[j];
            divs=divs+"<div class='table-content-first'>";
            divs=divs+tempData.auditTime+"</div>";
            divs=divs+"<div class='table-content-center'>";
            divs=divs+tempData.actualAmount+"元"+"</div>";
            divs=divs+"<div class='table-content-first'>";
            var status=tempData.status;
            if(status==0){
                divs=divs+"支付失败";
            }
            if(status==1){
                divs=divs+"已支付";
            }
            if(status==2){
                divs=divs+"待支付";
            }
            divs=divs+"</div>";
        }
        $("#rechargeList").html(divs);
    }
}