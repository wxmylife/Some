# RecyclerView的核心要点

## ListView 的局限
* 只有纵向列表一种布局
* 没有支持动画的 API
* 接口设计和系统不一致
    * setOnItemClickListener()
    * setOnItemLongClickListener()
    * setSelection()
* 没有强制实现 ViewHolder
* 性能不如 RecyclerView

## RecyclerView的优势
* 默认支持 Liner, Grid, Staggered Grid 三种布局
* 友好的 ItemAnimator 动画 API
* 强制实现 ViewHolder
* 解耦的架构设计
* 比 ListView 有更好的性能

## ViewHolder
* View holder 和 item view 是什么关系？一对一？一对多？多对一？
* View holder 解决的是什么问题
* View holder 的 ListView item view 的复用有什么关系

``` 
* 一一对应
* 防止重复 findViewById（广度深度优先算法） ,来提升效率
* 没有关系
```

### ListView 缓存示意图 
缓存 ItemView，2层缓存
* Active View
* Scrap View

![示例ListView 缓存示意图1](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/listview_cache1.png)

![示例ListView 缓存示意图2](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/listview_cache2.png)

## RecyclerView 缓存示意图 
缓存 ViewHolder(所有的 ItemView)，4层缓存 position
* Scrap 屏幕内，position 查找，直接复用 ，数据clean,不用重新绑定数据
* Cache 屏幕外，position 查找，直接复用 ，数据clean,不用重新绑定数据

* ViewCacheExtension 用户自定义的
* RecycledViewPool 被废弃的，脏 ViewHolder， viewType 查找, 查找后重新绑定数据

![示例RecyclerView 缓存示意图1](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/recyclerview_cache1.png)

![示例RecyclerView 缓存示意图2](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/recyclerview_cache2.png)

### ViewCacheExtension Example
* 广告卡片
    ** 每一页一共有4个广告
    ** 这些广告短期内不会发生改变
* 每次划入一个广告卡片，一般情况下都需要重新绑定
* Cache 只关心 position，不关心 view type
* RecycledViewPool 只关心 view type，都需要重新绑定
* 在 ViewCacheExtension 里保持4个广告 Card 缓存

### 注意：列表中 item/广告的 impression 统计
* ListView 通过 getView() 统计
* RecyclerView 通过 onBindViewHolder(）统计？可能错误(遗漏)!
* 通过 onViewAttachedToWindow() 统计 

## RecyclerView 性能优化策略
* 点击监听  
    * onBindViewHolder 里设置点击监听器会导致重复创建对象
    * 在onCreateViewHolder 中设置点击监听

* InitialPrefetchItemCount
```
LinerLayoutManager.setInitialPrefetchItemCount()
```
    * 用户滑动到横向滑动的 item RecyclerView 的时候，由于需要创建更复杂的 RecyclerView 以及多个子 View，可能会导致页面卡顿
    * 由于 RenderThread 的存在，RecyclerView 会进行 prefetch(RenderThread:5.0以上，Ui渲染的线程)
    * LinearLayoutManager,setInitialPrefetchItemCount(横向列表除此显示时刻见的 item 个数）
    * 只有 LinearLayoutManager 有这个API
    * 只有嵌套在内部的 RecyclerView 才会生效

* HasFixedSize
```
RecyclerView.setHasFixedSize() :true
```
    * 如果 Adapter 的数据变化不会导致 RecyclerView 的大小变化：RecyclerView.setHasFixedSize(true)
    
* 多个 RecyclerView 公用 RecycleViewPool
```
RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool()
recycledView1.setRecyclerViewPool(recycledViewPool);
recycledView2.setRecyclerViewPool(recycledViewPool);
recycledView3.setRecyclerViewPool(recycledViewPool);
```
* DiffUtil

## ItemDecoration
* 分割线

![示例ItemDecoration 缓存示意图1](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/itemdecoration_1.png)

![示例ItemDecoration 缓存示意图2](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/itemdecoration_2.png)

* 高亮
* Item 进行分组

## 知识推荐

* [Advanced RecyclerView 官网主页 ](https://advancedrecyclerview.h6ah4i.com)
* [Advanced RecyclerView 源码 ](https://github.com/h6ah4i/android-advancedrecyclerview)
