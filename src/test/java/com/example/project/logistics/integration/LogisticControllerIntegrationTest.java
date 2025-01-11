package com.example.project.logistics.integration;

import com.example.logistics.LogisticsApplication;
import com.example.logistics.model.SelectedTransfers;
import com.example.logistics.model.Transfer;
import com.example.logistics.model.WeightCost;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = LogisticsApplication.class)
@AutoConfigureMockMvc
public class LogisticControllerIntegrationTest {

    private int randomSeed = 42;
    private int testNumberSeed = 3;
    private int numItemSeed = 8;
    private int weightSeed = 10;
    private int costSeed = 20;
    private int maxWeightSeed = 21;
    private String rootOfInputTests = "src/test/java/resources/input";
    private String rootOfOutputTests = "src/test/java/resources/output";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCalculateRandomTransfers() {
        Random random = new Random(randomSeed);
        int testNumber = random.nextInt(testNumberSeed) + 2;
        for (int i = 0; i < testNumber; i++) {
            int numItems = random.nextInt(numItemSeed) + 3;

            List<WeightCost> randomItems = new ArrayList<>();
            for (int j = 0; j < numItems; j++) {
                int weight = random.nextInt(weightSeed) + 1;
                int cost = random.nextInt(costSeed) + 1;
                randomItems.add(new WeightCost(weight, cost));
            }

            int maxWeight = random.nextInt(maxWeightSeed) + 10;

            Transfer transferData = new Transfer(randomItems, maxWeight);

            String urlI = rootOfInputTests + "/transferInputTes" + i +".json";
            makeInputFile(urlI, transferData);

            ResponseEntity<SelectedTransfers> response = restTemplate.postForEntity(
                    "/api/logistics/calculate-transfers",
                    transferData,
                    SelectedTransfers.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            SelectedTransfers result = response.getBody();
            assertThat(result).isNotNull();

            String urlO = rootOfOutputTests + "/transferOutputTest" + i + ".json";
            makeOutputFile(urlO, result);

            int totalWeight = result.getWeightCostList().stream().mapToInt(WeightCost::getWeight).sum();
            int totalCost = result.getWeightCostList().stream().mapToInt(WeightCost::getCost).sum();


            assertThat(totalWeight).isLessThanOrEqualTo(maxWeight);
            assertThat(result.getTotalCost()).isEqualTo(totalCost);
            assertThat(result.getTotalWeight()).isEqualTo(totalWeight);
            System.out.println("\nTest" + i + " passed without assert\n");
        }
        System.out.println("\nAll tests passed without assert\n");
    }

    private void makeInputFile(String url, Transfer transferData){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(url), transferData);
        } catch (IOException e) {
            System.err.println("Error during I/O operations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void makeOutputFile(String url, SelectedTransfers selectedTransfers){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(url), selectedTransfers);
        } catch (IOException e) {
            System.err.println("Error during I/O operations: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
