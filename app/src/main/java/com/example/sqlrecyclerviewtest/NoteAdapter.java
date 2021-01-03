package com.example.sqlrecyclerviewtest;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private onItemClickListener listener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
//                    oldItem.getPriority() == newItem.getPriority() &&
                    oldItem.getAmount().equals(newItem.getAmount()) &&
                    oldItem.getYear() == newItem.getYear() &&
                    oldItem.getMonth() == newItem.getMonth() &&
                    oldItem.getDayOfMonth() == newItem.getDayOfMonth();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        String st;
        String temp = currentNote.getMonth() + "/" + currentNote.getDayOfMonth() + "/" + currentNote.getYear();
//        holder.textViewTitle.setText(currentNote.getTitle() + "- " + currentNote.getMonth() +"/" + currentNote.getDayOfMonth() + "/" + currentNote.getYear());

        holder.textViewDescription.setText(currentNote.getDescription());
//        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textViewAmount.setText("$" + currentNote.getAmount());

        st = currentNote.getAmount();

//        yourTextView.setText(String.format("Value of a: %.2f", a));

        String s= currentNote.getTitle() + "- " + currentNote.getMonth() +"/" + currentNote.getDayOfMonth() + "/" + currentNote.getYear();
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(.75f), currentNote.getTitle().length()+2,s.length(), 0); // set size
        holder.textViewTitle.setText(ss1);

    }

    public Note getNoteAt(int pos){
        return getItem(pos);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewDate;
        private TextView textViewAmount;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDate = itemView.findViewById(R.id.date_picker);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(pos));
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
