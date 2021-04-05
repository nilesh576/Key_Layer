package com.example.keylayer;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    ClipboardManager clipboard;
    EditText msgone_et,keyone_et,keytwo_et,hashtwo_et;
    TextView hashone_tv,msgtwo_tv;

    Button copy_msg1_btn,copy_key1_btn,copy_hash1_btn,
            paste_msg1_btn,paste_key1_btn,

            copy_msg2_btn,copy_key2_btn,copy_hash2_btn,
            paste_hash2_btn, paste_key2_btn;

    volatile String key_one, key_two,msg_one,hash_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        clipboard = (ClipboardManager) getSystemService(MainActivity.this.CLIPBOARD_SERVICE);

        msgone_et = findViewById(R.id.et_raw_message);
        keyone_et = findViewById(R.id.et_key_to_en);
        hashone_tv = findViewById(R.id.tv_created_hash);

        hashtwo_et = findViewById(R.id.et_hashed_message);
        keytwo_et = findViewById(R.id.et_key_to_de);
        msgtwo_tv = findViewById(R.id.tv_msg);

        copy_hash1_btn = findViewById(R.id.copy_hash1_btn);
        copy_msg1_btn = findViewById(R.id.copy_msg1_btn);
        copy_key1_btn = findViewById(R.id.copy_key1_btn);
        paste_msg1_btn = findViewById(R.id.paste_msg1_btn);
        paste_key1_btn = findViewById(R.id.paste_key1_btn);

        copy_hash2_btn = findViewById(R.id.copy_hash2_btn);
        copy_msg2_btn = findViewById(R.id.copy_msg2_btn);
        copy_key2_btn = findViewById(R.id.copy_key2_btn);
        paste_hash2_btn = findViewById(R.id.paste_hash2_btn);
        paste_key2_btn = findViewById(R.id.paste_key2_btn);
        //NJ4yEDbKsjP71G9hi+TQbg==

        copyFromET(msgone_et,copy_msg1_btn);
        copyFromET(keyone_et,copy_key1_btn);
        copyFromTV(hashone_tv,copy_hash1_btn);
        pasteToET(msgone_et,paste_msg1_btn);
        pasteToET(keyone_et,paste_key1_btn);
//
        copyFromET(hashtwo_et,copy_hash2_btn);
        copyFromET(keytwo_et,copy_key2_btn);
        copyFromTV(msgtwo_tv,copy_msg2_btn);
        pasteToET(hashtwo_et,paste_hash2_btn);
        pasteToET(keytwo_et,paste_key2_btn);

        msgtwo_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String urlString = msgtwo_tv.getText().toString();
                if(urlString.equals("")||urlString.equals("invalid\t")){
                    return false;
                }



                try {
                    java.net.URL url = new java.net.URL(urlString);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        MainActivity.this.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        intent.setPackage(null);
                        MainActivity.this.startActivity(intent);
                    }

                } catch (MalformedURLException e) {
                    urlString = "https://www.google.com/search?q="+(msgtwo_tv.getText().toString()).replaceAll(" ","+");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        MainActivity.this.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        intent.setPackage(null);
                        MainActivity.this.startActivity(intent);
                    }
                }
                return false;
            }
        });

        keyone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key_one = s.toString();
                try {
                    hashone_tv.setText(msgToHash(msg_one,key_one));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        });
        msgone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                msg_one = s.toString();
                try {
                    hashone_tv.setText(msgToHash(msg_one,key_one));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        });

        keytwo_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key_two = s.toString();
                try {
                    msgtwo_tv.setText(hashTomsg(hash_two,key_two));
                } catch (UnsupportedEncodingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                }
                catch (Exception e){
                    msgtwo_tv.setText("invalid\t");
//                    Toast.makeText(MainActivity.this, ""+e.toString()+"\n"+hash_two+" "+key_two, Toast.LENGTH_SHORT).show();
                }
            }
        });
        hashtwo_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hash_two = s.toString();
                try {
                    msgtwo_tv.setText(hashTomsg(hash_two,key_two));
                } catch (UnsupportedEncodingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    msgtwo_tv.setText("invalid\t");
                    e.printStackTrace();
                }
                catch (Exception e){
                    msgtwo_tv.setText("invalid\t");
//                    Toast.makeText(MainActivity.this, ""+e.toString()+"\n"+hash_two+" "+key_two, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String msgToHash(String msg, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if(key==null){
            key = "";
        }
        if(msg==null){
            msg = "";
        }
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] encVal = cipher.doFinal(msg.getBytes());
        String hash = Base64.encodeToString(encVal,Base64.DEFAULT);
        return hash;
    }
    String hashTomsg(String hash, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if(hash==null){
            hash = "";
        }
        if(key==null){
            key = "";
        }
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] decodedValue = Base64.decode(hash,Base64.DEFAULT);
        byte[] decValue = cipher.doFinal(decodedValue);
        String decrypted_msg = new String(decValue);
        return decrypted_msg;
    }

    SecretKeySpec generateKey(String passwaord) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = passwaord.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] key = messageDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return  secretKeySpec;
    }

    void copyFromET(EditText editText, Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("simple text", editText.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this,"cpoied",Toast.LENGTH_SHORT).show();

            }
        });
    }
    void pasteToET(EditText editText, Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(clipboard.getPrimaryClip().getItemAt(0).getText().toString());
                Toast.makeText(MainActivity.this,"pasted",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void copyFromTV(TextView textView, Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("simple text", textView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this,"copied",Toast.LENGTH_SHORT).show();
            }
        });
    }



}