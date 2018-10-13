### 补充笔记 - 2 - GET请求中追加的随机数

url 请求get方式 为什么加 随机数作为参数
----
IE浏览器下使用GET发送请求时，如果两次请求的地址和参数相同，在不刷新页面的情况下，浏览器会缓存第一次请求的内容，服务端更新后浏览器仍然显示第一次的内容。 
--- https://blog.csdn.net/happydecai/article/details/78760485



我自己做了一个测试
在springMVC（mycc）里面加了一个 /api/testGet ：

```
@RequestMapping(value = "/api/testGet", method = RequestMethod.GET, consumes="application/json", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String testGet() {
		JSONObject resultJson = new JSONObject(true);
		resultJson.put("k1", "v1");
		resultJson.put("k2", System.currentTimeMillis());
		return resultJson.toJSONString();
	}
```
我以为请求 http://localhost:8080/mycc/api/testGet的时候，
k2的时间戳会一直是第一个请求的返回值

然后我在PostMan里面测试
发现每次请求都会返回不同的 k2 时间戳

这说明我原来的猜测是错误的，每次GET请求都会发送到服务器上
并且服务器不会原样返回数据，而是每次都会当做一个新的请求来处理。

那么就引出了上面提到的那个问题
我印象中之前做web开发的时候的确是需要在请求串后追加传递一个随机数的啊！
原因就是最上面解释的
浏览器会缓存GET请求的结果，如果发现两次请求的地址是一模一样的，浏览器就不会发送请求到服务器了，而是直接使用了缓存的结果。
如果加上了随机数，那么对浏览器来说每次请求都是全新的，这样就不会返回缓存里面的数据了。

