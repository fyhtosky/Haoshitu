package com.sj.yinjiaoyun.shituwang.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.bean.JsonBean;
import com.sj.yinjiaoyun.shituwang.callback.PhoneCallBack;

import java.util.ArrayList;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/7/3.
 */
public class ProvincePicker {
    private Context context;
    //省市区的选择器
    private OptionsPickerView  provincePicker;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String tx;

    public ProvincePicker(Context context) {
        this.context=context;
        initJsonData();
    }
    /**
     * 解析省市区的三级联动
     */
    private void initJsonData() {
        String jsonData = GetJsonDataUtil.getJson(context,"province.json");//获取assets目录下的json文件数据
        options1Items.clear();
        options1Items.addAll(GetJsonDataUtil.parseData(jsonData));
        for (int i=0;i<options1Items.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<options1Items.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = options1Items.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (options1Items.get(i).getCityList().get(c).getArea() == null
                        ||options1Items.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < options1Items.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = options1Items.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
            Logger.d("所有的数据："+options1Items.size()+"，城市："+options2Items.size()+"，区域："+options3Items.size());
        }
    }
    /**
     * 省市区的选择器
     */
    public void  ProvincePickerShow(final PhoneCallBack callBack){
        provincePicker = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                 tx = options1Items.get(options1).getPickerViewText()+","+
                        options2Items.get(options1).get(options2)+","+
                        options3Items.get(options1).get(options2).get(options3);
                callBack.setContent(tx);
            }
        })
              .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleText("城市选择")
                .setSubmitColor(Color.parseColor("#24C789"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#a0a0a0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#eeeeee"))//标题背景颜色 Night mode
                .setDividerColor(Color.parseColor("#eeeeee"))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setOutSideCancelable(false)// default is true
                .build();


        provincePicker.setPicker(options1Items, options2Items,options3Items);//三级选择器
        provincePicker.show();
    }
    public void  ProvinceCityShow(final PhoneCallBack callBack){
        provincePicker = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中城市位置;
                callBack.setContent( options2Items.get(options1).get(options2));
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleText("城市选择")
                .setSubmitColor(Color.parseColor("#24C789"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#a0a0a0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#eeeeee"))//标题背景颜色 Night mode
                .setDividerColor(Color.parseColor("#eeeeee"))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setOutSideCancelable(false)// default is true
                .build();


        provincePicker.setPicker(options1Items, options2Items,options3Items);//三级选择器
        provincePicker.show();
    }
}
