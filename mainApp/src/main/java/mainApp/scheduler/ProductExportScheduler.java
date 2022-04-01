package mainApp.scheduler;

import mainApp.helpers.CsvExportService;
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
public class ProductExportScheduler implements SchedulingConfigurer {

    @Value("${scheduler.frequency}")
    private long schedulerFrequency;

    @Autowired
    private CsvExportService csvExportService;

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
                        csvExportService.exportToCsv();
                    } catch (Exception e) {
                        throw new RuntimeException("Unexpected error exporting products to csv", e);
                    }
                },
                schedulerFrequency
        ));
    }
}
