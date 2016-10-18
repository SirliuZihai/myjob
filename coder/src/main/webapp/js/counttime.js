var i=0;

function timedCount()
{
	i=i+1;
postMessage(i+"秒");
setTimeout("timedCount()",500);
}

timedCount()