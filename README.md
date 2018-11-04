# 新手引导库

## 思路
灵感来源于PS的图层概念。
1. 每个新手引导画面为一个图层
2. 用ConstranitLayout作为画布容器，每个图层间的控件相互约束
3. 通过设置锚点确定图层的位置(相当于PS中的可整体拖动图层)

## 效果演示

![](https://www.github.com/wslaimin/GuideLayout/raw/master/screenshot/newer_guide.gif)

## API

### XML属性

| Name   | Type   | Default| Description|
| ------ | ------ | ------ | ---------- |
| group | integer | -1 | 图层位于那一层|
| closed_anchor | boolean | false | 图层中最靠近锚点的控件 |
| anchor_gravity | [left\|right]\|[top\|bottom] | left\|top  | 靠近锚点的位置 |

### GuideLayout

| Method | description|
| ------ | ---------- |
| nextFrame(int frame)  | 显示新手引导|
| getFrame(int frame) | 获取图层 |
| dismissGuide() | 取消新手引导  |
| setBackgroundColor(int color) | 设置背景色  |

### GuideFrame

| Method | description|
| ------ | ---------- |
| setOffsetX(int offsetX)  | 离锚点X方向偏移量|
| setOffsetY(int offsetY) | 离锚点Y方向偏移量 |
| findViewById(int resId) | 根据寻找图层中的view |

>不再使用GuideLayout对象，最好将引用置为null(防止内存泄漏)
