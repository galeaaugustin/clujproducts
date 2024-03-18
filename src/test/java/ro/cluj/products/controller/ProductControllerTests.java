package ro.cluj.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ro.cluj.products.config.SpringSecurityConfig;
import ro.cluj.products.dto.ProductDto;
import ro.cluj.products.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {SpringSecurityConfig.class})
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception{
        // given - precondition or setup
        ProductDto productDto = ProductDto.builder()
                .name("Crema Gerovital")
                .description("Farmec Cluj-Napoca")
                .price(25.4)
                .categoryId(1L)
                .build();
        given(productService.addProduct(any(ProductDto.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bererer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDY5OTUzNiwiZXhwIjoxNzExMzA0MzM2fQ.Vxj8GeDhNay0hTp3_In590KMmczdSNA-OMNEUOVtiNriEviK1JPafyB_9aAS5oc7")
                .content(objectMapper.writeValueAsString(productDto)));
        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(productDto.getName())))
                .andExpect(jsonPath("$.description",
                        is(productDto.getDescription())))
                .andExpect(jsonPath("$.categoryId",
                        is(productDto.getCategoryId())))
                .andExpect(jsonPath("$.price",
                        is(productDto.getPrice())));
    }

    /**
     * JUnit test for Get All Products REST API
     */
    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception{
        // given - precondition or setup
        List<ProductDto> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductDto.builder().name("abc123").description("XYZ456").price(10.6).categoryId(1L).build());
        listOfProducts.add(ProductDto.builder().name("abc456").description("XYZ456").price(20.7).categoryId(1L).build());
        given(productService.getAllProducts()).willReturn(listOfProducts);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/products"));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfProducts.size())));
    }

    // positive scenario - valid Product id
    // JUnit test for GET Product by id REST API
    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception{
        // given - precondition or setup
        Long productId = 1L;
        ProductDto productDto = ProductDto.builder().name("abc123").description("XYZ456").price(10.5).categoryId(1L).build();
        given(productService.getProductById(productId)).willReturn(Optional.of(productDto));
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/products/{id}", productId));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(productDto.getName())))
                .andExpect(jsonPath("$.description", is(productDto.getDescription())))
                .andExpect(jsonPath("$.price", is(productDto.getPrice())))
                .andExpect(jsonPath("$.categoryId", is(productDto.getCategoryId())));

    }

    // negative scenario - valid product id
    // JUnit test for GET Product by id REST API
    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        Long productId = 100000L;
        given(productService.getProductById(productId)).willReturn(Optional.empty());
        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/products/{id}", productId));
        // then - verify the output
        response.andExpect(status().isNotFound()).andDo(print());
    }

    /**
     *  JUnit test for update Product REST API - positive scenario
     */
    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception{
        // given - precondition or setup
        long productId = 1L;
        ProductDto savedProduct = ProductDto.builder()
                .name("Trifermnent")
                .description("Fabricat la Terapia")
                .price(15.5)
                .categoryId(2L)
                .build();
        ProductDto updatedProductDto = ProductDto.builder()
                .name("Trifermnent")
                .description("Fabricat la Terapia in Cluj-Napoca")
                .price(15.5)
                .categoryId(2L)
                .build();
        given(productService.getProductById(productId)).willReturn(Optional.of(savedProduct));
        given(productService.updateProduct(any(ProductDto.class), 1L)).willAnswer((p)-> p.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProductDto)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedProductDto.getName())))
                .andExpect(jsonPath("$.description", is(updatedProductDto.getDescription())))
                .andExpect(jsonPath("$.categoryId", is(updatedProductDto.getCategoryId())))
                .andExpect(jsonPath("$.price", is(updatedProductDto.getPrice())));
    }

    // JUnit test for update Product REST API - negative scenario
    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturn404() throws Exception{
        // given - precondition or setup
        long productId = 1L;
        ProductDto saveProductDto = ProductDto.builder().name("abc123").description("XYZ456").price(10.5).categoryId(1L).build();
        ProductDto updatedProduct = ProductDto.builder().name("abc123").description("XYZ456").price(20.5).categoryId(1L).build();

        given(productService.getProductById(productId)).willReturn(Optional.empty());
        given(productService.updateProduct(any(ProductDto.class), 1L)).willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for delete Product REST API
    @Test
    public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception{
        // given - precondition or setup
        long productId = 1L;
        willDoNothing().given(productService).deleteProduct(productId);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/products/{id}", productId));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
