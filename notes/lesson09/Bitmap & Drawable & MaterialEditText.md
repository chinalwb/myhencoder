#### Bitmap & Drawable & MaterialEditText


#### [Convert between Bitmap and Drawable](https://corochann.com/convert-between-bitmap-and-drawable-313.html)


#### Drawable to Bitmap

```
public static Bitmap drawableToBitmap (Drawable drawable) {
    Bitmap bitmap = null;
 
    if (drawable instanceof BitmapDrawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if(bitmapDrawable.getBitmap() != null) {
            return bitmapDrawable.getBitmap();
        }
    }
 
    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else {
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }
 
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
}
```


#### Bitmap to Drawable
```
Drawable d = new BitmapDrawable(getResources(), bitmap);
```


#### Bitmap 是什么？
是一个个的像素值

#### Drawable

```
Drawable drawable = new ColorDrawable(Color.RED);
drawable.setBounds(0,0,getWidth(), getHeight());
drawable.draw(canvas);
```

#### 自定义Drawable

```
MeshDrawable extends Drawable

draw(Canvas canvas) {
  canvas.draw()
}
```

-----

#### MaterialEditText
1. setPadding -- 把EditText顶部空间加大
2. onDraw方法的super.onDraw(canvas) 之后，在顶部空间位置画出floating text
3. 有文字的时候才显示floating text，没有文字的时候不显示floating text
4. 加动画
5. `addTextChangedListener` --> `onTextChanged`, 根据内容是否为空来决定执行哪个动画
6. 