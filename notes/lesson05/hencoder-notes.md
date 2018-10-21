#### OKHttp

```
OkHttpClient client = new OkHttpClient();
client.newCall(new Request.Builder()
	.url("http://...")
	.build())
	.enqueue(new Callback() {
		onFailure...
		onResponse...
	})
);
```

#### ExecutorService

#### enqueue
* runningAsyncCalls
* readyAsyncCalls

#### AsyncCall

#### getResponseWithInterceptChain

#### Dispatcher
* maxRequest - 64 by default
* maxRequestPerHost - 5 by default


#### InterceptorChain 重点！



