package mainApp.config;

import mainApp.MainAppApplication;
import mainApp.model.Product;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    @Bean
    public JdbcCursorItemReader<Product> reader(){
        JdbcCursorItemReader<Product> reader = new JdbcCursorItemReader<Product>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id,name,description,location,price FROM product");
        reader.setRowMapper(new ProductRowMapper());
        return reader;
    }

    public class ProductRowMapper implements RowMapper<Product>{

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException{
             Product product = new Product();
             product.setId(rs.getInt("id"));
             product.setName(rs.getString("name"));
             product.setDescription(rs.getString("description"));
             product.setLocation(rs.getString("location"));
             product.setPrice(rs.getDouble("price"));

         return product;
        }
    }

    @Bean
    public ProductItemProcessor processor(){return new ProductItemProcessor();}


    @Bean
    public FlatFileItemWriter<Product> writer(){
        FlatFileItemWriter<Product> writer = new FlatFileItemWriter<Product>();
        writer.setResource(new ClassPathResource("products.csv"));
//        writer.setResource(new FileSystemResource("products.csv"));
        DelimitedLineAggregator<Product> aggregator = new DelimitedLineAggregator<>();
//        aggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Product> fieldExtractor = new BeanWrapperFieldExtractor<Product>();
        fieldExtractor.setNames(new String[] {"id", "name", "description", "location", "price"});
        aggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(aggregator);
        return writer;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<Product, Product>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportProductJob() {
        return jobBuilderFactory.get("exportProductJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}
