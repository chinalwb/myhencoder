#### 绘制 2

文字绘制

* paint.setTextSize()
* canvas.drawText()
* paint.setTypeface() // 设置字体
* Typeface.createFromAsset(getContext().getAssets(), "")
* paint.setTextAlign() // 横向居中

垂直居中计算方式：
1. paint.getTextBounds // 获取文字绘制的矩形范围
2. paint.getFontMetrics() // FontMetrics ascent / descent


多行文字
* StaticLayout

* paint.breakText // canvas.drawText("….") 折行

  ```java
  paint.breakText(text, textStart, textEnd, x, y, float[] width)
  ```


* canvas.clipPath() // 设定剪切范围，会有毛边
* canvas.clipRect()
* translate
* rotate (默认以坐标原点为中心，如果需要以View的中心旋转，则需要先移动再旋转)
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