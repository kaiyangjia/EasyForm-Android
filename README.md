1.项目简介
>该项目是一个android端用于生成复杂表格的库。可以用来做像Excel表格那样的UI界面。

2.事先声明:
>本人明白该项目的UI组件完全不符合android的设计规范，甚至都不符合移动端的设计。
>但是客户会经常提出这种无理的需求，所以作为开发者的我们也就只好硬着头皮实现了。


3.使用须知:
>1.该项目依赖lombok项目，在使用之前确保Android Studio上已经安装有 Lombok 插件
>>   lombok的版本使用12 或者 16 时会有编译错误，请保持当前的lombok版本

>2.自定义item布局时，填充文字的view中必须包含有id为ef_item_text 的TextView的控件；填充图片的view中必须
>包含id为ef_item_image的imageView的控件。