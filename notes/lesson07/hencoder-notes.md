#### 绘制 2

文字绘制

* paint.setTextSize()
* canvas.drawText()
* paint.setTypeface() // 设置字体
* Typeface.createFromAsset(getContext().getAssets(), "")
* paint.setTextAlign() // 横向居中

垂直居中计算方式：
1. paint.getTextBounds // 获取文字绘制的矩形范围
2. paint.getFontMetrics() // 


多行文字
* StaticLayout
* paint.breakText


* canvas.clipPath() // 设定剪切范围，会有毛边
* canvas.clipRect()
* translate
* rotate
* scale
* skew
* 抗锯齿会多增加几个像素在绘制范围内

Matrix
* preTranslate / postTranslate

Camera
* camera.rotateX
* camera.applyToCanvas

Skia
* 1 英寸 = 72像素