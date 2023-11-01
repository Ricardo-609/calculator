package com.ricardo.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.test.R;

import java.util.List;

/* 该类将绑定RecyclerView和数据集 */
public class CalculatorAaptor extends RecyclerView.Adapter<CalculatorAaptor.ViewHolder> {

    private List<Button> mButtons;
    private static String[] data = {"9", "8", "7", "+", "6", "5", "4", "-", "3", "2", "1", "*", "Del", "0", "=", "/"};

    public interface OnItemOnClickListener {
        void onItemOnClick(View view, int pos);
    }
    private OnItemOnClickListener onItemOnClickListener;
    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
        this.onItemOnClickListener = listener;
    }

    public CalculatorAaptor(List<Button> buttons) {
        mButtons = buttons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculatorAaptor.ViewHolder holder, int position) {


        holder.mButton.setText(data[position]);

//        Log.d("+++++++++++++++", String.valueOf(position));

        if (onItemOnClickListener != null) {
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemOnClickListener.onItemOnClick(holder.mButton, position);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.button);
        }

    }

    @Override
    public int getItemCount() {
        return mButtons.size();
    }

    public String getText(int pos) {
        return data[pos];
    }
}
