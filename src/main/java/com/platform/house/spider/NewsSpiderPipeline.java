package com.platform.house.spider;

import com.platform.house.form.NewsForm;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by Office on 2018/12/25.
 */
public class NewsSpiderPipeline implements Pipeline {

    private NewsForm newsForm;
    @Override
    public void process(ResultItems resultItems, Task task) {
        NewsForm newsForm = resultItems.get("newsForm");
        this.setNewsForm(newsForm);
    }

    public NewsForm getNewsForm() {
        return newsForm;
    }

    public void setNewsForm(NewsForm newsForm) {
        this.newsForm = newsForm;
    }
}
