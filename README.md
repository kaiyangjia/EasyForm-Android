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

>3.在使用前请先阅读doc目录下的文档了解该项目.


4.Useage:

    1.在布局文件中使用如下配置：
    
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      xmlns:jky="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:paddingBottom="@dimen/activity_vertical_margin"
      android:paddingLeft="@dimen/activity_horizontal_margin"
      android:paddingRight="@dimen/activity_horizontal_margin"
      android:paddingTop="@dimen/activity_vertical_margin"
      app:layout_behavior="@string/appbar_scrolling_view_behavior"
      tools:context=".MainActivity"
      tools:showIn="@layout/activity_main">
      
          <com.jiakaiyang.library.easyform.view.EFFormView
              android:id="@+id/ef_form"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              jky:formTitleNames="title1,title2,title3,title4"
              jky:frameWidth="1dp"
              jky:frameColor="@android:color/darker_gray"
              jky:dividerWidth="1dp"
              jky:dividerColor="@android:color/darker_gray"
              jky:itemWidth="30dp"
              jky:itemHeight="40dp"
              jky:itemGravity="center"
              jky:formItemTextColor="#666666"
              jky:rowCount="4"
              jky:columnCount="4" >
      
          </com.jiakaiyang.library.easyform.view.EFFormView>
    </RelativeLayout>
    
    
    2.在Activity的代码中：
    final EFFormView formView = (EFFormView) findViewById(R.id.ef_form);
    
            List<Map<String, Object>> data = new ArrayList<>();
            for(int i=0;i<12;i++){
                Map map = new HashMap();
                map.put(Constant.KEY.KEY_DATA, i + "--");
                data.add(map);
            }
            formView.setData(data);
            formView.fillForm();
            //设置每一条点击变色
            formView.setRowClickChange();
            //设置第一行不可点击，用于设置表头
            formView.setRowClickable(0, false);
    