package org.ucommerce.apps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ucommerce.apps.rest.TestInterfaceRestController;
import org.ucommerce.apps.rpc.TestInterfaceRpcClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
class TestInterfaceTest {

    @LocalServerPort
    private int port;

    TestInterfaceRpcClient client;

    @BeforeEach
    public void beforeEach() {
        client = new TestInterfaceRpcClient("http://localhost:" + port);
    }

    @Test
    public void shouldCallGetValue() {

        String value = client.getValue(4);
        assertEquals("4", value);
    }

    @Test
    public void shouldCallCreateThing() {

        String thingId = client.createThing(new ThingData("foo", 5));
        assertNotNull(thingId);
        assertEquals("abc", thingId);

    }

    @org.springframework.context.annotation.Configuration
    public static class Configuration {

        @Bean
        public TestInterfaceImpl testInterface() {
            return new TestInterfaceImpl();
        }

        @Bean
        public TestInterfaceRestController testInterfaceRestController(TestInterface testInterface) {
            return new TestInterfaceRestController(testInterface);
        }

    }
}