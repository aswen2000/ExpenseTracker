package com.example.sqlrecyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.example.sqlrecyclerviewtest.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.sqlrecyclerviewtest.EXTRA_DESCRIPTION";
//    public static final String EXTRA_PRIORITY =
//            "com.example.sqlrecyclerviewtest.EXTRA_PRIORITY";
    public static final String EXTRA_AMOUNT =
            "com.example.sqlrecyclerviewtest.EXTRA_AMOUNT";
    public static final String EXTRA_ID =
            "com.example.sqlrecyclerviewtest.EXTRA_ID";
    public static final String EXTRA_YEAR =
            "com.example.sqlrecyclerviewtest.EXTRA_YEAR";
    public static final String EXTRA_MONTH =
            "com.example.sqlrecyclerviewtest.EXTRA_MONTH";
    public static final String EXTRA_DAYOFMONTH =
            "com.example.sqlrecyclerviewtest.EXTRA_DAYOFMONTH";

    private EditText editTextTitle;
    private EditText editTextDescription;
//    private NumberPicker numberPickerPriority;
    private DatePicker datePicker;
    private EditText editTextAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
//        numberPickerPriority = findViewById(R.id.number_picker_priority);
        editTextAmount = findViewById(R.id.edit_text_amount);
        datePicker = findViewById(R.id.date_picker);

//        numberPickerPriority.setMinValue(1);
//        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
//            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            editTextAmount.setText(intent.getStringExtra(EXTRA_AMOUNT));
            //TODO: Figure out how to set the date instead of it defaulting to the default values (USE A TEXTVIEW???)
//            System.out.println(intent.getIntExtra(EXTRA_PRIORITY, 3) + " xps1");
            datePicker.updateDate(intent.getIntExtra(EXTRA_YEAR, 89), intent.getIntExtra(EXTRA_MONTH,4), intent.getIntExtra(EXTRA_DAYOFMONTH,20));



        }else{
            setTitle("Add Note");
        }
        System.out.println("Made it to end of AddEditNoteActivity onCreate xps");
    }

    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
//        int priority = numberPickerPriority.getValue();
        String amount = editTextAmount.getText().toString();
//        System.out.println(priority + " xps");
        int year1 = datePicker.getYear();
        System.out.println(year1 + " xps");
        int month1 = datePicker.getMonth();
        System.out.println(month1 + " xps");
        int dayOfMonth1 = datePicker.getDayOfMonth();
        System.out.println(dayOfMonth1 + " xps");

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_YEAR, year1);
        data.putExtra(EXTRA_MONTH, month1);
        data.putExtra(EXTRA_DAYOFMONTH, dayOfMonth1);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}