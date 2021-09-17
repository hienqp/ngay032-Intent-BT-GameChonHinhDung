package com.hienqp.gamechonhinhdung;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Collections;

public class ListAnswerActivity extends Activity {
    public static final String KEY_PICTURE = "KEY_PICTURE";

    TableLayout tableLayoutListAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_answer);

        tableLayoutListAnswer = (TableLayout) findViewById(R.id.tableLayout_list_answer);

        int row = 5;
        int column = 4;
        int position = -1;

        Collections.shuffle(MainActivity.arrayListNameOfBird);

        // tạo dòng và cột
        for (int i = 1; i <= row; i++) {
            TableRow tableRow = new TableRow(ListAnswerActivity.this);

            // tạo cột
            for (int j = 1; j <= column; j++) {
                ImageView imageView = new ImageView(ListAnswerActivity.this);

                // thiết lập Layout cho ImageView
                // Converts 14 dip into its equivalent px
                float dip = 75f;
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px, px);
                imageView.setLayoutParams(layoutParams);

                // lấy hình ảnh và gán vào ImageView
                position++;
                if (MainActivity.arrayListNameOfBird.get(position) != null) {
                    int pictureOfBirdID = getResources().getIdentifier(MainActivity.arrayListNameOfBird.get(position), "drawable", getPackageName());
                    imageView.setImageResource(pictureOfBirdID);
                }

                // gắn imageView vào tableRow
                tableRow.addView(imageView);

                // gắn sự kiện click cho imageView
                final int finalPosition = position; // vị trí cố định của imageView chỉ định
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(ListAnswerActivity.this, MainActivity.arrayListNameOfBird.get(finalPosition), Toast.LENGTH_SHORT).show();
                        Intent data = new Intent();
                        data.putExtra(ListAnswerActivity.KEY_PICTURE, MainActivity.arrayListNameOfBird.get(finalPosition));
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    }
                });
            }

            // add tableRow vào tableLayoutListAnswer
            tableLayoutListAnswer.addView(tableRow);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}