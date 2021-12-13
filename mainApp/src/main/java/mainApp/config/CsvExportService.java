//package mainApp.config;
//
//import mainApp.model.Product;
//import mainApp.repository.ProductRepository;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
//import org.slf4j.Logger;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.Writer;
//import java.util.List;
//
//import static org.slf4j.LoggerFactory.getLogger;
//
//@Service
//public class CsvExportService {
//
//    private static final Logger log = getLogger(CsvExportService.class);
//
//    private final ProductRepository productRepository;
//
//    public CsvExportService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    public void writeProductsToCsv(Writer writer) {
//
//        List<Product> products = productRepository.findAll();
//        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
//            for (Product product : products) {
//                csvPrinter.printRecord(product.getId(), product.getDescription(),
//                        product.getLocation(), product.getName(), product.getPrice());
//            }
//        } catch (IOException e) {
//            log.error("Error While writing CSV ", e);
//        }
//    }
//}