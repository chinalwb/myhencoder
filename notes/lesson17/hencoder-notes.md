#### RecyclerView

* LinearLayoutManager
* GridLayoutManager
* Stagger Layout Manager


view holder 解决的是 findViewById 的性能问题

convertView 本身就已经复用了

`onViewAttachedToWindow()` 统计recycler view中被用户看到的次数。不能从 RecyclerView 的 `onBindViewHolder()` 统计。

`DiffUtil`适用于整个页面需要刷新，但是有部分数据不变或完全不变的情况。