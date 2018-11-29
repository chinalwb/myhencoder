## RxJava2

* Single - onSuccess
	- api.getRepos(..)
	- .subscribeOn(Schedulers.io()) // 后台
	- .observeOn(AndroidScheduler.mainThread()) // 前台
	- .subscribe(new SingleObserver<List<Repo>>() {
	...
		1. @Override public void onSubscribe(Disposable d) // 产生订阅关系的时候的回调（正在请求）
		2. Override public void onSuccess(List<Repo> repos) // observeOn 指定的线程
		3. Override public void onError(Throwable e) // observeOn 指定的线程
	})
	
	- Disposable.dispose() // onSubscribe 的输入参数。调用这个方法，取消后续任务调用

* Observable - onComplete / onNext


----

## RxJava2 的运行原理

### 1. `Single.just()` // just是瞬间发射

```
Single.just("1") // 获取被观察者对象
.subscribe(new SingleObserver<String>() {

	@Override
	public void onSubscribe(Disposable d) {
	}
	
	@Override
	public void onSuccess(String s) {
	}
	
	@Override
	public void onError(Throwable e) {
	}

})
```

### 2. Operator 操作符 - 对数据流进行操作
* map - 订阅上层被观察者，通知下层观察者

```
Single.just(1)
.map(new Function<Integer, String> {
	@Override
	public String apply(Integer integer) {
		return String.valueOf(integer);
	}
})
.subscribe(new SingleObserver<String>() {

	@Override
	public void onSubscribe(Disposable d) {
	}
	
	@Override
	public void onSuccess(String s) {
	}
	
	@Override
	public void onError(Throwable e) {
	}

})
```


### 3. Disposables.dispose()

1. Observable.just(1, 2) // 有后续

2. Observable.interval(2， TimeUnit.SECONDS).subscribe(); 有延迟

class IntervalObserver extends AtomicReference

取消的思路：

1. 操作符使得上下游直接对接
2. 多个中间环节的时候，下游直接找最上游
3. 让Scheduler不要继续发送计划任务

### 4. 线程切换
1. subscribeOn
2. observeOn 

Scheduler.io()
Scheduler.newThread()
Scheduler.mainThread()