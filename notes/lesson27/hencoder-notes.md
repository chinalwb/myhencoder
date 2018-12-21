### 插件化和热更新

#### 1. 反射：

```
try {
	Class utilClass = Class.forName("com.hencoder.xx.hidden.Utils");
	Constructor utilConstructor = utilClass.getDeclaredConstructors()[0];
	utilConstructor.setAccessible(true);
	Object utils = utilsConstructor.newInstance();
	Method shoutMethod = utilClass.getDeclareMethod("shout");
	shoutMethod.setAccessible(true);
	shoutMethod.invoke(utils);
} catch (Exception e) {
	e.printStrackTrace();
}
```

* 反射出现的是为了让限制不了解的人的使用。不是为了防止外来者入侵
* 但是反射机制可以让了解的人来访问被限制的代码

* public @hide 注解的方法
* Google 不希望让开发者调用，但自己在framework代码中需要调用


#### 2. ClassLoader

* ClassLoader 加载 .class 文件，把 .class 文件变为 Java中的 Class


* DexClassLoader

```
DexClassLoader classLoader = new DexClassLoader(
	apkPath: String -- 复制apk到cacheDir - getCacheDir() + "/xx.apk"
	odexPath: String -- getCacheDir().getPath(),
	librarySearchPath: librarySearchPath -- null
	classLoader parent: ClassLoader -- null
);
Class pluginUtilsClass = classLoader.loadClass("package.class");
```

* dex V.S. odex
* odex: Optimized Dex - 安装到手机之后，手机会对dex文件进行优化之后的结果
* dex 和 odex 放到了不同的目录下
* AOT： Ahead-Of-Time compilation: 预先编译，安装过程当中把java字节码编译成本地机器码。处理之后的文件是 oat 文件： Optimized Android file Type


* 插件化
	- proxy activity
	- object activity
	- override all lifecycle methods
	- 懒加载
	- 