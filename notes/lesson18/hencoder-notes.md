#### 线程间通信的原理，以及Android中的多线程

* thread.stop - 结束线程 - 不建议使用，因为结果是不可预期的
* thread.interrupt() - 中断线程，不会立即执行，而是在线程中加一个中断标志 - 需要与被中断线程配合使用 - 在被中断的线程中可以用 `thread.isInterrupted()` 判断该线程是否被中断。

	- `Thread.interrupted()` 也能判断当前线程是否已经被中断，跟成员方法 `thread.isInterrupted()` 的区别是，静态方法 `Thread.interrupted()` 除了返回是否被中断之外还会 reset 这个标志，也就是说 如果 Thread.interrupted() 返回了true 则下次调用成员方法 `thread.isInterrupted()` 会返回false


Android中也可以用这个API：
```
SystemColor.sleep(2000)
```
他不会抛出 InterruptedException


	
* wait / notify / notifyAll()
- 在调用这些方法之前必须持有相关对象的锁（必须先获得 monitor）
- wait 会释放 monitor

* thread.join
- 调用这个方法的线程会等 thread 线程结束之后继续执行

* Thread.yield
- 当前线程暂时让出时间片 让其他线程先执行



#### Android Looper

* Looper中有一个死循环，循环从消息队列中取出并执行任务
- Looper中有一个MessageQueue

* Handler && HandlerThread ???

```
Handler handler = new Handler(??); // 这个里面可以传入一个Looper， 传入哪个线程的looper 消息就会在哪个线程处理

handler.post(new Runnable() {
	public void run() {
	...
	}
});
```

new Handler的时候
如果没有传入Looper，则会默认用当前执行线程的looper
如果传入了looper，则会用传入的looper

looper.loop 实现了一个死循环
如果这个looper是由一个子线程启动的，则handler的handleMessage会在子线程执行
如果传给Handler的looper是主线程的，则handleMessage会在主线程执行

* ActivityThread main 方法中执行了 Looper looper() 进入了死循环 但APP还能运行，并且为什么他还不会ANR ???


* GC root
	- Thread
	- static
	- Native

	

- Executor - 后台任务执行 但不需要推到主线程
- AsyncTask - 后台任务需要跟前台交互的最小的任务
- HandlerThread - 他 extends Thread. 默认带looper。extends HandlerThread 可以
- Thread - 最最小的任务可能用这个更好

* Service && IntentService



`HandlerThread`
```
public class HandlerThread extends Thread {
    int mPriority;
    int mTid = -1;
    Looper mLooper;
    private @Nullable Handler mHandler;

    public HandlerThread(String name) {
        super(name);
        mPriority = Process.THREAD_PRIORITY_DEFAULT;
    }
    
    @Override
    public void run() {
        mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(mPriority);
        onLooperPrepared();
        Looper.loop();
        mTid = -1;
    }
  }
```

`IntentService`
```
MyHandlerThread myHandlerThread = new MyHandlerThread("xxx");
Handler handler = new Handler(myHandlerThread.getLooper()) {
 public void handleMessage(Message msg) {
  // 在这里处理的message 就会发生在 xxx 这个子线程中了
 }
};
Message msg = Message.obtain();
msg.what = 1;
msg.obj = user...;
handler.sendMessage(msg); // 这个就会把 msg 放到MyHandlerThread创造出来的Looper中了
```



