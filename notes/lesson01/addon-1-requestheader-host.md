### 补充笔记 - 1 - Request Header （Host）

任何请求（Request）都需要带有 Host 这个request header

比如：
POST /recommend/baidu_zhannei_search?keyword=origin+host+http HTTP/1.1
Host: zhannei-dm.csdn.net
Connection: keep-alive
Content-Length: 4657
...



Host header的作用是：
- 首先Host他不是用来做网络寻址的，也就是说不是让服务器去根据这个域名去找IP。
- 因为在找到IP之前是不可能连接上服务器的，更不用说把请求报文发送到服务器上。

那他的作用是什么呢？
先看一个场景：
我们知道网络上，通过IP可以定位到某一台服务器。
而在这个服务器上可能部署了多个域名
比如 www.csdn.net /  zhannei-dm.csdn.net
如果在Request Header中不带有Host信息
那么这个服务器根本不知道把这个请求交给谁处理。
相反，
如果Request Header中带有
Host: zhannei-dm.csdn.net
这个header的话，
服务器就知道这个请求是发给哪个域名的。
所以Host:xx这个header的作用仅仅是，
在找到目标主机之后确认主机域名和端口的

这个补充笔记的重点在下面这段：
在浏览器上输入一个地址并访问的时候
具体的过程是
浏览器首先会识别要访问的主机域名，
然后通过DNS去找域名相对应的IP
找到IP之后，把输入的地址转变成一个请求报文
发送给目标主机

那么，对于Android代码来说，域名解析这一步是怎么完成的呢？

我们在Android上，发送一个网络请求的时候
可能会用okhttp 或者 直接用HttpUrlConnection 这样的类
把要访问的地址传给这个类 然后就连接上了
所以“连接上了”这个过程中一定有根据域名找IP这个操作

查看HttpURLConnection源码
有这样的注释：
* <p>For example, to retrieve the webpage at {@code http://www.android.com/}:
 * <pre>   {@code
 *   URL url = new URL("http://www.android.com/");
 *   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 *   try {
 *     InputStream in = new BufferedInputStream(urlConnection.getInputStream());
 *     readStream(in);
 *   } finally {
 *     urlConnection.disconnect();
 *   }
 * }</pre>

也就是url.openConnection() 这个方法返回了一个HttpURLConnection对象

那么接着看url.openConnection 方法
    /**
     * Returns a new connection to the resource referred to by this URL.
     *
     * @throws IOException if an error occurs while opening the connection.
     */
    public URLConnection openConnection() throws IOException {
        return streamHandler.openConnection(this);
    }

可以看到他调用了 streamHandler的openConnection

而当查看URLStreamHandler的源码时，发现
    /**
     * Establishes a new connection to the resource specified by the URL {@code
     * u}. Since different protocols also have unique ways of connecting, it
     * must be overwritten by the subclass.
     *
     * @param u
     *            the URL to the resource where a connection has to be opened.
     * @return the opened URLConnection to the specified resource.
     * @throws IOException
     *             if an I/O error occurs during opening the connection.
     */
    protected abstract URLConnection openConnection(URL u) throws IOException;

他是一个抽象方法

再回去到URL.java里面看怎么初始化的URLStreamHandler
       // If there is a stream handler factory, then attempt to
        // use it to create the handler.
        if (streamHandlerFactory != null) {
            streamHandler = streamHandlerFactory.createURLStreamHandler(protocol);

也就是说是streamHandlerFactory这个工厂类创建了这个streamHandler对象
看他源码的时候发现他是一个interface

package java.net;
/**
 * Defines a factory which creates an {@code URLStreamHandler} for a specified
 * protocol. It is used by the class {@code URL}.
 */
public interface URLStreamHandlerFactory {
    /**
     * Creates a new {@code URLStreamHandler} instance for the given {@code
     * protocol}.
     *
     * @param protocol
     *            the protocol for which a handler is needed.
     * @return the created handler.
     */
    URLStreamHandler createURLStreamHandler(String protocol);
}

--- 再往下这条路走不下去了 -- 不过我看到一句注释说，streamHandler是在URL被实例化的时候就确定好了的。后来找不到在哪看到的了--

那继续从其他角度分析吧
建立连接肯定离不开Socket
在socket中看到有这样一个构造:
public Socket(String dstName, int dstPort) 
不同版本略有差异，javadoc里面是这么写的：
public Socket(String host,
      int port)
       throws UnknownHostException,
              IOException

    /**
     * Creates a new streaming socket connected to the target host specified by
     * the parameters {@code dstName} and {@code dstPort}. The socket is bound
     * to any available port on the local host.
     *
     * <p>This implementation tries each IP address for the given hostname (in
     * <a href="http://www.ietf.org/rfc/rfc3484.txt">RFC 3484</a> order)
     * until it either connects successfully or it exhausts the set.
     *
     * @param dstName
     *            the target host name or IP address to connect to.
     * @param dstPort
     *            the port on the target host to connect to.
     * @throws UnknownHostException
     *             if the host name could not be resolved into an IP address.
     * @throws IOException
     *             if an error occurs while creating the socket.
     */
    public Socket(String dstName, int dstPort) throws UnknownHostException, IOException {
        this(dstName, dstPort, null, 0);
    }

这就是说传进来一个host，就能建立连接了
顺着这条线索看到：
        InetAddress[] dstAddresses = InetAddress.getAllByName(dstName);
那么在InetAddress类中一定完成了域名到IP的映射：
再看这个InetAddress类：
public class InetAddress
extends Object
implements Serializable
** This class represents an Internet Protocol (IP) address.

果然！
其中有一个静态方法：
static InetAddress	getByName(String host)
Determines the IP address of a host, given the host's name.

而在InetAddress类中最终是通过
/*
1421 * Simple factory to create the impl
1422 */
1423class InetAddressImplFactory {
1424
1425    static InetAddressImpl 

 create() {
1426    Object 

 o;
1427    if (isIPv6Supported()) {
1428        o = InetAddress.loadImpl("Inet6AddressImpl");
1429    } else {
1430        o = InetAddress.loadImpl("Inet4AddressImpl");
1431    }
1432    return (InetAddressImpl 

)o;
1433    }
1434
1435    static native boolean isIPv6Supported();
1436}


可以看到是更底层JNI调用native代码来完成的。

这些类可以在这里查看到
https://android.googlesource.com/platform/libcore/+/a47f800/luni/src/main/java/java/net/InetAddress.java
或者
http://kickjava.com/src/java/net/InetAddress.java.htm





有时间可以实验下这个了解更深入一些：
https://www.oreilly.com/library/view/learning-java/1565927184/apas02.html

