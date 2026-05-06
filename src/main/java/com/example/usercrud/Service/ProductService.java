package com.example.usercrud.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.usercrud.Model.ProductModel;
import com.example.usercrud.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductModel saveProduct(ProductModel product) {
        return repository.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return repository.findAll();
    }
    public ProductModel updateProduct(String id, ProductModel product) {
        return repository.findById(id).map(existing -> {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
    
    public String saveCSVData(MultipartFile file) {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                ProductModel product = new ProductModel();
                product.setName(data[0]);
                product.setPrice(Double.parseDouble(data[1]));
                product.setQuantity(Integer.parseInt(data[2]));
                product.setDiscount(Double.parseDouble(data[3]));

                repository.save(product);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error saving CSV data");
        }

        return "CSV Data Saved Successfully";
    }
    public String saveCSVData(MultipartFile file, String delimiter) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length != 4) {
                    throw new RuntimeException("Invalid CSV row: " + line);
                }
                ProductModel product = new ProductModel();
                product.setName(data[0].trim());
                product.setPrice(Double.parseDouble(data[1].trim()));
                product.setQuantity(Integer.parseInt(data[2].trim()));
                product.setDiscount(Double.parseDouble(data[3].trim()));

                repository.save(product);
                System.out.println("Saved: " + product);

                String converted =data[0] + delimiter +data[1] + delimiter +data[2] + delimiter +data[3];

                System.out.println(converted);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }

        return " Data Saved ";
    }
}
