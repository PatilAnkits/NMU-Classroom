package com.unveil.temp.viewholder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unveil.temp.R;

public class BranchViewHolder extends RecyclerView.ViewHolder {
    Button button;
    public BranchViewHolder(@NonNull View itemView) {
        super (itemView);
        button = itemView.findViewById (R.id.button_branch);
    }

    public void setText(String text){
        button.setText (text);
    }

    public void setOnClick(View.OnClickListener onClickListener){
        button.setOnClickListener (onClickListener);
    }
}
