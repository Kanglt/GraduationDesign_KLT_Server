if(typeof(this["Realtime"])=="undefined")
	this.Realtime={};
Realtime.getMessage=function(successfulCallback,errorCallback){
	if(arguments.length!=2){
		alert("调用Realtime.getMessage方法时提供的参数个数不正确，该方法需要下列参数successCallback,errorCallback");return;}
	var params = {};
	WebServiceRealtime.service("Realtime.service?getMessage",params,successfulCallback,errorCallback);
};
Realtime.newMessage=function(messageType,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用Realtime.newMessage方法时提供的参数个数不正确，该方法需要下列参数messageType,successCallback,errorCallback");return;}
	var params = {messageType:messageType};
	WebService.service("Realtime.service?newMessage",params,successfulCallback,errorCallback);
};
