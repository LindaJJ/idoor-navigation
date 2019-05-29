package com.linda.homework.lindaindoornavigation.timedtask;

import com.linda.homework.lindaindoornavigation.controller.service.LineService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 定时更新最短路径的定时任务
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduledRefreshPathTask implements InitializingBean {

    @Resource
    private LineService lineService;

    //3.添加定时任务
    @Scheduled(cron = "0/60 * * * * ?")
    private void configureTasks() {
        lineService.refreshPath();
        System.out.println("执行定时任务时间: " + LocalDateTime.now());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configureTasks();
    }
}
