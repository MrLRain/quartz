package w3cblogs.club.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import w3cblogs.club.service.TestService;

import java.text.SimpleDateFormat;
import java.util.Date;
//注解@Component无法注入 JobQuartz
// @Bean 能注入进来
//无参数任务
public class SpringQuartzJob{
    @Autowired
    private TestService testService;

    /**
     * 普通任务：总调度（SchedulerFactoryBean）--> 定时调度器(CronTriggerFactoryBean) --> 调度明细自定义执行方法bean（MethodInvokingJobDetailFactoryBean） -->调度bean（我们定义的job类）
     *
     * 可传参任务：总调度（SchedulerFactoryBean）--> 定时调度器(CronTriggerFactoryBean) -->  调度明细bean（JobDetailFactoryBean）
     */
        //JobExecutionContext jobExecutionContext
        //org.springframework.beans.factory.BeanCreationException: Error creating bean with name
        // 'methodJob' defined in class path resource [w3cblogs/club/conf/QuartzConfig.class]
        // : Invocation of init method failed; nested exception is
        // java.lang.NoSuchMethodException: w3cblogs.club.job.SpringQuartzJob.executeInternal()
        public  void executeInternal(){
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("new Date() = " + sim.format(new Date()));
            System.out.println(" SpringQuartzJob ");
            testService.idFind("SpringQuartzJob");
        }

}
