package com.example.mediworld;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAIMedicalAssitant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAIMedicalAssitant extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserAIMedicalAssitant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAIMedicalAssitant.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAIMedicalAssitant newInstance(String param1, String param2) {
        UserAIMedicalAssitant fragment = new UserAIMedicalAssitant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_a_i_medical_assitant, container, false);

        EditText medicineInputEditText = view.findViewById(R.id.medicine_input);
        Button submitButton = view.findViewById(R.id.submit_button);
        TextView botResponseTextView = view.findViewById(R.id.bot_response);

        // Initialize the inputMethodManager object
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = medicineInputEditText.getText().toString();
                String apiUrl = "https://api.fda.gov/drug/drugsfda.json?search=substance_name:" + medicineName + "&limit=1";

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                                    // Check if there are any results
                                    if (resultsArray.length() == 0) {
                                        botResponseTextView.setText("No information found for " + medicineName);
                                        return;
                                    }

                                    // Get the first result
                                    JSONObject resultObject = resultsArray.getJSONObject(0);
                                    JSONObject openFdaObject = resultObject.getJSONObject("openfda");

                                    // Get the substance name
                                    String substanceName = "";
                                    if (openFdaObject.has("substance_name")) {
                                        JSONArray substanceNameArray = openFdaObject.getJSONArray("substance_name");
                                        if (substanceNameArray.length() > 0) {
                                            substanceName = substanceNameArray.getString(0);
                                        }
                                    }

                                    // Get the brand name
                                    String brandName = "";
                                    if (openFdaObject.has("brand_name")) {
                                        JSONArray brandNameArray = openFdaObject.getJSONArray("brand_name");
                                        if (brandNameArray.length() > 0) {
                                            brandName = brandNameArray.getString(0);
                                        }
                                    }

                                    // Get the product type
                                    String productType = "";
                                    if (openFdaObject.has("product_type")) {
                                        JSONArray productTypeArray = openFdaObject.getJSONArray("product_type");
                                        if (productTypeArray.length() > 0) {
                                            productType = productTypeArray.getString(0);
                                        }
                                    }

                                    // Set the information in the text view
                                    String info = "Substance Name: " + substanceName + "\n"
                                            + "Brand Name: " + brandName + "\n"
                                            + "Product Type: " + productType;
                                    botResponseTextView.setText(info);

                                    // Hide the keyboard
                                    inputMethodManager.hideSoftInputFromWindow(medicineInputEditText.getWindowToken(), 0);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        botResponseTextView.setText("An error occurred while getting information for " + medicineName);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer sYn8sqCsRoD3qEyasyK1fGxNJFuA6BGQAdcZbotr");
                        return headers;
                    }
                };

                queue.add(stringRequest);
            }
        });


        // Return the inflated view
        return view;
    }}
