package com.sep.carsharingbusiness.extentions;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeJsonAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)); // "yyyy-MM-dd'T'HH:mm:ssZ"
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss"));
        }
    }
}