package com.hienqp.gamechonhinhdung;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICTURE = 1;

    ImageView imageViewQuestion;
    ImageView imageViewAnswer;
    TextView textViewScore;
    int totalScore = 100;
    public static ArrayList<String> arrayListNameOfBird;

    String pictureNameQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewQuestion = (ImageView) findViewById(R.id.imageView_question);
        imageViewAnswer = (ImageView) findViewById(R.id.imageView_answer);
        textViewScore = (TextView) findViewById(R.id.textView_score);

        textViewScore.setText(String.valueOf(totalScore));

        String[] stringsNameOfBird = getResources().getStringArray(R.array.birdList);
        arrayListNameOfBird = new ArrayList<>(Arrays.asList(stringsNameOfBird));

        refreshArrayList();

        // sự kiện click chọn đáp án ở imageViewAnswer
        imageViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ListAnswerActivity.class), MainActivity.REQUEST_CODE_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // kiểm tra các điều kiện của kết quả trả về có khớp hay không
        if (requestCode == MainActivity.REQUEST_CODE_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
            String pictureNameResult = data.getStringExtra(ListAnswerActivity.KEY_PICTURE);
            int pictureIdResult = getResources().getIdentifier(pictureNameResult, "drawable", getPackageName());

            imageViewAnswer.setImageResource(pictureIdResult);
            
            // kiểm tra câu trả lời
            if (pictureNameResult.equals(pictureNameQuestion)) {
                Toast.makeText(MainActivity.this, "Chính Xác \nBạn được cộng 10 điểm", Toast.LENGTH_SHORT).show();
                totalScore += 10;
                // đổi hình câu hỏi
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        refreshArrayList();
                    }
                }.start();
            } else {
                Toast.makeText(MainActivity.this, "Sai Rồi \nBạn bị trừ 5 điểm", Toast.LENGTH_SHORT).show();
                totalScore -= 5;
            }
            textViewScore.setText(String.valueOf(totalScore));
        }

        // nếu user nhấn vào nút BACK hệ thống mà không chọn hình bất kỳ
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Bạn muốn xem lại à\nBạn bị trừ 20 điểm", Toast.LENGTH_SHORT).show();
            totalScore -= 20;
            textViewScore.setText(String.valueOf(totalScore));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_reload) {
            refreshArrayList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshArrayList() {
        // trộn mảng với Collections
        Collections.shuffle(arrayListNameOfBird);

        // lấy ID của Resource có tên chỉ định trong loại "drawable" (bắt buộc chính xác), ở Package chỉ định
        int pictureOfBirdID = getResources().getIdentifier(MainActivity.arrayListNameOfBird.get(7), "drawable", getPackageName());
        imageViewQuestion.setImageResource(pictureOfBirdID);
        pictureNameQuestion = MainActivity.arrayListNameOfBird.get(7);
    }
}