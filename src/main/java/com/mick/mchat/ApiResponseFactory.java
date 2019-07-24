package com.mick.mchat;

import io.javalin.http.Context;

public class ApiResponseFactory {
    public static <T> void ok(Context context, T body){
        context.status(200).result(body.toString());//todo serialize
    }

    public static void notFound(Context context, String message){
        context.status(404).result(message);
    }

    public static void badRequest(Context context, String message){
        context.status(400).result(message);
    }
    public static void forbidden(Context context, String message){
        context.status(403).result(message);
    }

    public static void unauthorized(Context context, String message){
        context.status(401).result(message);
    }

    public static void internalServerError(Context context, String message){
        context.status(500).result(message);
    }

}
