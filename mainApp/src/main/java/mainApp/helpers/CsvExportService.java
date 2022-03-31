package mainApp.helpers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import mainApp.dto.ProductDTO;
import mainApp.model.Product;
import mainApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvExportService {

    @Value("${export.path}")
    private String exportPath;

    @Autowired
    private ProductService productService;

    // helps with loading a class path or file system rsrcs
    @Autowired
    private ResourceLoader resourceLoader;

    public File exportToCsv() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        File file;
        // path to export to
        Resource resource = resourceLoader.getResource(exportPath);

        // create file or retrieve it
        if (!resource.exists()) {
            file = new File(exportPath);
            file.createNewFile();
        } else {
            file = ResourceUtils.getFile(exportPath);
        }

        List<Product> productList = productService.findAll();

        Writer writer = Files.newBufferedWriter(file.toPath());
        writer.write("");
        writer.flush();

        StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder<Product>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        beanToCsv.write(productList);
        writer.close();

        return file;
    }

    public File exportToCsv(List<ProductDTO> productDTOList) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException{
        Path tempFile = Files.createTempFile("product-export", ".csv");

        Writer writer = Files.newBufferedWriter(tempFile);
        writer.write("");
        writer.flush();

        StatefulBeanToCsv<ProductDTO> beanToCsv = new StatefulBeanToCsvBuilder<ProductDTO>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        beanToCsv.write(productDTOList);
        writer.close();

        return tempFile.toFile();
    }
}
