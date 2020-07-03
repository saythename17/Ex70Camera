package com.icandothisallday2020.ex70camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.iv);
    }

    public void clickFAB(View view) {
        //카메라 앱을 실행하는 인텐트
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 20:
                //카메라앱에서 캡쳐한 결과를 가져왔는지 확인
                if(resultCode==RESULT_OK){
                    // 프로그래밍(인텐트)으로 실행한 카메라앱은 디바이스 마다 사진캡쳐후 저장방식이 다름
                    //(ver.M~마시멜로우 이후 대부분) 찍은 사진을 디바이스에 저장하지 않고 사진의 Bitmap 객체를 만들어 리턴
                    //∴ 사진의 경로인 Uri 가 없음(하드디스크에 저장되지않고 RAM 에만 존재하기 때문)

                    //인텐트 객체에게 Uri 가져왔는지 확인
                    Uri uri=data.getData();
                    if(uri!=null){//사진이 파일로 저장되는 방식의 디바이스
                        Toast.makeText(this, "URI로 저장", Toast.LENGTH_SHORT).show();
                        Glide.with(this).load(uri).into(iv);
                    }
                    else{//Bitmap 으로 사진의 경로가 전달된 디바이스
                        Toast.makeText(this, "저장 없이 Bitmap", Toast.LENGTH_SHORT).show();
                        //Bitmap 객체를 Intent 의 Extra data 로 전달
                        Bundle bundle=data.getExtras();
                        Bitmap bm=(Bitmap) bundle.get("data");//data : 비트맵이미지를 가져오기로 약속된 key 값
                        Glide.with(this).load(bm).into(iv);
                        //비트맵으로 저장된 데이터기 때문에 해상도가 떨어짐.
                        //Bitmap -> 캡쳐한 사진이 디바이스에 저장되어 있지 않을 때 저장하는 방법(다음 예제)
                    }

                }
                break;
        }
    }
}
