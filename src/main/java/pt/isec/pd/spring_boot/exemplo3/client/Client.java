package pt.isec.pd.spring_boot.exemplo3.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pt.isec.pd.spring_boot.exemplo3.models.Attendance;
import pt.isec.pd.spring_boot.exemplo3.models.Event;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Scanner;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {

    public static String sendRequestAndShowResponse(String uri, String verb, String authorizationValue, String body)
            throws MalformedURLException, IOException {

        String responseBody = null;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(verb);
        connection.setRequestProperty("Accept", "application/xml, */*");

        if (authorizationValue != null) {
            connection.setRequestProperty("Authorization", authorizationValue);
        }

        if (body != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "Application/Json");
            connection.getOutputStream().write(body.getBytes());
        }

        connection.connect();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode + " (" + connection.getResponseMessage() + ")");

        Scanner s;

        if (connection.getErrorStream() != null) {
            s = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
            responseBody = s.hasNext() ? s.next() : null;
        }

        try {
            s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
            responseBody = s.hasNext() ? s.next() : null;
        } catch (IOException e) {
        }

        connection.disconnect();

        System.out.println(verb + " " + uri + (body == null ? "" : " with body: " + body) + " ==> " + responseBody);
        System.out.println();

        return responseBody;
    }

    public static void getAllEvents(String uri, String authorizationValue) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml, */*");

        if (authorizationValue != null) {
            connection.setRequestProperty("Authorization", authorizationValue);
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode + " (" + connection.getResponseMessage() + ")");
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            System.err.println("Authentication failed");
        } else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            System.err.println("You are not authorized");
        } else{
            InputStream jsonStream = connection.getInputStream();
            JsonReader jsonReader = Json.createReader(jsonStream);
            JsonArray array = jsonReader.readArray();

            jsonReader.close();
            connection.disconnect();

            Gson gson = new GsonBuilder().create();


            System.out.println(" ---------------------------EVENTS---------------------------- ");
            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.getJsonObject(i);

                Event event = gson.fromJson(object.toString(), Event.class);
                System.out.println("\t" + event);
            }
        }
    }

    public static void createEvent(String uri, String authorizationValue, String body) throws IOException {
        URL url = new URL(uri);
        String responseBody = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/xml, */*");

        if (authorizationValue != null) {
            connection.setRequestProperty("Authorization", authorizationValue);
        }

        if (body != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "Application/Json");
            connection.getOutputStream().write(body.getBytes());
        }
        connection.connect();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode + " (" + connection.getResponseMessage() + ")");
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            System.err.println("Authentication failed");
        } else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            System.err.println("You are not authorized");
        } else{
            Scanner s;

            if (connection.getErrorStream() != null) {
                s = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            }

            try {
                s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
            }

            connection.disconnect();

            System.out.println("POST" + " " + uri + (body == null ? "" : " with body: " + body) + " ==> " + responseBody);
            System.out.println();

        }
    }

    public static void consultEventAttendance(String uri, String authorizationValue) throws IOException {
        URL url = new URL(uri);
        String responseBody = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml, */*");

        if (authorizationValue != null) {
            connection.setRequestProperty("Authorization", authorizationValue);
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode + " (" + connection.getResponseMessage() + ")");
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            System.err.println("Authentication failed");
        } else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            System.err.println("You are not authorized");
        } else{
            InputStream jsonStream = connection.getInputStream();
            JsonReader jsonReader = Json.createReader(jsonStream);
            JsonArray array = jsonReader.readArray();
            jsonReader.close();

            Scanner s;

            if (connection.getErrorStream() != null) {
                s = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            }

            try {
                s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
            }

            connection.disconnect();

            if(array.isEmpty()){
                System.out.println("No attendance for this event");
            }else {
                System.out.println("POST" + " " + uri + " ==> " + responseBody);
                System.out.println();
                Gson gson = new GsonBuilder().create();

                JsonObject object1 = array.getJsonObject(0);
                Attendance event = gson.fromJson(object1.toString(), Attendance.class);
                System.out.println("-----------------------------------EVENT----------------------------------------");
                System.out.format("%-15s%-10s%-20s%-10s%-10s\n", event.getEventName(), event.getLocation(),
                        event.getDate(), event.getStartTime(), event.getEndTime());

                System.out.println("\n---------------------------------ATTENDANCE--------------------------------------");
                for (int i = 0; i < array.size(); i++) {
                    JsonObject object2 = array.getJsonObject(i);
                    Attendance attendance = gson.fromJson(object2.toString(), Attendance.class);
                    System.out.println("\t" + attendance);
                }
            }
        }
    }

    private static void deleteEvent(String uri, String authorizationValue) throws IOException {
        URL url = new URL(uri);
        String responseBody = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/xml, */*");

        if (authorizationValue != null) {
            connection.setRequestProperty("Authorization", authorizationValue);
        }

        connection.connect();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode + " (" + connection.getResponseMessage() + ")");
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            System.err.println("Authentication failed");
        } else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            System.err.println("You are not authorized");
        } else{
            Scanner s;

            if (connection.getErrorStream() != null) {
                s = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            }

            try {
                s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
            }

            connection.disconnect();

            System.out.println("POST" + " " + uri + " ==> " + responseBody);
            System.out.println();

        }
    }

    public static void main(String args[]) throws MalformedURLException, IOException {

        String loginUri = "http://localhost:8080/login";
        String registerUri = "http://localhost:8080/register";
        String allEventsUri = "http://localhost:8080/events/get";
        String createEventUri = "http://localhost:8080/events/create";
        String eventAttendanceUri = "http://localhost:8080/events/consultAttendance";
        String deleteEventUri = "http://localhost:8080/events/delete";
        System.out.println();

        // POST /register
        String registerBody = "{\"email\": \"user1@example.com\", \"password\": \"123\"}";
        sendRequestAndShowResponse(registerUri, "POST", null, registerBody);

        String credentials = Base64.getEncoder().encodeToString("admin@example.com:123".getBytes());
        String Token = sendRequestAndShowResponse(loginUri, "POST", "basic " + credentials, null);

        getAllEvents(allEventsUri, "bearer " + Token);

        String newEvent = "{\"name\":\"Nuevo Evento\",\"location\":\"Ubicación Nueva\",\"date\":\"2023-12-31\",\"startTime\":\"12:00\",\"endTime\":\"3:00\"}";
        createEvent(createEventUri,"bearer " + Token,newEvent);

        consultEventAttendance(eventAttendanceUri + "?event=14","bearer " + Token);

        deleteEvent(deleteEventUri + "?event=17","bearer " + Token);


        /*
         *
         *
         *
         * //OK
         * sendRequestAndShowResponse(helloUri, "GET", null, null);
         *
         * //Língua "gr" não suportada
         * sendRequestAndShowResponse(helloUri2, "GET", null, null);
         *
         * //Falta um campo "Authorization: basic ..." válido no cabeçalho do pedido
         * para autenticação básica
         * String token = sendRequestAndShowResponse(loginUri, "POST",null, null);
         *
         * //OK
         * String credentials =
         * Base64.getEncoder().encodeToString("admin:admin".getBytes());
         * token = sendRequestAndShowResponse(loginUri, "POST","basic "+ credentials,
         * null); //Base64(admin:admin) YWRtaW46YWRtaW4=
         *
         * //Falta um campo "Authorization: bearer ..." no cabeçalho do pedido com um
         * token JWT válido
         * sendRequestAndShowResponse(loremUri, "GET", null, null);
         *
         * //OK
         * sendRequestAndShowResponse(loremUri+"?type=word&length=6", "GET", "bearer " +
         * token, null);
         *
         * //PUT não suportado para esta URI
         * sendRequestAndShowResponse(loremUri, "PUT", "bearer " + token, null);
         *
         * //POST sem corpo de mensagem
         * sendRequestAndShowResponse(loremUri, "POST", "bearer " + token, null);
         *
         * //Ok
         * sendRequestAndShowResponse(loremUri, "POST", "bearer " + token,
         * "{\"type\":\"word\",\"length\":4}");
         */
    }


}

