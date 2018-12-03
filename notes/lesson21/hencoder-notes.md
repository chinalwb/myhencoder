## Gradle 配置文件拆解


1. 闭包 - 可以被传递的方法（或代码块）


```
int methodA() {
	return 5;
}

void methodB(int num) {
	
}

methodB(methodA()) // 

```

2. buildScript


```
buildScript {

	repository { // 从这里下载 dependencies
		google()
		jcenter()
	}
	
	dependencies { // 这里包含了要使用的插件
		classpath 'com.android.tools.build:gradle:3.2.1'
	}

}

allprojects { // 所有的modules的仓库配置
	
	repositories {
		google()
		jcenter()
	}

}
```

3. Build Variant && Product Flavors

* buildTypes
	- release
	- debug

	
* debug版本 和 release版本界面显示不同颜色标记的实现
	- main -- 任何build type 和 product flavor 都会执行
	- 拆分
		- debug - debug是 src 的一个子目录
		- release - release 也是 src 的一个子目录
		- internal - internal

		```
		buildTypes {
			internal {
				initWith debug
			}
		}
		```

 * productFlavors
 
 ```
 flavorDimensions 'price', 'nation'
 productFlavors {
 	free {
 		dimension 'price'
 	}
 	paid {
 		dimension 'price'
 	}
 	china {
 		dimension 'nation
 	}
 	global {
 		dimension 'nation'
 	}
 }
 ```
 
4. implementation V.S. api

api project(":library1") // 会传递依赖
implemention (":library1")  // 不会传递依赖


5. ./gradlew - gradle wrapper

运行 ./gradlew 的时候，会检查本机是否已经安装了 Gradle，如果安装了，就直接运行不必再安装，如果没有安装则会在线安装然后再运行。

`gradle wrapper` 这个命令会在当前目录下生成一套 gradle wrapper 所必须的文件（夹）

执行 gradlew 命令的时候，会先在当前目录下寻找settings.gradle 来确定项目层级结构。如果当前目录没有这个文件则在兄弟目录 master 文件夹下寻找 settings.gradle， 最后尝试在父目录中寻找这个文件。

6. Gradle task

`./gradlew clearn` 执行 clean 这个task

task 分为 `配置` 和 `执行` 两部分

task 的功能一定要写到 doLast 或者 doFirst 闭包当中。

- doFirst - list.add(0, Closure) -- 每次都往执行列表的最前面加
- doLast (list.add(closure)) -- 每次都往列表的最后面加

```
task taskB (dependsOn: taskA) { // 执行B的时候 需要先执行taskA
	...
}
```

`./gradlew taskB` -- 执行taskB 会先执行taskA!!


1. 初始化阶段 - 准备有哪些project (settings.gradle)
2. 定义阶段 - 画出各个task的有向无环图
3. 执行阶段 - 执行task


 