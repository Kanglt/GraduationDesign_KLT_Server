if(typeof(this["Utils"])=="undefined")
	this.Utils={};
Utils.getNewId=function(successfulCallback,errorCallback){
	if(arguments.length!=2){
		alert("调用Utils.getNewId方法时提供的参数个数不正确，该方法需要下列参数successCallback,errorCallback");return;}
	var params = {};
	WebService.service("Utils.service?getNewId",params,successfulCallback,errorCallback);
};
