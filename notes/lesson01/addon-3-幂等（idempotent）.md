###  DELETE是幂等吗？
https://stackoverflow.com/questions/4088350/is-rest-delete-really-idempotent

-------

https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html

9.1.2 Idempotent Methods
Methods can also have the property of "idempotence" in that (aside from error or expiration issues) the side-effects of N > 0 identical requests is the same as for a single request. The methods GET, HEAD, PUT and DELETE share this property. Also, the methods OPTIONS and TRACE SHOULD NOT have side effects, and so are inherently idempotent.

----

idempotence:: the side-effects of N > 0 identical requests is the same as for a single request.

幂等指的是 同一个请求执行多次所产生的 side-effect 跟执行一次产生的 side-effect 是一样的. 
这个HTTP/1.1中的定义跟老师说的 -- 幂等（即反复调用多次时会得到相同的结果）
感觉还是有一点点非常极其细微的差异。就是在于 “结果” 是不是 “side-effect”

此处side-effect 我认为就是指的服务器上的状态，如果这样考虑的话，多次删除和一次删除对服务器所产生的side-effect是一样的，所以就幂等。
如果说，side-effect 等于服务器状态加上客户端得到的响应，那就不一样了，明显第二次以及之后的删除会得到与第一次不同的response。

我刚才sof上看到，关于DELETE方法是否是幂等的有两种观点，有人认为side-effect != server status. 另外一部分人则是相反观点。 不过我认为是幂等的。原因如下：

在Head First Servlet & JSP 中讨论幂等这个概念的时候举例如下：
1. 用户正在购物网站买一本书
2. 她填写了账户和密码之后提交了订单（POST）
3. 服务器收到请求，开始联系银行扣款
4. 但用户迟迟没有得到订单成功的反馈，此时她又点击了一次提交（第二次POST）
5. 服务器收到请求，再次联系银行扣款...

从这个例子中引出了POST不是幂等。

----

而对于DELETE来说，我认为没有上述场景中类似的side-effect.....  

个人看法哈。分享给大（自）家（己）。



----------------



http://leedavis81.github.io/is-a-http-delete-requests-idempotent/
第一个回复就是截图的文字

如果说，side-effect 等于服务器状态加上客户端得到的响应，那就不一样了，明显第二次以及之后的删除会得到与第一次不同的response。 
-----
第二次以及以后的删除请求是否返回与第一次删除请求同样的response，由服务器端实现。
当第二次以DELETE方法请求 http://hencoder.com/user/123的时候
当然服务器端可以发现123这个user已经被删除了，他完全可以返回与第一次同样的response。
他也可以返回类似用户不存在这样的response。


正如上面截图最后一句说的：
Whether the second DELETE should give the same HTTP response as the first one, is imho not a requirement for idempotency.

第二次处理DELETE方法的请求是否要返回与第一个请求一样的response，IMHO（in my humble opinion, 恕我之言），这不是HTTP/1.1 中关于幂等性（idempotency）的要求。

