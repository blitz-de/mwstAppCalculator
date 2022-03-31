package storageService.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class CsvConverterScheduler implements SchedulingConfigurer {

    @Value("${scheduler.frequency}")
    private long schedulerFrequency;

    @Autowired
    private CsvConverterService csvConverterService;

    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(2);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());

        taskRegistrar.addFixedDelayTask(new IntervalTask(
                () -> {
                    try {
                        csvConverterService.process();
                    } catch (Exception e) {
                        throw new RuntimeException("Unexpected error converting csv", e);
                    }
                },
                schedulerFrequency
        ));
    }

}