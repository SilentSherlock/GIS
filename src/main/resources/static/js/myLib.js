/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 根据参数名paramName获取浏览器地址的参数值，失败返回null
 */
function getUrlParam(paramName) {
	//构造一个含有目标参数的正则表达式对象
	let reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
	//匹配目标参数
	let r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURIComponent(r[2]); //对参数进行解码并返回
	return null;
}
/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 转换日期格式(时间戳转换为datetime格式yyyy:mm:dd:hh:mm:ss)
 */
function timestampToTime(timestamp) {
	//时间戳为10位(s)需*1000，时间戳为13位(ms)的话不需乘1000，默认时间戳单位为ms
	let date = new Date(parseInt(timestamp));
	//yyyy
	let Y = date.getFullYear() + '-';
	let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
	let D = date.getDate() + ' ';
	let h = date.getHours() + ':';
	let m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
	let s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
	return Y + M + D + h + m + s;
}
/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 判断给定的时间是今天（返回0）、明天（返回1）还是后天（返回2）、其他（返回-1）
 */
function toRelativeDate(data) {
	let today0 = new Date(new Date().toLocaleDateString()).getTime();
	let result = (data - today0) / 1000 / 24 / 60 / 60;
	if (result >= 0 && result < 1) {
		return 0;
	} else if (result >= 1 && result < 2) {
		return 1;
	} else if (result >= 2 && result < 3) {
		return 2;
	} else
		return -1;
}
/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 判断给定的时间是今天、明天还是后天、其他(直接返回String)
 */
function toRelativeDateString(data) {
	let str = toRelativeDate(data);
	if (str === 0) {
		return "今天";
	} else if (str === 1) {
		return "明天";
	} else if (str === 2) {
		return "后天";
	} else
		return "其他";
}
/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 将给定的时间戳转换为当天的xx小时:xx分
 */
function timestampToTimeOfTheDay(timestamp) {
	let date = new Date(timestamp);
	return date.getHours() + ":" + date.getMinutes();
}
/**
 * 作者: lwh
 * 时间: 2019.12.10
 * 描述: 将给定的时间戳转换为星期几
 */
function getDateWeek(date) {
	let day = new Date(date);
	let today = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	return today[day.getDay()];
}

