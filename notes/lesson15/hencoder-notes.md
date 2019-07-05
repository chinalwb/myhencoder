#### 拖拽和滑动冲突

* ViewDragHelper
  * 从底部或侧方滑出菜单
  * 真正的移动 view
* OnDragListener
  * 内容的移动
  * ViewCompat.startDragAndDrop(view, ClipData, DragShadowbuilder, localState, flags)



```java
class CollectionListener implements OnDragListener {
  
}

xxView.setOnLongClickListener(dragStarter);
xxView.setOnDragListener(dragListener);

OnLongClickListener dragStarter = new OnLongClickListener() {
  @Override
  public boolean onLongClick(View v) {
    ClipData imageData = xxx;
    return ViewCompat.startDragAndDrop(view, imageData, DragShadowBuilder, localState, flags)
  }
}
```





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
	
