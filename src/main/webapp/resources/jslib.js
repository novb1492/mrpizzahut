var result;

function  changePage(num){
            var keyword=getParam('keyword');
            var page=getParam('page');
            page=(page*1);
            location.href="/app/admin/menu?scope=메뉴수정삭제&page="+(page+num)+"&keyword="+keyword;
}
function insertMenu(formId,text){
	var myForm = document.getElementById(formId);
	let formdata=new FormData(myForm);
	formdata.append("text",text);
	var result=requestToFormPost('/app/admin/menu/insert',formdata);
	alert(result.message);
	if(result.flag){
		location.reload();
	}
}
function requestToFormPost(url,data){
	var result;
	 $.ajax({
            type : 'POST',
            url : url,
            data : data,
            dataType : 'json',
            enctype: 'multipart/form-data',
			processData: false,	
			contentType: false,
			async: false,
            success : function(response){
                result=response;
            }
        });
        return result;
}
function requestOrder(){
	var carr=document.getElementsByClassName('coupon');
	var coupons='*';
	for(var i=0;i<carr.length;i++){
		coupons+=carr[i].value+'/';
	}
	coupons=coupons.replace('*',"");
	var arr=document.getElementsByName('payGroup');
		var kind;
       for(var i=0;i<arr.length;i++){
            if(arr[i].checked == true) {
				kind=arr[i].value;
				break;
            }
       }
   	if(kind==undefined||kind==null){
		alert('결제수단을 선택해주세요');
		return;
	}
	let	data=JSON.stringify({
         "mobile1":document.getElementById('view_delivery_phone1').value,
          "mobile2":document.getElementById('view_delivery_phone2').value,
           "mobile3":document.getElementById('view_delivery_phone3').value,
           "name":document.getElementById('gift_from_nm').value,
           "kind":kind,
           "coupon":coupons
	}); 
	 var result=requestToServer('/app/tryOrder',data);
	 if(!result.flag){
		alert(result.message);
		return;	
	}
	if(kind=='card'){
		card(result);
	}else if(kind=='vbank'){
		vbank(result);
	}else if(kind=='kpay'){
		 window.open(result.message, 'width=500, height=900','resizable=no');
	}
	
	
}
function vbank(result) {
    SETTLE_PG.pay({
        "env": "https://tbnpg.settlebank.co.kr",
        "mchtId": result.mchtId,
        "method": "vbank",
        "trdDt": result.trdDt,    
        "trdTm": result.trdTm,
        "expireDt":result.expireDt,
        "mchtTrdNo":result.mchtTrdNo,
        "mchtName": "MrPizzaHut",
        "mchtEName": "MrPizzaHut",
        "pmtPrdtNm": result.itemName,
        "trdAmt": result.trdAmt,
        "mchtCustId":result.mchtCustId,
        "mchtParam":result.mchtParam,
        "notiUrl": "http://kim80800.iptime.org:8085/app/settle/callback?scope=noti",
        "nextUrl": "http://localhost:8085/app/settle/callback?scope=confrim",
        "cancUrl": "http://localhost:8085/app/settle/callback?scope=cancle",
        "pktHash": result.pktHash,
        "ui": {
            "type": "popup",
            "width": "430",
            "height": "660"
        }
        }, function(rsp){
            //iframe인경우 온다고 한다
            console.log('통신완료');
            console.log(rsp);
        });      


}
 function card(result) {
 
        SETTLE_PG.pay({
            "env": "https://tbnpg.settlebank.co.kr",
            "mchtId": result.mchtId,
            "method": "card",
            "trdDt": result.trdDt,    
            "trdTm": result.trdTm,
            "mchtTrdNo": result.mchtTrdNo,
            "mchtName": "MrPizzaHut",
            "mchtEName": "MrPizzaHut",
            "pmtPrdtNm": result.itemName,
            "trdAmt": result.trdAmt,
            "mchtCustId":result.mchtCustId,
            "mchtParam":result.mchtParam,
            "notiUrl": "http://kim80800.iptime.org:8085/app/settlebank",
            "nextUrl": "http://localhost:8085/app/settle/callback?scope=confrim",
            "cancUrl": "http://localhost:8085/app/settle/callback?scope=cancle",
            "pktHash": result.pktHash,
            "ui": {
                "type": "popup",
                "width": "430",
                "height": "660"
            }
            }, function(rsp){
                //iframe인경우 온다고 한다
                console.log('통신완료');
                console.log(rsp);
            });      
    
    
}
function deletechoice(){
	var arr = document.getElementsByName("cart_item");
	var arr2=[];
       for(var i=0;i<arr.length;i++){
            if(arr[i].checked == true) {
				arr2[arr2.length]=arr[i].value;
            }
       }
    let	data=JSON.stringify({
         "arr":arr2
	}); 
	var result=requestToServer('/app/deleteCart',data);
	if(result.flag){
		location.reload();
		return;	
	}
	alert(result.message);
}
function checkAll(){
	var arr = document.getElementsByName("cart_item");
        for(var i=0;i<arr.length;i++){
            if(arr[i].checked == false) {
				arr[i].checked=true;
            }else{
				arr[i].checked=false;
			}
        }
}
function test(bid,num,originPrice){
	let	data=JSON.stringify({
         "bid":bid,
         "num":num
	}); 
	var re=requestPutToServer('/app/changeCount',data);
	if(re.flag){
		document.getElementById(bid+'count').value=re.count;
		$('.'+bid+'num').empty();
		$('.'+bid+'num').append(re.count);
		$('.'+bid+'price').empty();
		$('.'+bid+'price').append(re.price);
		var totalPrice=document.getElementById('totalPrice').value;
		totalPrice=totalPrice*1;
		totalPrice=totalPrice-originPrice+re.price;
		$('.totalPrice').empty();
		$('.totalPrice').append(totalPrice);

	}else{
		if(re.message=="0"){
			$('#'+bid+'body').empty();
			return;
		}
		alert(re.message);
	}
}
function requestToServer(url,data){ 
   $.ajax({
       type: 'POST',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function requestToServer2(url,data){
   $.ajax({
       type: 'POST',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function requestPutToServer(url,data){
   $.ajax({
       type: 'PUT',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function requestDelteToServer(url){
   $.ajax({
       type: 'DELETE',
       url: url,
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function requestGetToServer(url){
   $.ajax({
       type: 'GET',
       url: url,
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function getIdValue(id){
	return document.getElementById(id).value;
}
function disabledById(id,flag){
	document.getElementById(id).disabled=flag;	
}
function sendSns(phoneOrEmail,kind,detail){
	console.log('sendSns');
	 var data=JSON.stringify({
			"phoneOrEmail":phoneOrEmail,
			"kind":kind,
			"detail":detail
	});
	var result=requestToServer('/demo4/sns/send',data);
	alert(result.message);
	if(result.flag){
		disabledById('sendNum',false);
	}
}
 function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
       var temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}
function getRadioValue(name){
	 var obj_length = document.getElementsByName(name).length;
        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName(name)[i].checked == true) {
               return document.getElementsByName(name)[i].value;
            }
        }
}

