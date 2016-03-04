package com.example.shayanths.represent;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ShayanthS on 3/2/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<PersonData> peopleDataSet;
    private ArrayList<PollData> pollDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        CardView cardLayout;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        TextView text6;
        TextView text7;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                this.textViewName = (TextView) itemView.findViewById(R.id.firstName);
                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
                this.cardLayout = (CardView) itemView.findViewById(R.id.card_view);
            }
            else if (viewType == 1){
                this.text1 = (TextView) itemView.findViewById(R.id.textView2);
                this.text2 = (TextView) itemView.findViewById(R.id.textView3);
                this.text3 = (TextView) itemView.findViewById(R.id.textView4);
                this.text4 = (TextView) itemView.findViewById(R.id.textView5);
                this.text5 = (TextView) itemView.findViewById(R.id.textView6);
                this.text6 = (TextView) itemView.findViewById(R.id.textView7);
                this.text7 = (TextView) itemView.findViewById(R.id.textView8);
            }

        }
    }
    public MyAdapter(ArrayList<PersonData> people, ArrayList<PollData> poll) {
        this.peopleDataSet = people;
        this.pollDataSet = poll;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view;
        MyViewHolder myViewHolder;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.results, parent, false);

                view.setOnClickListener(MainWearActivity.myOnClickListener);

                myViewHolder = new MyViewHolder(view, viewType);
                return myViewHolder;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cards_layout, parent, false);

                view.setOnClickListener(MainWearActivity.myOnClickListener);

                myViewHolder = new MyViewHolder(view, viewType);
                return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        if (listPosition == 3){

            TextView textView1 = holder.text1;
            TextView textView2 = holder.text2;
            TextView textView3 = holder.text3;
            TextView textView4 = holder.text4;
            TextView textView5 = holder.text5;
            TextView textView6 = holder.text6;
            TextView textView7 = holder.text7;

            textView1.setText("Voting Results");
            textView2.setText(pollDataSet.get(0).getCounty());
            textView3.setText(pollDataSet.get(0).getState());
            textView4.setText(pollDataSet.get(0).getPresident0());
            textView5.setText(pollDataSet.get(0).getPresident1());
            textView6.setText(pollDataSet.get(0).getVote0());
            textView7.setText(pollDataSet.get(0).getVote1());
        }
        else {
            TextView textViewName = holder.textViewName;
            ImageView imageView = holder.imageViewIcon;
            CardView cardLayout = holder.cardLayout;

            String contentName = peopleDataSet.get(listPosition).getName();
            String party = peopleDataSet.get(listPosition).getParty();

            textViewName.setText(contentName);
            imageView.setImageResource(peopleDataSet.get(listPosition).getImage());
            if (party.equals("Republican")) {
                cardLayout.setBackgroundColor(Color.parseColor("#DC143C"));
                cardLayout.setUseCompatPadding(true);
                cardLayout.setCardElevation(5);
            } else {
                cardLayout.setBackgroundColor(Color.parseColor("#1E90FF"));
                cardLayout.setUseCompatPadding(true);
                cardLayout.setCardElevation(5);

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //Default is 1
        if (position == 3) viewType = 1; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public int getItemCount() {
        return peopleDataSet.size();
    }
}