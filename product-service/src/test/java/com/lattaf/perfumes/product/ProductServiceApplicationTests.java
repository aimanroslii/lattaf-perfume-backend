package com.lattaf.perfumes.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	// Set up the MongoDB container and configure RestAssured with the dynamic port
	// Since the container assigns a dynamic port, we must set RestAssured's base URI and port accordingly
	// before each test
	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost:";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	// Remarks: Haven't tested because of Mac limited space
	@Test
	void shouldCreateProduct() {
		String requestBody = """
					{
						"name" : "Iphone 15",
						"description" : "Get your latest iphone 15 from Apple",
						"price" : 1540
					}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Iphone 15"))
				.body("description", Matchers.equalTo("Get your latest iphone 15 from Apple"))
				.body("price", Matchers.equalTo(1540.00F));
	}

}
