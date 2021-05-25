import com.google.gson.Gson;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public class WeatherAPIExerciseFour {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws InterruptedException {
       // System.out.println("Status code :: " +sendWhetherReport());
        var time = new Timer(); // Instantiate Timer Object
        var scheduledTask = new ScheduledTask(); // Instantiate SheduledTask class
        time.schedule(scheduledTask, 0, 600000);

        //for demo only.
        for (int i = 0; i <= 5; i++) {
            System.out.println("Execution in Main Thread...." + i);
            Thread.sleep(600000);
            if (i == 2) {
                System.out.println("Application Terminates");
                System.exit(0);
            }
        }
    }

    //this method call the getWhetherData and get the temperature, then sms if either the temperature is > 20C or < 20C
    //and return the status of the sms either success or failure.
    public static int sendWhetherReport(){
        String body = "Yusuf Isah Abdulsalam and Temperature is %s than 20C. %sC";
        String from = "YusufIsah";
        String to = "+306978745957";
        var temp  = getWhetherData().getMain().getTemp();
        if (temp > 20){
            body =  String.format(body, "greater", temp);
        }else {
            body =  String.format(body, "less", temp);
        }

      return   sendSms(body, to, from);

    }

    //this method get the whether data from Thessaloniki api
    public static WhetherDto getWhetherData(){
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=b385aa7d4e568152288b3c9f5c2458a5"))
                .setHeader("Accept", "application/json") // add request header
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return  new Gson().fromJson(response.body(), WhetherDto.class);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    //this method send using routee sms api
    public static int sendSms(String body, String to, String from){

        var requestBody = "{ \"body\": \""+body+"\",\"to\" : \""+to+"\",\"from\": \""+from+"\"}";

        if (getSmsAccessToken().isPresent()) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("https://connect.routee.net/sms"))
                .header("authorization", "Bearer "+ getSmsAccessToken().get()) // add request header
                .header("Content-Type", "application/json")
                .build();

            try {
                var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                return  response.statusCode();

            } catch (Exception ex) {
                ex.printStackTrace();
                return 500; // return 500 error code if internal server occur
            }
        }
        return 401; //return 401 if is not present


    }

    //this method get sms access token from routee token endpoint
    public static Optional<String> getSmsAccessToken(){

        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");


        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormData(requestBody))
                .uri(URI.create("https://auth.routee.net/oauth/token"))
                .header("authorization", "Basic NWY5MTM4Mjg4YjcxZGUzNjE3YTg3Y2QzOlJTajY5akxvd0o=") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try {

            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var accessTokenResponseDto =   new Gson().fromJson(response.body(), AccessTokenResponseDto.class);
            return Optional.ofNullable(accessTokenResponseDto.getAccess_token());

        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    // this method create a simple: 'grant_type=client_credentials' and return HttpRequest.BodyPublisher
    public static HttpRequest.BodyPublisher buildFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    //Access token response model
    public class AccessTokenResponseDto{

       private String  access_token;

       public AccessTokenResponseDto(){}

        public String getAccess_token() {
            return access_token;
        }
    }

    //Thessaloniki whether response model
    public class WhetherDto{
        private int cod;
        private String message;

        private WhetherMainDto main;

        public WhetherDto() {
        }

        public int getCod() {
            return cod;
        }

        public String getMessage() {
            return message;
        }


        public WhetherMainDto getMain() {
            return main;
        }
    }

    //Thessaloniki whether response main properties model
    public class WhetherMainDto{

       private float temp;

        public WhetherMainDto(){}

        public float getTemp() {
            return temp;
        }

    }

    //this class extends TimerTask Abstract class and implement the run method
    //to send out the whether report using routee sms api
    static class ScheduledTask extends TimerTask {

        Date now; // to display current time
        @Override
        public void run() {
            now = new Date(); // initialize date
            System.out.println("Time is :" + now); // Display current time
            System.out.println("Sms Status :: " + sendWhetherReport());
        }
    }


}
