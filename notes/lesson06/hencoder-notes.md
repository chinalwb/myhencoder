
### 自定义View - 绘制




坐标系 - X 左负右正， Y 上负下正

尺寸单位 全部都是像素 px


#### TypedValue
```
TypedValue.applyDimesion(
	TypedValue.COMPLEX_UNIT_DIP,
	150,
	getResources().getDisplayMetrics()
); 
```

* Resources.getSystem() 获取系统的`Resources`对象, 但得不到具体App里面的资源属性
```
Resources.getSystem().getDisplayMetrics();
```

* `View#onSizeChanged`
layout执行后，view的尺寸发生改变了，就会被调用

* path.setFillType(Path.FillType.EVEN_ODD);
	EVEN: 内部 - 填充
	ODD: 外部 - 不填充
	- Path.FillType.WINDING - 如果全部填充 Path的方向的那个参数全部设定为一样
	- 镂空的话，就直接用 EVEN_ODD
* CW: clockwise - 顺时针
* CCW: counter clockwork - 逆时针


* PathMeasure - new PathMeasure(path, forceClose - false)
* pathMeasure.getLength() - 测量出来整个图形的周长


Paint.setStyle(Paint.Style.STOKE) // 只画线条
paint.setStrokeWidth(2dp)
paint.setPathEffect(new PathDashEffect(path - shape, ))





----


#### Canvas
* drawCircle(float cx, cy, radius, Paint)
* drawRect
* drawPath
* drawBitmap
* drawText
* drawLine
* drawOval
* drawRoundRect

#### Paint
* setStrokeWidth
* setStrokeCap(Paint.Cap.ROUND)

#### 绘制范围裁切
* clipRect
* clipPath

#### 绘制内容的几何变换
* 放大缩小
* 平移

#### 绘制顺序
* 先绘制的内容会比后绘制的内容盖住
* 前景 背景


