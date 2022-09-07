package com.henzmontera.cap102_plantapp;

import static java.sql.Types.NULL;

import android.os.Bundle;
import android.util.Log;
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
    private UserPostAdapter useradapt;
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
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                useradapt.notifyDataSetChanged();
                swiperefresh.setRefreshing(false);
            }
        });
        return rootview;
    }

    private void GetLatestPost(){
        String url = "http://192.168.254.100/networkingbased/DisplayLatestPost.php";

        RequestQueue q = Volley.newRequestQueue(getActivity());

        StringRequest r = new StringRequest( //Request String type
                Request.Method.GET, //Get or Retrieve only Method of request
                url,
                response -> {
                    Log.d("length", response.length()+"");
                    try{
                        JSONObject oh = new JSONObject(response);
                        JSONArray latestpost = oh.getJSONArray("LatestPost");

                        if(latestpost.length() == NULL){
                            DataErrorTextView.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        } else {
                            DataErrorTextView.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);

                            for(int i = 0; i<latestpost.length();i++){
                                JSONObject al = latestpost.getJSONObject(i);

                                ListPost post = new ListPost(
                                        al.optString("postUserId"),
                                        al.optString("postId"),
                                        al.optString("username"),
                                        al.optString("postDescriptions"),
                                        al.optString("postTime"),
                                        al.optString("commentsCount"),
                                        al.optString("likeCount"),
                                        al.optInt("userprofilepicture"),
                                        al.optInt("postImages")
                                );
                                listposts.add(post);
                                useradapt = new UserPostAdapter(getActivity(), listposts);
                                recyclerview.setAdapter(useradapt);
                            }
                        }
                    }
                    catch(Exception e){

                    }
                }, error -> Toast.makeText(getActivity(), "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show());
        q.add(r);

    }
}