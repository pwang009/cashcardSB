package example.cashcard;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardHttpGetTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturn200IfRestTemplateIsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkIdFieldIsNotNull() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        DocumentContext context = JsonPath.parse(response.getBody());
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
    }

    @Test
    void checkCashCardReturnHardCodedValue() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        DocumentContext context = JsonPath.parse(response.getBody());
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(99);
    }

    @Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldReturnAllCashCardsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int cashCardCount = documentContext.read("$.length()");
        assertThat(cashCardCount).isEqualTo(3);

        // JSONArray ids = documentContext.read("$.id");
        // assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

    }

    @Test
    void shouldReturnAPageOfCashCards() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/cashcards?page=0&size=1&sort=amount,desc", String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }
}
