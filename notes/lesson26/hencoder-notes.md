### 架构

* MVC
	- 老师说的MVC 是我理解的NoMVX
	- Activtiy 是当 C 来用
	- Model 是保存数据
	- View 是XML描述的对象

	- Activity 是 C
	- Model
	- View 是自定义View
	- C 加载 View
	- C 调用 Model
	- Model 把数据回传到 Activity.. Activity (C) 调用View的方法 把Model传过去

* MVP
	- Activity - View
	- Model - 数据
	- P - Presenter

	- Activity 加载View
	- Activity 调用 Presenter 方法来取数据并显示，然后把View(自己)传递到Presenter
	- Presenter 调用 Model 取得结果
	- Presenter 回调 View 的方法来显示

	- 引入 IView 负责展示数据
	- Activity implements IView 并实现 IView 的接口方法
	- Presenter 持有 IView 的引用，这样就不需要直接引用一个Activity，而是可以任何实现了 IView 接口的任何类

* MVVM
	- 表现数据和内存数据 双向绑定
	- 内存数据和数据库数据 双向绑定 -- 可选
	
	- ViewModel
	- ViewBinder - View 和 Data 的绑定
	- ViewBinder - 给View加 change 监听器，改变 data 内存数据
	- data 加 setter 方法，改变的时候，回调 OnChangeListener 的 onChange 方法 改变表现数据

	
	
* MVC & MVP 更倾向于架构的规范
* MVVM 更倾向于框架
* MVP 就是 更多人心目中的MVC (Oh yea~~)