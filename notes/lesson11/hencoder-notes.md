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