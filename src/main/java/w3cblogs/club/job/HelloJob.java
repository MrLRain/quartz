package w3cblogs.club.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("I love you");
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("new Date() = " + sim .format(new Date()));
        //获取dataMap 三种形式 ：
        //1：各自获取各自所需的
        //接受jobDetail 的jobDataMap
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        //接受trigger 的jobDataMap
        JobDataMap triggerDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        Object love1 = triggerDataMap.get("love");
        System.out.println("triggerDataMap = " + love1);
        Object love = jobDataMap.get("love");
        System.out.println("love = " + love);
        //====================================第一个结束======================
//        第二个是把job trigger 的jobDataMap 合并在一起 但是会造成相同key的情况，只保留trigger的
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object mergedDataMap = mergedJobDataMap.get("love");
        //输出的值是triggerLove
        System.out.println("mergedDataMap = " + mergedDataMap);
//      ================第二个结束===============
    }
}
