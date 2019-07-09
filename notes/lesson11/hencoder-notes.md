TagLayout -- 自定义 ViewGroup
-----
override 3 个方法

override fun generateDefaultLayoutParams(): LayoutParams { .. }
override fun generateLayoutPrams(attrs: AttibuteSet?): LayoutParams { .. }
override fun checkLayoutParams(p: LayoutParams?): Boolean { .. }
其中:
当用代码 addView 的方式往 viewgroup 里面添加一个 view 的时候, 如果这个 view 没有调用 setLayoutParams(..) 方法的话, ViewGroup 的 addView 方法默认会调用 generateDefaultLayoutParams 方法, 得到一个 LayoutParams 对象, 并且子类 override 的这个方法不能返回 null, 否则 ViewGroup 会直接抛出异常导致 crash

如果是 XML 的方式往 viewgroup 中添加了子view, 则会先调用 generateLayoutPrams(attrs) 方法来得到一个 LayoutParams 对象, 这是给自定义 viewgroup 的子类一个机会来应用自定义的 LayoutParams.

在 generateLayoutParams 方法调用完毕之后返回一个 LayoutParams 对象, 这个对象会被传到 checkLayoutParams 方法中, 进行有效性判断, checkLayoutParams 方法, 他的默认实现是判断传入的 p 是不是 null. 如果不是 null 返回 true.

如果 checkLayoutParams 方法返回了 false,则会调用 ViewGroup#generateLayoutParams(LayoutParams) 方法(*注意这个方法重载了刚才的那个以 AttributeSet 作为参数的方法), 而此方法默认再讲传递进来的LayoutParams 参数返回.


#### 触摸反馈



`onTouchEvent`



```java
protected boolean onTouchEvent(MotionEvent event) {
  if (event.getActionMasked() == MotionEvent.ACTION_UP) {
    performClick();
  }
  return true;
}
```

- 事件序列
  - 按下 ACTION_DOWN 开始
  - 抬起 ACTION_UP 结束
  - 取消 ACTION_CANCEL  结束



- getAction() / getActionMasked()
  - getAction() - 包含两个信息, 动作类型(DOWN / UP / ACTION_POINTER_UP) 以及 第几根手指 — 多点触控
  - getActionMasked() - 包含一个信息, 动作类型
  - getActionIndex() - 这个动作是第几根手指 — 多点触控

- 消费事件
  - 关注事件 只有在 ACTION_DOWN 事件返回 true, 之后的事件序列会继续传递到这个 view



`onInterceptTouchEvent()`

`dispatchTouchEvent()`

	- onInterceptTouchEvent
	- 子view 的 onTouchEvent
	- onTouchEvent



onInterceptTouchEvent - false 不拦截; true 拦截 ——> 滑动距离足够大的时候, 拦截事件, 传递给自己(viewgroup)的 onTouchEvent 方法.. 

​	第一次触发到自己的 ontouch event 的那个 event 也是最后一次触发 onInterceptTouchEvent



`dispatchTouchEvent`

​	parent.`requestDisallowInterceptTouchEvent`





- 重叠 view 的 onTouchEvent?
