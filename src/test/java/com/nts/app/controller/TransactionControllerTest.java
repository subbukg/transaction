package com.nts.app.controller;

import com.mongodb.util.JSON;
import com.nts.entity.Transaction;
import com.nts.repository.StatisticsRepository;
import com.nts.repository.TransationRepository;
import javafx.application.Application;
import org.hibernate.engine.spi.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by subramanya on 29/03/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TransactionControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("UTF-8"));

    private MockMvc mockMVC;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Transaction transaction;

    @Autowired
    TransationRepository transationRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    @Before
    public void setUp() throws Exception {
        this.mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.statisticsRepository.deleteAllInBatch();
        this.transationRepository.deleteAllInBatch();

        long currentInMilli = System.currentTimeMillis() % 1000;
        long oneSecondBackInMilli = (System.currentTimeMillis() - 1*1000 ) % 1000;

        this.transationRepository.save(new Transaction(100.0, currentInMilli));
        this.transationRepository.save(new Transaction(200.0, oneSecondBackInMilli));


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addTransaction() throws Exception {
        long currentInMilli = System.currentTimeMillis() % 1000;
        String transJson =  json(transaction = new Transaction(500.0 , currentInMilli));

        this.mockMVC.perform(post("/transactions").contentType(contentType).content(transJson)).andExpect(status().isCreated());

    }

    @Test
    public void getStatistics() throws Exception {

        this.mockMVC.perform(get("/statistics")).andExpect(status().isOk()).andExpect(content().json("500"));

    }

    private String json(Transaction transaction ) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                transaction, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }



}