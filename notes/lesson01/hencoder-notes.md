## HTTP的原理和工作机制



#### HTTPS 为什么是安全的


#### TCP连接，长连接


------

#### HTTP是什么
* HyperText Transfer Protocol 超文本传输协议
* HTML - HyperText Markup Language

#### HTTP的工作方式
* Request

请求行 -- GET(**method**) /users(**path**) HTTP/1.1(**HTTP version**)

Header -- 
Host: api.github.com

Body -- body....

* Response

状态行 -- HTTP/1.1(**HTTP version**) 200(**status code**) OK(**status message**)

Headers -- content type

Body?


* Request Methods
	- GET
		- 获取资源，没有body
	- POST
		- 增加或修改，有body
	- PUT
		- 修改，有body
	- DELETE
		- 删除，没有body
	- HEAD
		- 跟GET类似，不返回body
		- 下载的时候，查询文件多大，以及是否支持断点续传等信息

GET / PUT / DELETE 幂等E. 幂等（即反复调用多次时会得到相同的结果）
POST 非幂等

#### 状态码
* 1xx 
* 2xx 成功
	- 
* 3xx 重定向
	- 301 Moved Permanently - 搜索引擎会用到
	- 302 临时重定向
	- 304 内容没有改变
* 4xx 客户端错误
	- 400 请求有问题
	- 401 没有授权
        - HTTP Status 405 – Method Not Allowed -- 如果某个API是用GET请求定义的，但客户端发送请求的时候使用了POST, 客户端就会收到这个状态码
* 5xx 服务器错误
	- 500 服务器错误

不同的状态码是为了方便软件开发人员进行调试


请求header里面的content-type::
Content-Type: 内容类型，给客户端用来解析



纯文字表单 application/x-www-form-urlencoded

@FormUrlEncoded -- 往body里面加东西
@POST
@Field -- body里面的内容拼接


* 带文件的表单
multipart/form-data; boundary=-----....（**boundary的内容是分界线**）
一般用于传输包含二进制内容的多项内容
@Multipart
@Part("photo") RequestBody photo


* application/json: json形式
* image/jpeg / application/zip ...