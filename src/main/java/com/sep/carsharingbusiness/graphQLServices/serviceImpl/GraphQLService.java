package com.sep.carsharingbusiness.graphQLServices.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sep.carsharingbusiness.extentions.LocalDateTimeJsonAdapter;
import com.sep.carsharingbusiness.log.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GraphQLService {

    private static final String GRAPHQL_URI = "http://localhost:5004/graphql?=";
    private static final Gson gson =
            new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
                    .create();

    public static HttpResponse<String> sendQuery(String query) throws IOException, InterruptedException {
        Log.addLog("|graphQlServices/GraphQLService.sendQuery| : Request : " + query);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_URI))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        query
                ))
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> ArrayList<T> createListQuery(String query, String naming, Class<T> arrType) throws IOException, InterruptedException {
        HttpResponse<String> response = sendQuery(query);

        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);

        if (obj.has("errors")) {
            String errorMessage = obj.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();
            Log.addLog("|graphQlServices/GraphQLService.createListQuery| : Error : " + errorMessage);
            throw new InternalError(errorMessage);
        }

        JsonArray arr = obj.get("data").getAsJsonObject().get(naming).getAsJsonArray();
        Log.addLog("|graphQlServices/GraphQLService.createListQuery| : Reply : " + arr);

        Type typeOfT = TypeToken.getParameterized(List.class, arrType).getType();
        return gson.fromJson(arr, typeOfT);
    }

    public static <T> T createObjQuery(String query, String naming, Class<T> objType) throws IOException, InterruptedException {
        HttpResponse<String> response = sendQuery(query);

        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);

        if (obj.has("errors")) {
            String errorMessage = obj.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();
            Log.addLog("|graphQlServices/GraphQLService.createObjQuery| : Error : " + errorMessage);
            throw new InternalError(errorMessage);
        }

        obj = obj.get("data").getAsJsonObject().get(naming).getAsJsonObject();
        Log.addLog("|graphQlServices/GraphQLService.createObjQuery| : Reply : " + obj);
        return gson.fromJson(obj, objType);
    }

    public static boolean createBooleanResponse(HttpResponse<String> response, String naming) {

        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);

        if (obj.has("errors")) {
            String errorMessage = obj.get("errors").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();
            Log.addLog("|graphQlServices/GraphQLService.createBooleanResponse| : Error : " + errorMessage);
            throw new InternalError(errorMessage);
        }

        boolean status = obj.get("data").getAsJsonObject().get(naming).getAsBoolean();

        Log.addLog("|graphQlServices/GraphQLService.createBooleanResponse| : Reply : " + status);
        return status;
    }

    public static String getQueryFromFile(String fileName, boolean isMutation) throws IOException {
        String path = "src/main/java/com/sep/carsharingbusiness/" + (isMutation ? "mutations/" : "queries/");
        List<String> strings = ((Files.readAllLines(Path.of(path + fileName))));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"query\":\"");
        strings.forEach(stringBuilder::append);
        stringBuilder.append("\"}");

        return stringBuilder.toString();
    }

}
