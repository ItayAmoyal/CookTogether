package com.example.cooktogether;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.ImagePart;
import com.google.ai.client.generativeai.type.Part;
import com.google.ai.client.generativeai.type.TextPart;

import java.util.ArrayList;
import java.util.List;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class GeminiManager {
    private static GeminiManager instance;
    GenerativeModel gemini;

    public GeminiManager() {
        gemini = new GenerativeModel("gemini-2.0-flash", BuildConfig.Gemini_API_Key);
    }

    public static GeminiManager getInstance() {
        if (instance == null) {
            instance = new GeminiManager();
        }
        return instance;
    }

    public void sendTextWithPrompt(String prompt, Bitmap photo, GeminiCallBack callBack) {
        List<Part> parts = new ArrayList<>();
       Bitmap  newphoto = downscale(photo, 512);
        parts.add(new TextPart(prompt));
        parts.add(new ImagePart(newphoto));
        Content[] content = new Content[1];
        content[0] = new Content(parts);
        gemini.generateContent(content, new Continuation<GenerateContentResponse>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                if (o instanceof Result.Failure) {
                    callBack.onFaliure(((Result.Failure) o).exception);
                } else {
                    callBack.onSuccess(((GenerateContentResponse) o).getText());
                }
            }
        });

    }

    private Bitmap downscale(Bitmap src, int maxSize) {
        int w = src.getWidth();
        int h = src.getHeight();
        float scale = Math.min((float) maxSize / w, (float) maxSize / h);
        if (scale >= 1f) return src;
        int nw = Math.round(w * scale);
        int nh = Math.round(h * scale);
        return Bitmap.createScaledBitmap(src, nw, nh, true);
    }
}
