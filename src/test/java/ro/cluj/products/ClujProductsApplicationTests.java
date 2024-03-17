package ro.cluj.products;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

@SpringBootTest
@ImportResource({"classpath*:application-context.xml"})
class ClujProductsApplicationTests {

	@Test
	void contextLoads() {
	}

}
