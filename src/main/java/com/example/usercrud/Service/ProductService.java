package com.example.usercrud.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.usercrud.Model.ColumnResponse;
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
            existing.setQuantity(product.getQuantity());
            existing.setDiscount(product.getDiscount());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    public String saveCSVData(MultipartFile file, String delimiter) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String header = br.readLine();
            if (header == null) {
                throw new RuntimeException("CSV file is empty");
            }
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] data = line.split("\\s*" + Pattern.quote(delimiter) + "\\s*");
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
            }

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        return "Data Saved";
    }
    
    public List<ColumnResponse> analyzeCSV(MultipartFile file, String delimiter){
    	List<ColumnResponse> result=new ArrayList<>();
    	try {
    		BufferedReader br= new BufferedReader(new InputStreamReader(file.getInputStream()));
    		String headerLine=br.readLine();
    		String[] headers= headerLine.split(Pattern.quote(delimiter));
    		List<String[]> rows= new ArrayList<>();
    		String line;
    		while((line=br.readLine())!=null){
    			rows.add(line.split(Pattern.quote(delimiter)));
    			}
    		for(int i=0;i<headers.length;i++) {
    			int integerCount=0;
    			int floatCount=0;
    			int booleanCount=0;
    			int stringCount=0;
    			
    			List<String> columnValues=new ArrayList<>();
    			for(String[] row: rows) {
    			if(i>=row.length) {
    				continue;
    			}
    			String value=row[i].trim();
    				columnValues.add(value);
    				if(value.matches("\\d+")) {
    					integerCount++;
    				}else if(value.matches("\\d+\\.\\d+")) {
    					floatCount++;
    				}
    				else if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("false")) {
    					booleanCount++;
    				}
    				else {
    					stringCount++;
    				}
    				}
    			String type="String";
    			int max=Math.max(Math.max(integerCount,floatCount), Math.max(booleanCount,stringCount));
    			if(max==integerCount) {
    				type="Integer";
    			}
    			else if(max==floatCount) {
    				type="Float";
    			}
    			else if(max==booleanCount) {
    				type="Boolean";
    			}
    			Set<String> allTypes= new HashSet<>();
    			if(integerCount>0) {
    				allTypes.add("Integer");
    			}
    			if(floatCount>0) {
    				allTypes.add("Float");
    			}
    			if(booleanCount>0) {
    				allTypes.add("Boolean");
    			}
    			if(stringCount>0) {
    				allTypes.add("String");
    			}
    			ColumnResponse response=new ColumnResponse();
    			response.setColumnName(headers[i]);
    			response.setPrimaryDataType(type);
    			response.setAllDataTypes(new ArrayList<>(allTypes));
    			result.add(response);
    			}
    
    	}catch(Exception e) {
    			throw new RuntimeException(e.getMessage());
    		
    	}
    	return result;
    	}
}
