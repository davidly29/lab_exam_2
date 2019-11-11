package com.example.lab_exam_two;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.Myclass> {
    Context context;
    ArrayList<RetriveParams> retriveParams;
    private RecyclerView mRecyclerV;

    public ExtraAdapter(Context context, ArrayList<RetriveParams> retriveParams, RecyclerView recyclerView) {
        this.context = context;
        this.retriveParams = retriveParams;
        this.mRecyclerV = recyclerView;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowfile, parent, false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, final int position) {
        final RetriveParams params = retriveParams.get(position);
        holder.textViewName.setText(params.getName());
        holder.textViewNumber.setText(params.getNumber());
        holder.textViewEmail.setText(params.getEmail());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        Intent intent = new Intent(context, UpdateActivity.class);
                        intent.putExtra("name", params.getName());
                        intent.putExtra("number", params.getNumber());
                        intent.putExtra("email", params.getEmail());
                        context.startActivity(intent);
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbAdapter help = new DbAdapter(context);
                        help.delete(params.getName(), context);
                        retriveParams.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, retriveParams.size());
                        notifyDataSetChanged();
//                        PersonDBHelper dbHelper = new PersonDBHelper(mContext);
//                        dbHelper.deletePersonRecord(person.getId(), mContext);
//
//                        mPeopleList.remove(position);
//                        mRecyclerV.removeViewAt(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, mPeopleList.size());
//                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("CALLED");
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return retriveParams.size();
    }

    public class Myclass extends RecyclerView.ViewHolder{
        public View layout;
        TextView textViewName, textViewNumber, textViewEmail;
        ImageView imageView;
        public Myclass(View item){
            super(item);
            layout = item;
            textViewName = item.findViewById(R.id.tv_name);
            textViewNumber = item.findViewById(R.id.tv_number);
            textViewEmail = item.findViewById(R.id.tv_email);
            imageView = item.findViewById(R.id.image_delete);

        }
    }
}
