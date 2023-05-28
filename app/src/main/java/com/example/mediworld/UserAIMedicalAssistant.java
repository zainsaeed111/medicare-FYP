package com.example.mediworld;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mediworld.Adapters.MessageAdapter;
import com.example.mediworld.Models.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserAIMedicalAssistant extends Fragment {

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private EditText mEditText;
    private ImageView mButton;
    private String apiUrl = "https://api.openai.com/v1/completions";
    private String accessToken = "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";
    private List<ChatMessage> mMessages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_assistant, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recylerviewAI);
        mEditText = view.findViewById(R.id.etMessageAI);
        mButton = view.findViewById(R.id.sendMessageAI);
        mMessages = new ArrayList<>();
        mAdapter = new MessageAdapter(mMessages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });
    }

    private void callAPI() {
        String text = mEditText.getText().toString();
        mMessages.add(new ChatMessage(text, true));
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        mEditText.getText().clear();
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("model", "text-davinci-003");
            requestBody.put("prompt", text);
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 1);
            requestBody.put("top_p", 1);
            requestBody.put("frequency_penalty", 0.0);
            requestBody.put("presence_penalty", 0.0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray choicesArray = response.getJSONArray("choices");
                            JSONObject choiceObject = choicesArray.getJSONObject(0);
                            String text = choiceObject.getString("text");
                            Log.e("API Response", response.toString());
                            mMessages.add(new ChatMessage(text.replaceFirst("\n", "").replaceFirst("\n", ""), false));
                            mAdapter.notifyItemInserted(mMessages.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleAPIError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                handleRateLimit(response);
                return super.parseNetworkResponse(response);
            }
        };

        int timeoutMs = 25000; // 25 seconds timeout
        RetryPolicy policy = new DefaultRetryPolicy(timeoutMs, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        MySingleton.getInstance(requireContext()).addToRequestQueue(request);
    }

    private void handleAPIError(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.statusCode == 429) {
            retryAPIAfterDelay();
        } else {
            // Handle other types of errors
        }
    }

    private void handleRateLimit(NetworkResponse response) {
        String retryAfter = response.headers.get("Retry-After");
        if (retryAfter != null) {
            int delaySeconds = Integer.parseInt(retryAfter);
            retryAPIAfterDelay(delaySeconds);
        }
    }

    private void retryAPIAfterDelay() {
        int delayMilliseconds = 5000; // Retry after 5 seconds (adjust as needed)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callAPI();
            }
        }, delayMilliseconds);
    }

    private void retryAPIAfterDelay(int delaySeconds) {
        int delayMilliseconds = delaySeconds * 1000; // Convert seconds to milliseconds
        retryAPIAfterDelay(delayMilliseconds);
    }
}