package storageService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import storageService.model.ProductDeliveryInfo;
import storageService.service.StorageService;

import java.util.List;

@RequestMapping("/storage")
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/product/details/csv")
    @Operation(summary = "Import product details by uploading a CSV")
    @ApiResponse(description = "The new delivery info details object")
    public ResponseEntity<List<ProductDeliveryInfo>> addProductDetails(
            @Parameter(description = "The CSV file with the delivery info of existing products") @RequestParam MultipartFile file
    ) {
        return ResponseEntity.ok(storageService.saveAllDeliveryInfo(file));
    }

    @PostMapping("/product/details/")
    @Operation(summary = "Import a list of product details by json")
    @ApiResponse(description = "The new delivery info details list")
    public ResponseEntity<List<ProductDeliveryInfo>> addProductDetails(
            @Parameter(description = "The list of delivery info for existing products") @RequestBody List<ProductDeliveryInfo> productDeliveryInfoList
    ) {
        return ResponseEntity.ok(storageService.saveAllDeliveryInfo(productDeliveryInfoList));
    }

    @PostMapping("/product/{id}/details/")
    @Operation(summary = "Add details of a single product by json")
    @ApiResponse(description = "The nwe delivery info details object")
    public ResponseEntity<ProductDeliveryInfo> addProductDetails(
            @Parameter(description = "Delivery info of an existing product") @RequestBody ProductDeliveryInfo productDeliveryInfo,
            @Parameter(description = "Id of the existing product") @PathVariable Integer id
    ) {
        return ResponseEntity.ok(storageService.saveDeliveryInfo(productDeliveryInfo, id));
    }

    @GetMapping("/product/details/")
    @Operation(summary = "Find all delivery infos")
    @ApiResponse(description = "List of delivery info")
    public ResponseEntity<List<ProductDeliveryInfo>> findAll(){
        return  ResponseEntity.ok(storageService.findAllDeliveryInfo());
    }

    @GetMapping("/product/{id}/details/")
    @Operation(summary = "Find delivery info by product id")
    @ApiResponse(description = "The delivery info object")
    public ResponseEntity<ProductDeliveryInfo> findByProductId(
            @Parameter(description = "Id of the product") @PathVariable Integer id
    ){
        return  ResponseEntity.ok(storageService.findByProductId(id));
    }

}
