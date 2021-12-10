package storageService.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import storageService.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if (jobExecution.getStatus()== BatchStatus.COMPLETED){
            log.info("!!! JOB FINISHED ! Time to verify the results");

            List<Product> results = jdbcTemplate.query("SELECT id, description, location, name, price FROM product", new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int row) throws SQLException {
                    return new Product(rs.getInt(1), rs.getString(2),rs.getDouble(3),rs.getString(4),
                            rs.getString(5));
                }
            });

            for (Product product : results) {
                log.info("Found <" + product + "> in the database.");
            }
        }
    }
}
