package w3cblogs.club.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import w3cblogs.club.job.HelloJob;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryDemo {
    /**
     * simpleTrigger 就不同，它会按重复的次数来一直执行到完成触发器
     * @throws SchedulerException
     */
    @Test
    public void testSimpleSchedule() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).
                withIdentity("job", "group1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group1")
                .startNow().withSchedule
                        (SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2).withRepeatCount(0)).build();
        SchedulerFactory sfct = new StdSchedulerFactory();
        Scheduler scheduler = sfct.getScheduler();
        Date date = scheduler.scheduleJob(jobDetail, trigger);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("date = " + date);
        scheduler.start();
    }

    /**
     * 做的时候会发现CronTrigger 触发器，如果你没有持久化这个调动是运行不起来的。
     * @throws SchedulerException
     */
    @Test
    public void testCronTrigger() throws  SchedulerException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH;mm:ss");
        System.out.println("simpleDateFormat.format(new Date()) = " + simpleDateFormat.format(new Date()));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("testCron", "group2")
                .usingJobData("love", "I love you").build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("testCron1", "group3").withSchedule(
                CronScheduleBuilder.cronSchedule("0/5 * * * * ? * ")).startAt(new Date(System.currentTimeMillis()+10000)).endAt(new Date(System.currentTimeMillis()+20000)).usingJobData("love","triggerLove").build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //预计下次执行job的时间。
        Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
        System.out.println("date = " + simpleDateFormat.format(date));
        scheduler.start();
        try {
            // 等待60秒查看效果
            Thread.sleep(15 * 1000L);
        } catch (Exception e) {
        }
        //直接关闭scheduler 会终止Job
//        scheduler.shutdown(false);
        //等任务执行完关闭
//        scheduler.shutdown(true);
        //暂时挂起
        scheduler.standby();
        try {
            // 等待60秒查看效果
            Thread.sleep(5 * 1000L);
        } catch (Exception e) {
        }
        scheduler.start();
        //org.quartz.SchedulerException: The Scheduler cannot be restarted after shutdown() has been called. shutdown后是不能被唤醒的。
//        scheduler.start();
        try {
            // 等待60秒查看效果
            Thread.sleep(30 * 1000L);
        } catch (Exception e) {
        }
    }
}
