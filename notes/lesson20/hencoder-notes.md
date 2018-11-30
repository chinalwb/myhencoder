## Java I/O && Okio

1. Java IO - 基于流
2. Java nio - 基于buffer ()
3. Okio - 


### 1. Java IO
1. OutputStream
2. InputStream

```
OuputStream outputStream = new FileOutputStream('name');
ouputStream.write(byte);
```

```
InputStream inputStream = new FileInputStream('name')
char c = inputStream.read();
```


3. 关闭流Stream.close();

```
// 关闭流Stream.close();

OuputStream outputStream = null;
try {
  outputStream = new FileOutputStream('name');
} catch(Exception e) {

} finally {
	ouputStream.close();
}


// Java 7 会自动关闭 Closable 的对象
try(OuputStream outputStream = new FileOutputStream('name');) {
	...
}
```


4. Java IO Decorator

```
InputStream inputStream = new FileInputStream("filename"); // read 到的是byte
Reader reader = new InputStreamReader(inputStream); // read 到的是一个??
BufferedReader bufferedReader = new BufferedReader(reader); // 可以read一整行
```

5. BufferedWriter.flush(), 输出流关闭的时候会执行flush的动作。 flush只有输出流的时候才需要。缓冲满了的时候也会自动调用flush，但如果输出流缓冲没满的时候，输出的文件中是没有buffer里面的内容的，此时就需要用flush来把缓冲中的内容输出到文件中

```
try(
	OutputStream outputStream = new FileOutputStream("file_name");
	Writer writer = new OutputStreamWriter(outputStream);
	BufferedWriter bufferedWriter = new BufferedWriter(writer);
) {
	bufferedWriter.write('a');
	bufferedWriter.write('b');
	// bufferedWriter.flush()
} catch (Exception e) {

}
```

6. 文件复制

```
try (
	InputStream inputStream = new FileInputStream("filename_in");
	OutputStream outputStream = new FileOutputStream("filename_out");
	
	byte[] data = new byte[1024];
	int readLen;
	while ((readLen = inputStream.read(data)) != -1) {
		outputStrean.write(data, 0, read);
	}
) {

} catch (Exception e) {

}
```


7. Socket

```
ServerSocket serverSocket = new ServerSocket(8080);
Socket socket = serverSocket.accept();
BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

String data;
while (true) {
  data = reader.readLine();
  writer.write(data);
  writer.write("\n");
  writer.flush();
}
```


Terminal: 
telnet localhost 8080 进行作为客户端连接到服务器 8080 端口


## 2. nio

1. position
2. limit
3. capacity

```

try {

RandomAccessFile file = new RandomAccessFile("filename", "r");

byteBuffer.flip();
// byteBuffer.limit(byteBuffer.position());
// byteBuffer.position(0);

byteBuffer.clear();
// byteBuffer.limit(byteBuffer.capacity());
// byteBuffer.position(0);
```

* Selector selector = Selector.open(); -- 配合ServerSocketChannel


## 3. Okio

1. Source - 读 - Okio.source
2. Sink - 写 - Okio.sink
3. Buffer buffer = new Buffer();

```
buffer.outputStream(); // 往buffer
buffer.inputStream();  // 
```


```
try {
	// Source source = Okio.source(new File("file_name")));
	Source source = Okio.buffer(Okio.source(new File("file_name")));
	Buffer buffer = new Buffer();
	source.read(buffer, 1024);
	syso(buffer.readUtf8()); // buffer.readUtf8Line()
} catch (Exception e) {

}
```


