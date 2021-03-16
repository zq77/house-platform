package com.platform.house.spider;

import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseTypeForm;
import com.platform.house.form.NormalImageFrom;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Office on 2018/12/25.
 */
public class HouseSpiderPipeline implements Pipeline {

    public static List<Map<String, HouseInfoForm>> houseList = new ArrayList<>();

    public static List<Map<String, List<HouseTypeForm>>> houseTypeList = new ArrayList<>();

    public static List<Map<String, List<NormalImageFrom>>> houseImageList = new ArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        resultItems.getAll().forEach((key, value)-> {
            if (HouseSpiderUtil.NEW_HOUSE_DETAIL_KEY.equals(key)) {
                houseList.add((Map<String, HouseInfoForm>)value);
            } else if (HouseSpiderUtil.NEW_HOUSE_TYPE_KEY.equals(key)) {
                houseTypeList.add((Map<String, List<HouseTypeForm>>)value);
            } else if (HouseSpiderUtil.NEW_HOUSE_IMAGE_KEY.equals(key)) {
                houseImageList.add((Map<String, List<NormalImageFrom>>)value);
            }
        });
    }   
    
}
