package com.platform.house.spider;

import com.platform.house.form.ResoldHouseInfoForm;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Office on 2018/12/25.
 */
public class ResoldHouseSpiderPipeline implements Pipeline {

    public static List<ResoldHouseInfoForm> houseList = new ArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        resultItems.getAll().forEach((key, value)-> {
            houseList.add((ResoldHouseInfoForm) value);
        });
    }   
    
}
