package edmt.dev.covid19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class CountryFragment extends Fragment {
    RecyclerView rv;
    ProgressBar progressBar;
    private  static final String TAG =CountryFragment.class.getSimpleName();
    ArrayList<Country> countries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        rv = root.findViewById(R.id.rv);
        progressBar=root.findViewById(R.id.progress_circular_country);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line));
        rv.addItemDecoration(dividerItemDecoration);

        getDataf();
        return root;
    }
    private void show(){
        CountryAdapter countryAdapter = new CountryAdapter(countries);
        rv.setAdapter(countryAdapter);

        ItemClick.addTo(rv).setOnItemClickListener(new ItemClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCountry(countries.get(position));
            }
        });
    }

    private void showSelectedCountry(Country country){
        Intent Countrydetail = new Intent(getActivity(), Countrydetail.class);
        Countrydetail.putExtra("EXTRA_COVID", country);
        startActivity(Countrydetail);


    }
    private void getDataf(){
        String str = "https://corona.lmao.ninja/v2/countries";
        countries = new ArrayList<>();
        StringRequest strreq = new StringRequest(Request.Method.GET, str, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "Response" + response);
                    try {
                        JSONArray ja = new JSONArray(response);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject data = ja.getJSONObject(i);
                            //countries.add(new Country(data.getString("country"),data.getString("cases")));
                           // countries.add(new Country(data.getString("country"),data.getString("cases")));
                            countries.add(new Country(data.getString("country"),data.getString("cases"),
                                    data.getString("todayCases"), data.getString("deaths"),
                                    data.getString("todayDeaths"), data.getString("recovered"),
                                    data.getString("active"), data.getString("critical")
                            ));
                        }
                        show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "Response" + error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(strreq);
    }
}