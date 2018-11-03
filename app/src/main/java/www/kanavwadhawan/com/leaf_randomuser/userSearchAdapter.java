package www.kanavwadhawan.com.leaf_randomuser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class userSearchAdapter extends RecyclerView.Adapter<userSearchAdapter.UserSearchViewHolder> {
    Context context;
    public List<Results> userSearchresult;
   List<Results> results;
    @NonNull
    @Override
    public UserSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_action_list_item, null);
        Log.i("TAG","onCreateViewHolder");
        // create ViewHolder
        return new UserSearchViewHolder(itemLayoutView);
    }

    public userSearchAdapter(Context context, List<Results> restaurantsArrayListInfo) {
        this.context = context;
        this.userSearchresult=restaurantsArrayListInfo;


    }

    @Override
    public void onBindViewHolder(@NonNull userSearchAdapter.UserSearchViewHolder holder, int position) {





        holder.resName.setText(userSearchresult.get(position).getName().getFirst()+" "+userSearchresult.get(position).getName().getLast());
        holder.user_mobile.setText(userSearchresult.get(position).getPhone().toString());

        String filename = userSearchresult.get(position).getDob().getDate();     // full file name
        int iend = filename.indexOf("T");
        String subString="";

        // for showing only DOB
        if (iend != -1)
        {
            subString= filename.substring(0 , iend); //this will give abc
        }

        holder.user_dob.setText(subString);

        holder.user_email.setText(userSearchresult.get(position).getEmail());
        Picasso.with(context)
                .load(userSearchresult.get(position).getPicture().getMedium())
                .into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return userSearchresult.size();
    }


    protected class UserSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView resName;
        final CircleImageView userImage;
        final TextView user_mobile;
        final TextView user_email;
        final TextView user_dob;

        UserSearchViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            resName= itemLayoutView.findViewById(R.id.place_name);
            userImage =itemLayoutView.findViewById(R.id.place_image);
            user_mobile =itemLayoutView.findViewById(R.id.place_description);
            user_email =itemLayoutView.findViewById(R.id.place_budget);
            user_dob = itemLayoutView.findViewById(R.id.place_location);

        }

        @Override
        public void onClick(View v) {

        }


    }
    private void add(Results users) {
        userSearchresult.add(users);
        notifyItemInserted(userSearchresult.size() - 1);
    }

    public void addAll(List<Results> usersList) {
        for (Results restaurants : usersList) {
            add(restaurants);
        }
    }


}
