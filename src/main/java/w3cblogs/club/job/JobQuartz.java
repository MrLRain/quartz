package w3cblogs.club.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import w3cblogs.club.service.TestService;

import java.text.SimpleDateFormat;
import java.util.Date;
// implements  Job 注解@Component 和 @Bean 一样 无法注入 JobQuartz
public class JobQuartz extends QuartzJobBean {
    @Autowired
    private TestService testService;

    /*@Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("new Date() = " + sim.format(new Date()));
        System.out.println(" JobQuartz ");
        testService.idFind("yang..");
    }
   */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("new Date() = " + sim.format(new Date()));
        System.out.println(" JobQuartz ");
        testService.idFind("yang..");
    }
}
