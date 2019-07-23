# myhencoder

## lesson01 - HTTP的原理和工作机制
* [HenCoder notes](./notes/lesson01/hencoder-notes.md) 
* [补充笔记-1-Request Header Host的作用](./notes/lesson01/addon-1-requestheader-host.md)
* [补充笔记-2-GET请求中追加的随机数](./notes/lesson01/addon-2-GET请求中追加的随机数.md)
* [补充笔记-3-幂等（idempotent）](./notes/lesson01/addon-3-幂等（idempotent）.md)

## lesson02 - 编码、加密、Hash
* [HenCoder notes](./notes/lesson02/hencoder-notes.md)
* [补充笔记-1-如何计算文件的SHA-1值？](./notes/lesson02/addon-1-计算文件的SHA-1值.md)
* [补充笔记-2-为什么override equals方法就必须要override hashCode方法](./notes/lesson02/addon-2-为什么override-equals方法就必须要override-hashCode方法.md)
* [补充笔记-3-HashMap的工作机制](./notes/lesson02/addon-3-HashMap的工作机制.md)

## lesson03 - 登录、授权，TCP/IP， HTTPS
* [HenCoder notes](./notes/lesson03/hencoder-notes.md)
* [补充笔记-1-到底要不要setHostnameVerifier？](./notes/lesson03/addon-1-到底要不要setHostnameVerifier.md)
* [补充笔记-2-数字证书的颁发和验证过程](./notes/lesson03/addon-2-数字证书的颁发_验证过程.md)
* [补充笔记-3-非对称加密/消息摘要/数字签名/证书验证](https://github.com/chinalwb/EasyCoding/blob/master/notes/ch01.md#4-https)

## lesson04 - retrofit
* [HenCoder notes](./notes/lesson04/hencoder-notes.md)

## lesson05 - okhttp
* [HenCoder notes](./notes/lesson05/hencoder-notes.md)

## lesson06 - 自定义View - 绘制
* [HenCoder notes](./notes/lesson06/hencoder-notes.md)

## lesson07 - 自定义View - 绘制 2
* [HenCoder notes](./notes/lesson07/hencoder-notes.md)

## lesson08 - 属性动画 - drawable / bitmap
* [HenCoder notes](./notes/lesson08/hencoder-notes.md)


## lesson09 - MaterialEditText
* [HenCoder notes](./notes/lesson09/hencoder-notes.md)

## lesson10 - 测量和布局
* [HenCoder notes](./notes/lesson10/hencoder-notes.md)

## lesson11 - 自定义ViewGroup TagLayout 和 触摸反馈
* [HenCoder_notes](./notes/lesson11/hencoder-notes.md)

## lesson12 - ScalableImageView
* [HenCoder notes](./notes/lesson12/hencoder-notes.md)

## lesson13 - 多点触控
* [HenCoder notes](./notes/lesson04/hencoder-notes.md)

## lesson14 - ???
* ???

## lesson15 - 拖拽和滑动冲突
* [HenCoder notes](./notes/lesson15/hencoder-notes.md)

## lesson16 - Java多线程和线程同步
* [HenCoder notes](./notes/lesson16/hencoder-notes.md)

## lesson17 - RecyclerView
* [HenCoder notes](./notes/lesson17/hencoder-notes.md)

## lesson18 - 线程间通信的原理，以及Android中的多线程
* [HenCoder notes](./notes/lesson18/hencoder-notes.md)

## lesson19 - RxJava
* [HenCoder notes](./notes/lesson19/hencoder-notes.md)

## lesson20 - Java I/O, nio, Okio
* [HenCoder notes](./notes/lesson20/hencoder-notes.md)

## Git
* git merge branch1
* 冲突时, 手动解决之后 执行:
* git add .
* git merge --continue // 解决冲突的命令
* git push

* git branch -d branchX // 删除一个本地仓库的分支

* git merge branchX --no-ff // 禁止fast forward. 不管是不是超前都需要一个额外的 commit

* git rebase master // 在分支上执行这句代码之后, 把分支上的所有提交版本历史在 master 上最后一次提交版本之后重新做一遍, 然后把 feature的引用指向 master 之后的最后一个版本 -- 也就是说 master 之后的所有提交都是跟 feature 上一样的. master 指向的引用保持不变.

## 其他相关
1. [为什么wait / notify必须在synchronize方法或代码块中运行？](./notes/others/why-wait-and-notify-have-to-be-in-synchronized.md)
2. [Java GC Roots 和 内存分区的垃圾回收相关](https://www.w3resource.com/java-tutorial/garbage-collection-in-java.php)
3. [Bitmap 优化管理](./notes/others/Bitmap%20优化管理)
