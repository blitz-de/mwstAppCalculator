package mainApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mainApp.dto.ProductDTO;
import mainApp.helpers.CsvExportService;
import mainApp.helpers.ProductAggregatorService;
import mainApp.model.Product;
import mainApp.model.ProductDeliveryInfo;
import mainApp.services.ProductService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class MainAppController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CsvExportService csvExportService;

	@Autowired
	private ProductAggregatorService productAggregatorService;

	@GetMapping("/{id}")
	@Operation(summary = "Get Product Details by Id")
	@ApiResponse(description = "Product object")
	public ResponseEntity<Product> getProduct(
			@Parameter(description = "Id of the product") @PathVariable("id") Integer id
	) {
		Optional<Product> productOpt = productService.findById(id);

		return productOpt
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	@Operation(summary = "Create new Product")
	@ApiResponse(description = "The newly created Product")
	public ResponseEntity<Product> createProduct(
			@Parameter(description = "Product object in json") @RequestBody Product product
	) {
		return ResponseEntity.ok(productService.save(product));
	}

	@GetMapping("/export")
	@Operation(summary = "Export all products to CSV")
	@ApiResponse(description = "CSV file")
	public ResponseEntity<Resource> exportProductstoCsv() {
		try {
			File file = csvExportService.exportToCsv();
			byte[] bytes = FileUtils.readFileToByteArray(file);
			ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

			return ResponseEntity.ok()
					.contentLength(byteArrayResource.contentLength())
					.header("Content-Disposition", "attachment; filename=products.csv")
					.contentType(MediaType.parseMediaType("text/csv"))
					.body(byteArrayResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/all/details")
	@Operation(summary = "Get all of the products and details including delivery, location, amount")
	@ApiResponse(description = "List of products with details")
	public ResponseEntity<List<ProductDTO>> findAll() {
		return ResponseEntity.ok(productAggregatorService.findAll());
	}

	@GetMapping("/all/details/csv")
	@Operation(summary = "Export all products and details to CSV")
	@ApiResponse(description = "CSV file")
	public ResponseEntity<Resource> exportToCsv() {
		try {
			File file = csvExportService.exportToCsv(productAggregatorService.findAll());
			byte[] bytes = FileUtils.readFileToByteArray(file);
			ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
			return ResponseEntity.ok()
					.contentLength(byteArrayResource.contentLength())
					.header("Content-Disposition", "attachment; filename=products_export.csv")
					.contentType(MediaType.parseMediaType("text/csv"))
					.body(byteArrayResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}/details")
	@Operation(summary = "Get a specific produc and it's details by Id")
	@ApiResponse(description = "Product object with details")
	public ResponseEntity<ProductDTO> findById(
			@Parameter(description = "Id of the product") @PathVariable Integer id
	) {
		ProductDTO productDTO = productAggregatorService.findById(id);
		if (productDTO != null) {
			return ResponseEntity.ok(productDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/details")
	@Operation(summary = "Import product details by uploading a CSV")
	@ApiResponse(description = "The new delivery info details object")
	public ResponseEntity<List<ProductDeliveryInfo>> addProductDetails(
			@Parameter(description = "The CSV file to upload") @RequestParam MultipartFile file
	) {
		return ResponseEntity.ok(productAggregatorService.importProductDeliveryInfoCsv(file));
	}

	@PostMapping("/details/add")
	@Operation(summary = "Import a list of product details by json")
	@ApiResponse(description = "The new delivery info details list")
	public ResponseEntity<List<ProductDeliveryInfo>> addProductDetails(
			@Parameter(description = "The list of Product delivery info") @RequestBody List<ProductDeliveryInfo> productDeliveryInfoList
	) {
		return ResponseEntity.ok(productAggregatorService.importDeliveryInfoList(productDeliveryInfoList));
	}

	@PostMapping("/{id}/details/")
	@Operation(summary = "Add details of a single product by json")
	@ApiResponse(description = "The nwe delivery info details object")
	public ResponseEntity<ProductDeliveryInfo> addProductDetails(
			@Parameter(description = "The Product delivery info") @RequestBody ProductDeliveryInfo productDeliveryInfo,
			@Parameter(description = "Id of the existing product") @PathVariable Integer id) {
		return ResponseEntity.ok(productAggregatorService.importDeliveryInfo(productDeliveryInfo, id));
	}


}
