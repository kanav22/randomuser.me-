package www.kanavwadhawan.com.leaf_randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private RecyclerView userSearchRecyclerView;
    private List<Results> mRandomModels = new ArrayList<>();
    private userSearchAdapter userSearchAdapter;
    public List<Results> userSearchresult;
    private Spinner filterSpinner;
    RandomModel p;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Progress dialog to show something is being downloaded

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Loading Results ...");
        mProgressDialog.show();


        userSearchRecyclerView = findViewById(R.id.searchUserRecyclerView);
        filterSpinner = findViewById(R.id.spinner);
        filterSpinner.setOnItemSelectedListener(new ItemSelectedListener());


        FetchUserInfo();


    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(filterSpinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(filterSpinner.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();

                if (parent.getItemAtPosition(pos).toString().equals("Sort by Name")) {


                    //Filter when user selects sort by name

                    userSearchresult = Arrays.asList(p.getResults());


                    Collections.sort(userSearchresult, new Comparator<Results>() {
                        @Override
                        public int compare(Results o1, Results o2) {
                            Results p1 = (Results) o1;
                            Results p2 = (Results) o2;
                            return p1.getName().getFirst().toString().compareToIgnoreCase(p2.getName().getFirst().toString());
                        }
                    });
                    userSearchAdapter = new userSearchAdapter(MainActivity.this, userSearchresult);
                    userSearchRecyclerView.setAdapter(userSearchAdapter);
                    userSearchAdapter.notifyDataSetChanged();


                    // Todo when item is selected by the user
                } else if (parent.getItemAtPosition(pos).toString().equals("Sort by Mobile")) {

                    //Filter when user selects sort by Mobile

                    userSearchresult = Arrays.asList(p.getResults());

                    Collections.sort(userSearchresult, new Comparator<Results>() {
                        @Override
                        public int compare(Results o1, Results o2) {
                            Results p1 = (Results) o1;
                            Results p2 = (Results) o2;
                            return p1.getPhone().compareToIgnoreCase(p2.getPhone());
                        }
                    });
                    userSearchAdapter = new userSearchAdapter(MainActivity.this, userSearchresult);
                    userSearchRecyclerView.setAdapter(userSearchAdapter);
                    userSearchAdapter.notifyDataSetChanged();


                } else if (parent.getItemAtPosition(pos).toString().equals("Sort by Email")) {

                    //Filter when user selects sort by Email

                    userSearchresult = Arrays.asList(p.getResults());
                    //List<Results> sortedList=new ArrayList<Results>();

                    Collections.sort(userSearchresult, new Comparator<Results>() {
                        @Override
                        public int compare(Results o1, Results o2) {
                            Results p1 = (Results) o1;
                            Results p2 = (Results) o2;
                            return p1.getEmail().compareToIgnoreCase(p2.getEmail());
                        }
                    });
                    userSearchAdapter = new userSearchAdapter(MainActivity.this, userSearchresult);
                    userSearchRecyclerView.setAdapter(userSearchAdapter);
                    userSearchAdapter.notifyDataSetChanged();


                } else {


                    //Filter when user selects sort by Birthday
                    userSearchresult = Arrays.asList(p.getResults());

                    Collections.sort(userSearchresult, new Comparator<Results>() {
                        // Take Dateformat format to compares dates
                        DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");


                        @Override
                        public int compare(Results o1, Results o2) {
                            Results p1 = (Results) o1;
                            Results p2 = (Results) o2;
                            try {
                                return f.parse(o1.getDob().getDate()).compareTo(f.parse(o2.getDob().getDate()));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                    });
                    userSearchAdapter = new userSearchAdapter(MainActivity.this, userSearchresult);
                    userSearchRecyclerView.setAdapter(userSearchAdapter);
                    // to show refreshed data in adapter
                    userSearchAdapter.notifyDataSetChanged();


                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


    public void FetchUserInfo() {

        try {


            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://randomuser.me/api/?inc=name,email,dob,phone,picture&results=50";
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson g = new Gson();
                            p = g.fromJson(response.toString(), RandomModel.class);

                            //Log.d("SIZE", p.getResults().length + " ");
                            //userSearchAdapter.addAll(mRandomModels);

                            userSearchAdapter = new userSearchAdapter(MainActivity.this, Arrays.asList(p.getResults()));
                            userSearchRecyclerView.setAdapter(userSearchAdapter);
                            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MainActivity.this);
                            userSearchRecyclerView.setLayoutManager(gridLayoutManager);
                            //userSearchAdapter.notifyDataSetChanged();
                            //Log.d("Response", response);
                            mProgressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR", "error => " + error.toString());
                            mProgressDialog.dismiss();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user-key", " c750173e8cf7e5fdc2c331cf897ee8c3");
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            queue.add(getRequest);
        } catch (IllegalStateException e) {


        } catch (Exception e) {


        }

    }


}
