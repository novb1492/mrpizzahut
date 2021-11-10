var result;
function requestOrder(){
	var arr=document.getElementsByName('payGroup');
		var kind;
       for(var i=0;i<arr.length;i++){
            if(arr[i].checked == true) {
				kind=arr[i].value;
				break;
            }
       }
   	if(kind==0){
		alert('결제수단을 선택해주세요');
		return;
	}
	let	data=JSON.stringify({
         "mobile1":document.getElementById('view_delivery_phone1').value,
          "mobile2":document.getElementById('view_delivery_phone2').value,
           "mobile3":document.getElementById('view_delivery_phone3').value,
           "name":document.getElementById('gift_from_nm').value,
           "kind":kind,
           "coupon":document.getElementById('coupons').value
	}); 
	requestToServer('/app/tryOrder',data);
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

