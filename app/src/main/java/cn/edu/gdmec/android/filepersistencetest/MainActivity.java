package cn.edu.gdmec.android.filepersistencetest;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件存储、java流读写操作
 * 学习利用Context类中提供的openFileInput()和openFileOutput()方法
 */
public class MainActivity extends AppCompatActivity {
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载布局
        edit= (EditText) findViewById(R.id.edit);
        String inputText=load();
        if (!TextUtils.isEmpty(inputText)){
            edit.setText(inputText);
            edit.setSelection(inputText.length());
            Toast.makeText(this,"这是您上次的输入！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String load(){
        FileInputStream input = null;
        BufferedReader reader = null;
        StringBuilder content=new StringBuilder();
        try {
            input=openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(input));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return content.toString();
    }

}
