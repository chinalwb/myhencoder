#### Java多线程和线程同步

* New Thread
```
Thread thread = new Thread() {
	public void run() {
		...
	}
};
thread.start();
```

`thread.start -> thread.start0()`


* Runnable
```
Runnable runnable = new Runnable() {
	run() {
		...
	}
}
Thread thread = new Thread(runnable);
thread.start();
```

* ThreadFactory
```
ThreadFactory factory = new ThreadFactory
```


* Executors
```
Runnable runnable = ..
Executor executor = Executors.newCachedThreadPool();
exector.execute(runnable)
```

- 创建单个线程的线程池
`newSingleThread`

- 创建固定数量的线程池
`Executors.newFixedThreadPool(nThreads)`

* Callable - 有返回值的线程
```
Callable<String> callable = new Callable<String>() {
	public String call() {
		...
		return "xx";
	}
};

ExecutorService executor = Executors.newCachedThreadPool();
Future<String> future = executor.submit(callable);
try {
	String result = future.get(); // get方法会阻塞！
} catch (InterruptedException | ExecutionException e) {
	
}
```

* synchronized 方法 、 synchronized代码块
	- 实例方法的 synchronized 关键字的 monitor 是对象
	- 静态方法的 synchronized 关键字的 monitor 是 类名.class


* 死锁

* 悲观锁 - 先取得锁再执行
* 乐观锁 - 先尝试执行，如果发现资源改变，再尝试取得锁

* volatile - 让对变量的操作变成原子性 和 同步性
private volatile int a = 0;

a = a + 1 // 这个不能保证 volatile a 的原子性

volatile 只能对基本数据类型保证原子性


* AtomicInteger / AtomicLong / AtomicReference ...
AtomicInteger.incrementAndGet(); // ++i

* Lock
	- ReentrantLock 
	```
	lock.lock();
	try {
		x = 0;
		.... /// throw new exception
		y = 0;
	} finally {
		lock.unlock();	
	}
	
	```
	- lock = ReentrantReadWriteLock();
	- ReentrantReadWriteLock.ReadLock = lock.readLock();
	- ReentrantReadWriteLock.WriteLock = lock.writeLock();
