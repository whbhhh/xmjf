/**
 * Created by lp on 2017/12/9.
 */

$(function () {
    /**
     * 页面加载完毕
     *    填充表格数据
     *    填充页码
     */
    loadInvestListData();


    /**
     * 期限查询切换控制
     */
    $(".tab").click(function () {
        /**
         * 1.当前节点选中
         * 2.其他选中节点选中状态取消
         */
        $(this).addClass("list_active");
        $(".tab").not($(this)).removeClass("list_active");
        // 获取点击的期限类别值
        var index=$(this).index();
        var isHistory=0;
        if(index==4){
            isHistory=1;
        }
        loadInvestListData(isHistory,null,index);
    })



});

/**
 * 1.是否为历史项目  isHistory
 * 2.项目类别  itemType
 * 3.项目期限   itemCycle
 * 4.翻页页码  pageNum
 * 5. 每页数据大小  pageSize
 */
function  loadInvestListData(isHistory,itemType,itemCycle,pageNum,pageSize) {
    var params={};
    params.isHistory=0;// 默认查询可投标项目
    if(!isEmpty(isHistory)){
        params.isHistory=isHistory;
    }
    if(!isEmpty(itemType)){
        params.itemType=itemType;
    }
    if(!isEmpty(itemCycle)&&itemCycle!=0){
        params.itemCycle=itemCycle;
    }
    if(!isEmpty(pageNum)){
        params.pageNum=pageNum;
    }
    if(!isEmpty(pageSize)){
        params.pageSize=pageSize;
    }

    /**
     * 发送ajax 请求后台数据
     */
    $.ajax({
        type:"post",
        url:ctx+"/basItem/queryBasItemsByParams",
        data:params,
        dataType:"json",
        success:function (data) {
            var paginator=data.paginator;// 分页信息
            var list=data.list;
            if(list.length>0){
                initTrHtml(list);
                initPageHtml(paginator);
            }else{
                alert("暂无数据!");
            }
        }
    })
}

/**
 * 填充表格行记录数据
 * @param list
 */
function  initTrHtml(list) {
    if(list.length>0){
        var trs="";
        for(var i=0;i<list.length;i++){
            var tempData=list[i];
            trs=trs+"<tr>";
            trs=trs+"<td><strong>"+tempData.itemRate+"</strong><span>%";
            if(!isEmpty(tempData.itemAddRate)){
                trs=trs+"+"+tempData.itemAddRate+"%"
            }
            trs=trs+"</span></td>";
            trs=trs+"<td>"+tempData.itemCycle+"|";
            if(tempData.itemCycleUnit==0||tempData.itemCycleUnit==1){
                trs=trs+"天";
            }
            if(tempData.itemCycleUnit==2){
                trs=trs+"月";
            }
            if(tempData.itemCycleUnit==3){
                trs=trs+"季";
            }
            if(tempData.itemCycleUnit==4){
                trs=trs+"年";
            }
            trs=trs+"</td>";


            trs=trs+"<td>";
            trs=trs+tempData.itemName;
            if(tempData.itemIsnew==1){
                trs=trs+"<strong class='colorful' new>NEW</strong>";
            }
            if(tempData.itemIsnew==0){
                if(tempData.moveVip==1){
                    trs=trs+"<strong class='colorful' app>APP</strong>";
                }
            }
            if(tempData.itemIsnew==0){
                if(tempData.moveVip==0){
                    if(tempData.itemIsrecommend==1){
                        trs=trs+"<strong class='colorful' hot>HOT</strong>";
                    }
                }
            }
            if(tempData.itemIsnew==0){
                if(tempData.moveVip==0){
                    if(tempData.itemIsrecommend==0){
                        if(!isEmpty(tempData.password)){
                            trs=trs+"<strong class='colorful' psw>LOCK</strong>";
                        }
                    }
                }
            }
            trs=trs+"</td>";

            trs=trs+"<td class='trust_range'>";
            if(tempData.total>90){
                trs=trs+"A+";
            }
            if(tempData.total>85 && tempData.total<=90){
                trs=trs+"A";
            }
            if(tempData.total<=85 && tempData.total>75){
                trs=trs+"A-";
            }

            if(tempData.total<=75 && tempData.total>65){
                trs=trs+"B";
            }
            trs=trs+"</td>";

            trs=trs+"<td class='company'>";
            trs=trs+"<img src='../img/logo.png' >";
            trs=trs+"</td>";

            // 暂时显示进度值  后期优化
            trs=trs+"<td>+"+tempData.itemScale+"</td>";

            trs=trs+"<td>";
            var status=tempData.itemStatus;
            if(status==1){
                trs=trs+"<p><a href='' ><input class=‘countdownButton’ valid type=‘button’ value='即将开标'></a></p>";
            }
            if(status==10||status==13||status==18){
                trs=trs+"<p class='left_money'>可投金额"+tempData.itemOngoingAccount+"元</p>" +
                    "<p><a href=''><input valid type='button' value='立即投资'></a></p>";
            }
            if(status==20){
                trs=trs+ "<p><a href=''><input not_valid type='button' value='已抢完'></a></p>";
            }
            if(status==30||status==31){
                trs=trs+ "<p><a href=''><input not_valid type='button' value='还款中'></a></p>";
            }
            if(status==32){
                trs=trs+ "<p style=‘position: relative’><a href='' class=‘yihuankuan’><input not_valid type='button' value='已还款'></a></p>";
            }
            if(status==23){
                trs=trs+ "<p><a href=''><input not_valid type='button' value='已满标'></a></p>";
            }
            trs=trs+"</td>";
            trs=trs+"</tr>";
        }
        $("#pcItemList").html(trs);
    }
}


function  initPageHtml(paginator) {
    var navigatepageNums=paginator.navigatepageNums;
    if(navigatepageNums.length>0){
        var lis="";
        for(var j=0;j<navigatepageNums.length;j++){
            var page=navigatepageNums[j];

            var href="javascript:toPageData("+page+")";
            if(page==paginator.pageNum){
                lis=lis+"<li class='active'><a href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }else{
                lis=lis+"<li ><a href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }
        }
        $("#pages").html(lis);
    }
}



function initItemData(itemType) {
    /**
     * 获取类别
     */
    var itemType=itemType;

    var index=$(".list_active").index();
    var isHistory=0;
    if(index==4){
        isHistory=1;
    }
    loadInvestListData(isHistory,itemType,index);

}


function toPageData(page) {
    var pageNum=page;
    var itemType=$("#itemType").val();
    var index=$(".list_active").index();
    var isHistory=0;
    if(index==4){
        isHistory=1;
    }
    loadInvestListData(isHistory,itemType,index,pageNum);
}