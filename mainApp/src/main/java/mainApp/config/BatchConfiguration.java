package mainApp.config;

import com.netflix.discovery.converters.Auto;
import mainApp.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired private JobBuilderFactory jobBuilderFactory;

    @Autowired private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Product> reader() {
        JdbcCursorItemReader<Product> reader =
                new JdbcCursorItemReader<Product>();

        reader.setDataSource(dataSource);
        System.out.println("######@@@@@@@@@!!!: I am here");
        reader.setSql("select id, price, name, location, description");
        reader.setRowMapper(new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setPrice(rs.getDouble("price"));
                product.setName(rs.getString("name"));
                product.setLocation(rs.getString("location"));
                product.setDescription(rs.getString("description"));
                return product;
            }
        });
        return reader;
    }
    @Bean
    public FlatFileItemWriter<Product> writer(){
        FlatFileItemWriter<Product> writer = new FlatFileItemWriter<Product>();
        writer.setResource(new FileSystemResource("/home/zacki/IdeaProjects/TheCalculatorApp/mainApp/csv_output.csv"));
        DelimitedLineAggregator<Product> aggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Product> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "price", "name", "location", "description"});
        aggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(aggregator);
        return writer;
    }

    @Bean
    public Step executeStep(){
        return stepBuilderFactory.get("executeStep").<Product, Product>chunk(10)
                .reader(reader()).writer(writer()).build();
    }
    @Bean
    public Job processJob(){
        return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer())
                .flow(executeStep()).end().build();
    }


}