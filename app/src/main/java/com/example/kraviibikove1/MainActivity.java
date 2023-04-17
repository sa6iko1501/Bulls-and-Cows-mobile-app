package com.example.kraviibikove1;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kraviibikove1.Models.Digit;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> numbers = numbersGen();
    private LinearLayout linearLayout;
    private int attempts = 0;
    private ScrollView scrollView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hint");
        builder.setMessage("Guess the 4 digit number. RED is for a digit that is in the correct position, YELLOW is for a digit that is in the wrong position and WHITE is for a digit that is not contained in the sequence");
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText = findViewById(R.id.numInput);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                guessAttempt();
                return true;
            }
            return false;
        });
        linearLayout = findViewById(R.id.linear_layout);
    }

    private  List<Integer> numbersGen() {
        Random rand = new Random();
        List<Integer> numList = new ArrayList<>();
        for(int i=0;i<4;i++){
            int num = rand.nextInt(10);
            if(numList.isEmpty()&&num!=0){
                numList.add(num);
            }
            else{
                if(numList.contains(num)||num==0){
                    i--;
                }
                else{
                    numList.add(num);
                }
            }
        }
        return numList;
    }

    public void getInputButton(View view) {
        editText = findViewById(R.id.numInput);
        guessAttempt();
    }

    private void guessAttempt(){
        attempts++;
        String input = editText.getText().toString();
        if(input.length()!=4){
            return;
        }
        ArrayList<Digit> digits = new ArrayList<>();
        for(int i=0;i<4;i++){
            digits.add(new Digit(Character.getNumericValue(input.charAt(i)),i,(ArrayList<Integer>) numbers));
        }
        printGuessToTextView(linearLayout,digits);
    }
    private void printGuessToTextView(LinearLayout linearLayout, ArrayList<Digit> digits){
        LinearLayout rowLayout = createRow();
        int counter = 0;
        for(int i=0;i<4;i++){
            TextView textToRow = createNewEntry(digits.get(i),digits.get(i).cowOrBull());
            if(digits.get(i).cowOrBull()==2){
                counter++;
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            params.setMargins(
                    i == 0 ? 0 : 1, // left margin for all TextViews except the first one
                    0,              // top margin
                    i == 3 ? 0 : 1, // right margin for all TextViews except the last one
                    0               // bottom margin
            );
            textToRow.setLayoutParams(params);
            rowLayout.addView(textToRow);
        }
        linearLayout.addView(rowLayout);
        scrollView = findViewById(R.id.scroll_view);
        if(counter==4){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You guessed the number "+printNumber(numbers)+" with only "+attempts+" attempts!");
            builder.setMessage("Would you like to play another round?");
            builder.setPositiveButton("Yes", (dialog, which) -> Restart());
            builder.setNegativeButton("No",null);
            builder.show();
        }
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    private LinearLayout createRow(){
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(Gravity.CENTER);
        return rowLayout;
    }

    private String printNumber(List<Integer> numbers){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            sb.append(numbers.get(i));
        }
        return sb.toString();
    }
    private TextView createNewEntry(Digit digit, int valueOfMatch){
        TextView entry = new TextView(this);
        String number = String.valueOf(digit.getNumber());
        entry.setText(number);
        entry.setTextSize(24);
        entry.setTypeface(null, Typeface.BOLD);
        if(valueOfMatch!=0){
            if(valueOfMatch==2){
                entry.setTextColor(0xFF880808);
            }
            else{
                entry.setTextColor(0xFFFFA500);
            }
        }
        else{
            entry.setTextColor(0xFFFFFFFF);
        }
        entry.setGravity(Gravity.CENTER);
        return entry;
    }

    private void Restart(){
        linearLayout.removeAllViews();
        while(!numbers.isEmpty()){
            numbers.remove(0);
        }
        numbers = numbersGen();
        attempts = 0;
    }
}