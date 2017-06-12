package helloChuck;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import helloChuck.chuckApi.ChuckService;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloChuckSpeechletTest {

    private final String HELLO_CHUCK_INTENT = "HelloChuckIntent";
    private ChuckService service = mock(ChuckService.class);

    @Test
    public void onIntentTest() throws SpeechletException, IOException {
        // Given
        when(service.getFact()).thenReturn("A chuck joke");
        HelloChuckSpeechlet helloChuckSpeechlet = new HelloChuckSpeechlet(service);
        IntentRequest intentRequest = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName(HELLO_CHUCK_INTENT)
                        .build())
                .withRequestId("someRequestId")
                .build();
        Session session = Session.builder().withSessionId("someSessionId").build();

        // When
        SpeechletResponse response = helloChuckSpeechlet.onIntent(intentRequest, session);

        // Then
        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText()).isNotBlank();
    }

    @Test
    public void onIntentTestApiFallback() throws SpeechletException, IOException {
        // Given
        String defaultJoke = "Chuck Norris counted to infinity. Twice!";
        when(service.getFact()).thenThrow(IOException.class);
        HelloChuckSpeechlet helloChuckSpeechlet = new HelloChuckSpeechlet(service);
        IntentRequest intentRequest = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName(HELLO_CHUCK_INTENT)
                        .build())
                .withRequestId("someRequestId")
                .build();
        Session session = Session.builder().withSessionId("someSessionId").build();

        // When
        SpeechletResponse response = helloChuckSpeechlet.onIntent(intentRequest, session);

        // Then
        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertEquals(outputSpeech.getText(), defaultJoke);
    }

    @Test
    public void onIntentTestShouldFailWithForInvalidIntent() throws IOException {
        // Given
        final String invalidIntent = "InvalidIntent123";
        when(service.getFact()).thenReturn("A chuck joke");
        HelloChuckSpeechlet helloChuckSpeechlet = new HelloChuckSpeechlet(service);
        IntentRequest intentRequest = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .withName(invalidIntent)
                        .build())
                .withRequestId("someRequestId")
                .build();
        Session session = Session.builder().withSessionId("someSessionId").build();

        assertThatExceptionOfType(SpeechletException.class)
                .isThrownBy(() -> helloChuckSpeechlet.onIntent(intentRequest, session))
                .withMessage("Invalid Intent");
    }
}
