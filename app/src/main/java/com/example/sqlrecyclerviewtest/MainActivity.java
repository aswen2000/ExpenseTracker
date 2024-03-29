package com.example.sqlrecyclerviewtest;

/*THE FRAMEWORK AND GENERAL LAYOUT OF THIS APP WAS CREATED BY CODING IN FLOW ON YOUTUBE.
* I HAVE JUST WATCHED HIS TUTORIALS AND PLAYED AROUND WITH HIS CODE TO FIT MY NEEDS*/

//TODO: Determine requirements for, and set up, stats page
//TODO: Be able to look back at expense amount over a certain time period (in stats)

//TODO: Add tagging functionality (food, gas, utilites, alc, etc.)
//TODO: Add windows or popouts for calendar and tags in Add/Edit to reduce clutter

//TODO: Try to use open source UI/UX libraries
//TODO: Fix bug where "Note not saved" shows in toast on back pressed from statsActivity
//TODO: Add logo
//TODO: Be able to search through titles/descriptions
//TODO: Add safe guard for delete all
//TODO: Be able to add images of receipts
//TODO: Add cold start loading screen

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int STATS_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonStats = findViewById(R.id.button_stats);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivityForResult(intent, STATS_REQUEST);
            }
        });

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(MainActivity.this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_AMOUNT, note.getAmount());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_YEAR, note.getYear());
                intent.putExtra(AddEditNoteActivity.EXTRA_MONTH, note.getMonth());
                intent.putExtra(AddEditNoteActivity.EXTRA_DAYOFMONTH, note.getDayOfMonth());
                intent.putExtra(AddEditNoteActivity.EXTRA_DATEVALUE, note.getDateValue());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            double amount = data.getDoubleExtra(AddEditNoteActivity.EXTRA_AMOUNT, 1);
            int year = data.getIntExtra(AddEditNoteActivity.EXTRA_YEAR, 1);
            int month = data.getIntExtra(AddEditNoteActivity.EXTRA_MONTH, 1);
            int dayOfMonth = data.getIntExtra(AddEditNoteActivity.EXTRA_DAYOFMONTH, 1);

            int dateValue = ((year*1000)+(month*10)+(dayOfMonth));

            Note note = new Note(title, description, amount, year, month, dayOfMonth, dateValue);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            
        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            double amount = data.getDoubleExtra(AddEditNoteActivity.EXTRA_AMOUNT,1);
            int year = data.getIntExtra(AddEditNoteActivity.EXTRA_YEAR, 2000);
            int month = data.getIntExtra(AddEditNoteActivity.EXTRA_MONTH, 5);
            int dayOfMonth = data.getIntExtra(AddEditNoteActivity.EXTRA_DAYOFMONTH, 20);

            int dateValue = ((year*1000)+(month*10)+(dayOfMonth));

            Note note = new Note(title, description, amount /*priority*/, year, month, dayOfMonth, dateValue);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}