package com.droider.checksn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText edt_userName;
    private EditText edt_sn;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_userName = (EditText)findViewById(R.id.edit_username);
        edt_sn = (EditText)findViewById(R.id.edit_sn);
        btn_register = (Button)findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = edt_userName.getText().toString().trim();
                String strPassword = edt_sn.getText().toString().trim();
                if (strUserName.length() == 0 || strPassword.length() == 0) {
                    Toast.makeText(MainActivity.this,
                            "请输入用户名与注册码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String realSN = calcSN(strUserName);
                if (strPassword.equalsIgnoreCase(realSN)) {
                    Toast.makeText(MainActivity.this,
                            "注册码正确",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "注册码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private String calcSN(String userName) {
        if (userName == null || userName.length() == 0) {
            return null;
        }
        return md5(userName);
    }

    private String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b: hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
