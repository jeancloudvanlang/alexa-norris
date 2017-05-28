package helloChuck;

import helloChuck.chuckApi.ChuckService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ChuckServiceTest {

    @Test
    public void testGetFact() throws IOException {
        ChuckService chuckService = new ChuckService();

        String fact = chuckService.getFact();

        System.out.println(fact);
        Assert.assertNotNull(fact);
    }

}