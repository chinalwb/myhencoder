#### 拖拽和滑动冲突

* ViewDragHelper
* OnDragListener

#### DragHelperGridView
* onMeasure
	- measureChildren() -- 所有的child尺寸都一样
* onLayout
	- 根据child index，确定摆放位置

* OnDragListener
	- `view.startDrag(data /* 可跨进程，只有drop的时候才能拿到 */, DragShadowBuilder, localState /* 随时可以拿到*/, flags)`
	- view.setOnDragListener()

* HenDragListener implements OnDragListener


* ViewDragHelper
	- 不需要长按就能拖动
	- 不需要自己去隐藏view

	```
	ViewDragHelper.create()
	```
	
	```
	DragCallViewDragHelper.Callback
	```
	
	- 需要 override onInterceptTouchEvent / onTouchEvent
	```
	dragHelper.shouldInterceptTouchEvent
	dragHelper.processTouchEvent
	```
	
