package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {

    private TextView tvTimer;
    private TextView tvResult;
    private ImageView ivShowImage;
    private HashMap<String, Integer> map = new HashMap<>();
    private ArrayList<String> techList = new ArrayList<>();
    private int index;
    private Button btn1, btn2, btn3, btn4;
    private TextView tvPoints;
    private int points;
    private CountDownTimer countDownTimer;
    private long millisUntilFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        ivShowImage = findViewById(R.id.ivShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        index = 0;
        techList.add("Bootstrap");
        techList.add("C");
        techList.add("Codeigniter");
        techList.add("Cplusplus");
        techList.add("Csharp");
        techList.add("Css3");
        techList.add("Github");
        techList.add("Html5");
        techList.add("Java");
        techList.add("jQuery");
        techList.add("MySQL");
        techList.add("Nodejs");
        techList.add("Php");
        techList.add("Python");
        techList.add("Wordpress");
        techList.add("Android");
        map.put(techList.get(0), R.drawable.bootstrap);
        map.put(techList.get(1), R.drawable.c);
        map.put(techList.get(2), R.drawable.codeigniter);
        map.put(techList.get(3), R.drawable.cplusplus);
        map.put(techList.get(4), R.drawable.csharp);
        map.put(techList.get(5), R.drawable.css3);
        map.put(techList.get(6), R.drawable.github);
        map.put(techList.get(7), R.drawable.html5);
        map.put(techList.get(8), R.drawable.java);
        map.put(techList.get(9), R.drawable.jquery);
        map.put(techList.get(10), R.drawable.mysql);
        map.put(techList.get(11), R.drawable.nodejs);
        map.put(techList.get(12), R.drawable.php);
        map.put(techList.get(13), R.drawable.python);
        map.put(techList.get(14), R.drawable.wordpress);
        map.put(techList.get(15), R.drawable.android);
        Collections.shuffle(techList);
        millisUntilFinished = 10000;
        points = 0;
        startGame();
    }

    @SuppressLint("SetTextI18n")
    private void startGame() {
        millisUntilFinished = 10000;
        tvTimer.setText((millisUntilFinished / 1000) + "s");
        tvPoints.setText(points + " / " + techList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > techList.size() - 1) {
                    ivShowImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartGame.this, GameOver.class);
                    intent.putExtra("points", points);
                    startActivity(intent);
                    finish();
                } else {
                    startGame();
                }
            }
        }.start();
    }

    private void generateQuestions(int index) {
        ArrayList<String> techListTemp = (ArrayList<String>) techList.clone();
        String correctAnswer = techList.get(index);
        techListTemp.remove(correctAnswer);
        Collections.shuffle(techListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(techListTemp.get(0));
        newList.add(techListTemp.get(1));
        newList.add(techListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        ivShowImage.setImageResource(map.get(techList.get(index)));
    }

    public void nextQuestion(View view) {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        countDownTimer.cancel();
        index++;
        if (index > techList.size() - 1) {
            ivShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        } else {
            startGame();
        }
    }

    @SuppressLint("SetTextI18n")
    public void answerSelected(View view) {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = techList.get(index);
        if (answer.equals(correctAnswer)) {
            points++;
            tvPoints.setText(points + " / " + techList.size());
            tvResult.setText("Correct");
        } else {
            tvResult.setText("Wrong");
        }
    }
}