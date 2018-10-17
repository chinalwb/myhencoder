#### Retrofit && HTTP

可以给Android 也可以给Java用




@GET("users")



api.getRepos()
* enqueue(new CallBack<ResponseBody>) // 异步
* execute // 同步



1. 定义一个GithubService 接口
2. 定义一个Retrofit 用builder来做
3. retrofit.create(GithubService.class) 得到GithubService 接口的对象实例





```
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
```

```
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);
```
--------

`enqueue` 不是线性执行请求，而是并行执行。只有当请求太多的时候（超过64个？正在执行），那么多出来的请求会加到队列，排队等待被执行。



Proxy.newProxyInstance(
1. service.getClassLoader()
2. new Class<?>[] { service }
3. new InvocationHandler() {
	..
	public Object invoke
}

设计模式：

* Adapter: 将A接口转换为B接口，client调用B接口

* Builder: 好处：
	* 如果先构造一个对象 然后再改属性的话，构造对象的初始化过程会重复执行 浪费效率
	* 参数太多的时候，builder 清晰

```
Person person = new Person.Biulder()
	.name("zhukai")
	.gender("male")
	.age(31)
	.build();
```

```
Person person = new Person();
person.setName("xx")
person.gender("male")
```

1. ServiceMethod
	- 
2. OKHttpCall
3. CallAdapter 
	- serviceMethod.callAdapter.adapt(okHttpCall);
4. Converter


-------

问题：
1. `Proxy.newProxyInstance` 是如何创建出来一个interface的实例对象的？
2. `enqueue` 源码解读









