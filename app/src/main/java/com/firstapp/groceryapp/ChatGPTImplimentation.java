package com.firstapp.groceryapp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Signature;

public class ChatGPTImplimentation {
    final public static String instructions = "Using the grocery receipt given, place each item from the receipt in one of the following categories: Beverages, Bread/Bakery, Canned/Jarred Goods, Dairy, Dry/Baking Goods, Frozen Foods, Meat, Produce, Personal Care, and Other. Format each item in the form of: Category, item, price. If the price is unknown, put price as UNKNOWN. : ";
    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

   /*
   public static void main(String[] args) {

    System.out.println(chatGPT(instructions + message));
   }
    */
}