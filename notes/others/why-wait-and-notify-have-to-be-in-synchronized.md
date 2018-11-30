#### 

-----

我们来看一个例子，假如 object.wait() 方法的调用没有发生在 synchronized 内部会发生什么问题。

假设我们现在是想实现一个 阻塞式队列 (Blocking Queue).

我们可能会这样来写这段代码（星球好像不支持MD？我把代码截屏放图了）：
```
class BlockingQueue {
    Queue<String> buffer = new LinkedList<String>();

    public void give(String data) { // 往队列里面放数据
        buffer.add(data);
        notify();                   // 或许其他线程正在 取数据 take() 方法中被阻塞着，所以我们唤醒一下：嘿哥们儿，有数据了！
    }

    public String take() throws InterruptedException { // 从队列里面取数据，如果取不到则等待 一直到取得数据为止
        while (buffer.isEmpty())    // don't use "if" due to spurious wakeups.
            wait();
        return buffer.remove();
    }
}
```

下面这种是可能发生的一种结果（2个线程同时执行，一个是消费者线程，一个是生产者线程）：
1. 消费者线程，调用了 take() 方法，但是队列中没有数据 buffer.isEmpty 返回true
2. 在消费者线程调用 wait() 方法之前，生产者线程调用了 give() 方法，并且也执行了 notify() 方法
3. 然后消费者线程调用了 wait() 方法 （此时 wait 其实是错过了上一步当中 notify 的唤醒时机）
4. 如果生产者线程再也没有调用 give 方法，那么消费者线程就永远陷入了 wait() 方法导致的等待状态 （--我自己加的：线程泄露的一种表现，他不能被回收，但已经失去活性--）

如果你理解了上面描述的问题，那么你应该也能想到解决方法，那就是 让give方法的 buffer.add(data)/notify 和take方法的 isEmpty/wait 永远成对执行（原子性执行）。

下面是Chris Smith给出的一段正式结论：

>
[…] You need an absolute guarantee that the waiter and the notifier agree about the state of the predicate.The waiter checks the state of the predicate at some point slightly BEFORE it goes to sleep, but it depends for correctness on the predicate being true WHEN it goes to sleep. There’s a period of vulnerability between those two events, which can break the program. […]

>>
等待者 和 通知者之间，对他们执行条件的状态必须有一个绝对的保证。等待者在进入wait *之前* 去检查这个条件是否成立，但是整个条件的正确性只有在等待者 *执行* wait的一瞬间才能真正确定（--我加的：也就是在条件判定之后，wait之前，这个判定条件存在被修改的可能性--）。在执行之前和执行时这个时间段内判定条件容易被更改，而条件的更改很可能会破坏代码的执行结果。


[原文](https://programming.guide/java/why-wait-must-be-in-synchronized.html)

[StackOverFlow why-must-wait-always-be-in-synchronized-block](https://stackoverflow.com/a/2779674/853191)

