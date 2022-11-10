package com.example.soup.elastic;

import com.example.soup.elastic.ProductDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/kjh")
@RestController
@RequiredArgsConstructor
public class ElasticController {
    private final com.example.soup.elastic.BaseElasticSearchRepo BaseElasticSearchRepo;
    @GetMapping("/test")
    public List<ProductDocs> testControl() {
        List<ProductDocs> productList = new ArrayList<>();

        for (ProductDocs prod: BaseElasticSearchRepo.findAll() ) {
            productList.add(prod);
            System.out.println(prod.getPrdName());
        }
        System.out.println("???????? : "+productList.size());
        return productList;
    }
    @GetMapping("/test2")
    public String testControl2() {
        return "test";
    }

    @PostMapping("/put")
    public String putData(@RequestBody DocsDTO docsDTO) {
        System.out.println("put : "+ docsDTO.getPrdName());
        ProductDocs productDocs  = ProductDocs.builder()
                        .cat(docsDTO.getCat()).imgSrc(docsDTO.getImgSrc()).prdName(docsDTO.getPrdName())
                        .purchase(docsDTO.getPurchase()).price(docsDTO.getPrice()).subcat(docsDTO.getSubcat())
                        .site(docsDTO.getSite()).score(docsDTO.getScore()).build();

        BaseElasticSearchRepo.save(productDocs);
        return "success";

    }
}
