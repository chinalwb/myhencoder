### 登录授权、TCP/IP、HTTPS

源数据 和 签名数据

用公钥解密签名数据可以看到源数据，可以证明这个数据是来自


源数据和签名数据一起发送
既然附加了源数据，那么签名数据就不必用于解密，那么签名就不需要对源数据进行加密，所以对源数据的hash进行加密就可以了。


#### Cookie / Authorization
Cookie: 登录确认 你是你
Authorization: 授权


Cookie的工作机制，最开始需要服务器端发送 Set-Cookie （在ResponseHeader中）：
Buy an apple:
Set-Cookie: cart="apple=1"
Cookie: cart="apple=1"

By a banana:
Set-Cookie: cart="apple=1&banana=1"



登录了才会生成sessionId
Set-Cookie: sessionId=123

浏览器再请求时，会带上 Cookie: sessionId=123


用Cookie管理用户偏好
Cookie: client_id=123
`client_id`


#### 使用Cookie追踪用户行为

访问网站的时候：
```<img src="http://3rd-party.com/image.jpg?from=shop.com" />```

这个追踪过程不是A网站记录用户在自己网站的浏览记录，而是有一个统一的网站记录用户所有的浏览记录。用户追踪就是由这个统一的网站来执行的。

#### XSS
Set-Cookie: HttpOnly

#### XSRF Referer
Referer, 从哪个网站访问的当前网站。
如果不是服务器认可的网站服务器可以不执行请求。

### Authorization
* Basic
Authorization: Basic (username/password(Base64ed))

Basic 后面是对用户名和密码进行Base64密码



* Bearer

OAuth2
第三方授权 和 第三方登录 区别？


-----

TCP / IP 协议族

TCP 可靠传输 保证送达
UDP 不可靠传输 不保证送达

分层的目的是为了功能的重用
 比如TCP会拆分包 和 合并包，并保证序列，以及消息重传的可靠传输
 IP为了寻址
 数据链路为了发送数据
 
 
 TCP 三次握手
 Three ways handshakes
 客户端向服务端发送TCP消息：我想给你通信
 服务端我知道你要给我发了，我也要给你发了
 客户端说我知道你要给我发了
 
 
 TCP 4次挥手
 C：我不给你发了
 S: 好的我知道了
 S: 我不再给你发了
 C: 好的我知道了
 
 -------
 
 TCP 长连接
运营商为每个网络设备分配一个端口号
如果一段时间没有活动就会关闭
保证不被关闭就是长连接
实现方式就是心跳

----
HTTPS


1. Client Hello
	TLS version
	对称加密算法
	非对称加密算法
	hash 算法
	-- 客户端随机数

2. Server Hello
	服务器选中的TLS版本、对称加密算法和非对称加密算法以及hash算法
	-- 服务器随机数
	
3. 服务器证书
	包含了非对称加密的公钥
	包含了服务器地址
	包含了证书签名
	

4. Pre-master secret

5. 根据 服务器随机数、客户端随机数、Pre-master secret 计算出来master secret
   客户端加密密钥
   服务端加密密钥
   客户端MAC Secret
   服务端MAC Secret
   
   HMAC hash-based Message Authenticate Code
   
6. 客户端说：我要使用加密通信了
7. 客户端发送：Finished