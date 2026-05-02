package basicjava.java11;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class Java11 {
    public static class HttpClientNew {
        // from: https://openjdk.org/groups/net/httpclient/intro.html
        public static void main(String[] args) throws IOException, InterruptedException {
            var client = HttpClient.newBuilder()
                                    .version(Version.HTTP_2)
                                    .followRedirects(Redirect.NORMAL)
                                    // .authenticator(Authenticator.getDefault())
                                    .build();
            
            var google_dot_com_request  = HttpRequest.newBuilder()
                                                    .uri(URI.create("https://google.com"))
                                                    .timeout(Duration.ofMinutes(1))
                                                    .build();
            var response = client.send(google_dot_com_request, BodyHandlers.ofString());

            System.out.println(response.body());
        }
    }
}
