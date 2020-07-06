package com.twist.navtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.twist.navtest2.Bean.SocketCommand;
import com.twist.navtest2.Bean.UserLogin;
import com.twist.navtest2.Util.encrypt;
import com.twist.navtest2.ui.login.SocketTestActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences courseSettings;
    final int BUF_SIZE = 8 * 1024;

    String uid,upwd;
    char[] readBuffer  = new char[BUF_SIZE];
    OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .followSslRedirects(false)
            .build();

    String sessionid ,randNumber,password,username,cookie,realXH;
    EditText et_u,et_p,code;
    boolean isLogon = false;
    Button btn,btn2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        courseSettings = getSharedPreferences("course_setting", Context.MODE_PRIVATE);

        iv = findViewById(R.id.iv);
        code = findViewById(R.id.et_login_code);
        et_u = findViewById(R.id.et_login_username);
        et_p = findViewById(R.id.et_login_pwd);
        btn = findViewById(R.id.bt_login_submit);
        btn2 = findViewById(R.id.bt_login_register);

        //userCourseDao = new UserCourseDao(getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uid = et_u.getText().toString();
                upwd = et_p.getText().toString();
                new Thread(onLogin).start();
                Intent data = new Intent();
                data.putExtra("cookie", cookie);
                data.putExtra("session","123");
                data.putExtra("sid",1);
                data.putExtra("name","测试用户");
                data.putExtra("major","河南大学");
                setResult(1, data);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TCPClient();
                Intent intent = new Intent(getApplicationContext(), SocketTestActivity.class);
                startActivityForResult(intent, 1);
                //new Thread(runnable).start();
                //Toast.makeText(getApplicationContext(),"btn2！",Toast.LENGTH_SHORT).show();
            }
        });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCode();
            }
        });
        initCode();
    }

    Runnable onLogin = new Runnable(){
        @Override
        public void run(){
            //UserLogin();
            Message msg = Message.obtain();
            Bundle data = new Bundle();
            data.putString("value", "存放数据");
            msg.setData(data);
            handler2.sendMessage(msg);
        }
    };

    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //Toast.makeText(getApplicationContext(),val,Toast.LENGTH_SHORT).show();
            //将数据进行显示到界面等操作
        }
    };

    protected void UserLogin() {
        try {
            //创建客户端Socket，指定服务器的IP地址和端口
            //Looper.prepare();
            //Toast.makeText(getApplicationContext(),"socket create.",Toast.LENGTH_SHORT).show();
            //Looper.loop();
            Log.d("socket test", "TCPClient");
            Gson gson = new Gson();
            SocketCommand socketCommand = new SocketCommand();
            Socket socket = new Socket("101.200.199.81",12458);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            socketCommand.setCommand("hello");
            socketCommand.setData("server");
            String str = gson.toJson(socketCommand);
            writer.write(str);
            writer.flush();

            reader.read(readBuffer, 0, BUF_SIZE);
            String  message = new String(readBuffer);
            message = message.substring(0,message.lastIndexOf('}')+1);
            Log.d("received json", message);
            socketCommand = gson.fromJson(message,SocketCommand.class);


            UserLogin userLogin = new UserLogin();
            userLogin.setUsername(uid);
            String pwdMD5 = encrypt.md5(upwd);
            Log.d("pwd encrypt", upwd+pwdMD5);
            userLogin.setPassword(pwdMD5);
            String str2 = gson.toJson(userLogin);
            socketCommand.setCommand("login");
            socketCommand.setData(str2);
            str = gson.toJson(socketCommand);
            Log.d("login json", str);
            writer.write(str);
            writer.flush();

            reader.read(readBuffer, 0, BUF_SIZE);
            message = new String(readBuffer);
            message = message.substring(0,message.lastIndexOf('}')+1);
            Log.d("received json", message);
            socketCommand = gson.fromJson(message,SocketCommand.class);

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void TCPClient() {
        try {
            //创建客户端Socket，指定服务器的IP地址和端口
            //Looper.prepare();
            //Toast.makeText(getApplicationContext(),"socket create.",Toast.LENGTH_SHORT).show();
            //Looper.loop();
            Log.d("socket test", "TCPClient");
            Gson gson = new Gson();
            SocketCommand socketCommand = new SocketCommand();
            Socket socket = new Socket("101.200.199.81",12458);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            socketCommand.setCommand("hello");
            socketCommand.setData("server");
            String str = gson.toJson(socketCommand);
            writer.write(str);
            writer.flush();


            reader.read(readBuffer, 0, BUF_SIZE);
            String  message = new String(readBuffer);
            message = message.substring(0,message.lastIndexOf('}')+1);
            Log.d("recvjson", message);
            socketCommand = gson.fromJson(message,SocketCommand.class);

            Looper.prepare();
            Toast.makeText(getApplicationContext(),"command:"+socketCommand.getCommand(),Toast.LENGTH_SHORT).show();

            socketCommand.setCommand("end.");
            socketCommand.setData("server");
            writer.write(gson.toJson(socketCommand));
            writer.flush();

            reader.read(readBuffer, 0, BUF_SIZE);
            String  message2 = new String(readBuffer);
            message2 = message2.substring(0,message2.lastIndexOf('}')+1);
            Log.d("recvjson", message);
            socketCommand = gson.fromJson(message2,SocketCommand.class);
            Toast.makeText(getApplicationContext(),"command:"+socketCommand.getCommand(),Toast.LENGTH_SHORT).show();

            socketCommand.setCommand("exit");
            socketCommand.setData("server");
            writer.write(gson.toJson(socketCommand));
            writer.flush();

            reader.read(readBuffer, 0, BUF_SIZE);
            String  message3 = new String(readBuffer);
            message3 = message3.substring(0,message3.lastIndexOf('}')+1);
            Log.d("recvjson", message);
            socketCommand = gson.fromJson(message3,SocketCommand.class);
            Toast.makeText(getApplicationContext(),"command:"+socketCommand.getCommand(),Toast.LENGTH_SHORT).show();
            Looper.loop();

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    String s = (String) msg.obj;
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    //Toast.makeText(getApplicationContext(),"验证码",Toast.LENGTH_SHORT).show();
                    byte[] Picture = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    iv.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    public void initCode() {

        String murl = null;
        try {
            murl = "http://xk.henu.edu.cn/cas/genValidateCode?dateTime=" + URLEncoder.encode(new Date().toString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e){
            Log.i("codefail","0");
        }
        Request request = new Request.Builder().url(murl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                showToast("验证码加载失败");
                Message message = mHandler.obtainMessage();
                message.obj = "验证码加载失败";
                message.what = 1;
                mHandler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] byte_image =  response.body().bytes();
                Message message = handler.obtainMessage();
                message.obj = byte_image;
                message.what = 1;
                handler.sendMessage(message);
                Headers headers = response.headers();
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                cookie = session;
                String sessionID = session.substring(11, session.indexOf(";"));
                sessionid = sessionID;
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
    }
}
