package com.henzmontera.cap102_plantapp;

import static java.sql.Types.NULL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 *
 *  THIS IS NEWSFEED
 *  Developed by Cyrex Joshua M. Cuizon
 */
public class FirstFragment extends Fragment {

    private SwipeRefreshLayout swiperefresh;
    private PostAdapter useradapt;
    private RecyclerView recyclerview;
    private List<ListPost> listposts;
    private TextView DataErrorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_first, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getActivity().getDrawable(R.drawable.actionbartheme));

        //TextView
        DataErrorTextView = rootview.findViewById(R.id.TextViewError);

        //RecyclerView
        recyclerview = rootview.findViewById(R.id.recyclerFirstFrag);

        //RecyclerView Layout
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listposts = new ArrayList<>();

        //Call Method
        GetLatestPost();

        swiperefresh = rootview.findViewById(R.id.swipeRefreshLayout);
        swiperefresh.setOnRefreshListener(() -> {
            listposts.clear(); //Clear Arraylist
            GetLatestPost();    //Re add the Data into Arraylist again
            swiperefresh.setRefreshing(false); //False to Animation
            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
        });
        return rootview;
    }

    private void GetLatestPost(){
        String url = "http://192.168.254.107/networkingbased/DisplayLatestPost.php";

        RequestQueue q = Volley.newRequestQueue(getActivity());

        StringRequest r = new StringRequest( //Request String type
                Request.Method.GET, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray latestpost = oh.getJSONArray("LatestPost");

                        if (latestpost.length() == NULL) {
                            DataErrorTextView.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        } else {
                            DataErrorTextView.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);

                            for (int i = 0; i < latestpost.length(); i++) {
                                JSONObject al = latestpost.getJSONObject(i);

                                ListPost post = new ListPost(
                                        al.optString("postUserId"),
                                        al.optString("postId"),
                                        al.optString("username"),
                                        al.optString("postDescriptions"),
                                        al.optString("postTime"),
                                        al.optString("commentsCount"),
                                        al.optString("likeCount"),
                                        al.optString("userprofilepicture"),
                                        al.optString("postImages")
                                );
                                listposts.add(post);
                                useradapt = new PostAdapter(getActivity(), listposts);
                                recyclerview.setAdapter(useradapt);
                                useradapt.notifyDataSetChanged();
                            }

                        }
                    } catch (Exception e) {
                    }
                }, error -> {
/*                       if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "TimeoutError/NoConnectionError occurred, please try again later. "+error, Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getActivity(), "AuthFailureError occurred, please try again later. "+error, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getActivity(), "ServerError occurred, please try again later. "+error, Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getActivity(), "NetworkError occurred, please try again later. "+error, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getActivity(), "ParseError occurred, please try again later. "+error, Toast.LENGTH_LONG).show();
                        } */
                });
        q.add(r);
    }
}