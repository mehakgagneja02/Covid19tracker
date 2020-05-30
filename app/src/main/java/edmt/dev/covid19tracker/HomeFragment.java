package edmt.dev.covid19tracker;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    TextView txttotaldeaths,txttotalrecovered,txttotalconfirmed,txtupdated;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

    txttotaldeaths= root.findViewById(R.id.txttotaldeaths);
    txttotalconfirmed=root.findViewById(R.id.txttotalconfirmed);
    txttotalrecovered=root.findViewById(R.id.txttotalrecovered);
    progressBar=root.findViewById(R.id.progress_circular_home);
    txtupdated=root.findViewById(R.id.txtupdated);

    getData();



    return root;
    }

    private String getDate(long milliSecond){
        SimpleDateFormat f = new SimpleDateFormat("EEE. dd MM yyyy hh:mm:ss aaa");
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTimeInMillis(milliSecond);
        return f.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        String s = "https://corona.lmao.ninja/v2/all";
        StringRequest sr = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject j = new JSONObject(response.toString());
                    txttotalconfirmed.setText(j.getString("cases"));
                    txttotaldeaths.setText(j.getString("deaths"));
                    txttotalrecovered.setText(j.getString("recovered"));
                    txtupdated.setText("Last Updated"+"\n"+getDate(j.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error res", error.toString());

            }
    });
        rq.add(sr);
    }
}