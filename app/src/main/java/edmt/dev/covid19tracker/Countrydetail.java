package edmt.dev.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Countrydetail extends AppCompatActivity {
    TextView countryname, totalcases, todaycases, totaldeaths,
            todaydeaths, totalrecovered, totalactive, critical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrydetail);countryname = findViewById(R.id.countryname);
        totalcases = findViewById(R.id.totalcases);
        todaycases = findViewById(R.id.todaycases);
        totaldeaths = findViewById(R.id.totaldeaths);
        todaydeaths = findViewById(R.id.todaydeaths);
        totalrecovered = findViewById(R.id.totalrecovered);
        totalactive = findViewById(R.id.totalactive);
        critical = findViewById(R.id.critical);


        // call Covid Country
        Country country = getIntent().getParcelableExtra("EXTRA_COVID");

        // set text view
        countryname.setText(country.getCountry());
        totalcases.setText(country.getCases());
        todaycases.setText(country.gettodaycases());
        totaldeaths.setText(country.getdeaths());
        todaydeaths.setText(country.gettodaydeaths());
        totalrecovered.setText(country.getrecovered());
        totalactive.setText(country.getactive());
        critical.setText(country.getcritical());

    }
}
