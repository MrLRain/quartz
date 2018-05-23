package w3cblogs.club.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import w3cblogs.club.job.JobQuartz;
import w3cblogs.club.job.SpringQuartzJob;

/**
 * @auth xiaoyu
 * @desciption
 * @Date 10:19 2018/5/23
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobQuartz jobQuartz(){
        return new JobQuartz();
    }
    @Bean
    public SpringQuartzJob springQuartz(){
        return new SpringQuartzJob();
    }
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobQuartz.class);
        jobDetailFactoryBean.setGroup("jobGroup");
        jobDetailFactoryBean.setName("myJob");
        return jobDetailFactoryBean;
    }
    @Bean
    public MethodInvokingJobDetailFactoryBean methodJob() {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(springQuartz());
        jobDetailFactoryBean.setTargetMethod("executeInternal");
        jobDetailFactoryBean.setGroup("jobGroup2");
        jobDetailFactoryBean.setName("myJob2");
        return jobDetailFactoryBean;
    }
    @Bean
    public CronTriggerFactoryBean triggerFactoryBeanToSpring(MethodInvokingJobDetailFactoryBean methodJob) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setCronExpression("0/10 * * * * ? *");
        cronTriggerFactoryBean.setGroup("triggerGroup");
        cronTriggerFactoryBean.setName("myTrigger");
        cronTriggerFactoryBean.setJobDetail(methodJob.getObject());
        return cronTriggerFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean triggerFactoryBean( JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setCronExpression("0/10 * * * * ? *");
        cronTriggerFactoryBean.setGroup("triggerGroup");
        cronTriggerFactoryBean.setName("myTrigger");
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        return cronTriggerFactoryBean;
    }
    //Qualifier注解指定哪个bean 有资格注入。
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("triggerFactoryBean") CronTriggerFactoryBean triggerFactoryBean) {
        //读取配置文件一种注入
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(triggerFactoryBean.getObject());
        scheduler.setJobFactory(myJobFactory());
        return scheduler;
    }

    /**
     * 注解 把 myJobFactory 注入进来。
     * @return
     */
    @Bean
    public MyJobFactory myJobFactory(){
        return new MyJobFactory();
    }
    @Bean
    public SchedulerFactoryBean schedulerFactorySpringBean(@Qualifier("triggerFactoryBeanToSpring") CronTriggerFactoryBean triggerFactoryBeanToSpring) {
        //读取配置文件一种注入
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(triggerFactoryBeanToSpring.getObject());
        return scheduler;

    }
}