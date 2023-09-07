package com.waseemshahzad.alpha4;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.net.URLEncoder;

public class CustomDialogCenter extends Dialog {
    public Activity c;
    public Dialog d;
    public Button btn_close;
    String html=
            "<HTML>"+
                    "<head>"+
                    "</head>"+
                    "<body>"+
                    "<Style='color=#F44336'>This  Email is sender from Khandani shifakhana App user.</style>\n" +
                    "Contact : <Style='color=#2196F3'>+923328695758</style>\n"+
                    "Address : <Style='color=#2196F3'>Defence Shopping mal DHA phase 1 lahore pakistan </style>\n"+
                    "</body"+
                    "</html>";

    public CustomDialogCenter(Activity a) {
        super(a);
        this.c = a;

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contactus_dialoge);
        btn_close = (Button) findViewById(R.id.btn_close);
        ImageView iv_facebook = (ImageView) findViewById(R.id.iv_facebook);
        ImageView iv_call = (ImageView) findViewById(R.id.iv_call);
        ImageView iv_google = (ImageView) findViewById(R.id.iv_google);
        ImageView iv_youtube = (ImageView) findViewById(R.id.iv_youtube);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String defultapp = "WhatsApp";

                try {
                    String url = "https://api.whatsapp.com/send?phone=+923477442050" +
                            "&text=" + URLEncoder.encode("HELLO KHANDANI SHAFAKHANA TEAM !  ", "UTF-8");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_VIEW);

                    if (defultapp.equalsIgnoreCase("WhatsApp")) {
                        sendIntent.setPackage("com.whatsapp");
                        Log.e("message", "WhatsApp");
                    }
                    sendIntent.setData(Uri.parse(url));
                    c.startActivity(sendIntent);

                } catch (Exception e) {
                    Log.e("message", e.getMessage());
                }
            }
        });
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"pksofter@gmail.com"));
//                intent.putExtra(Intent.EXTRA_SUBJECT, "KSK User");
                intent.putExtra(Intent.EXTRA_SUBJECT, "KSK USER");
                intent.putExtra(Intent.EXTRA_EMAIL, "\nThis email from KSK USER");

                c.startActivity(intent);





            }
        });
        iv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                String url="https://www.youtube.com/channel/UCTV_XVFjZAX2xg9OwQzf_nA";
                try {
                    intent =new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(url));
                    c.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    c.startActivity(intent);
                }
            }
        });
        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String YourPageURL = "https://www.facebook.com/n/?966338360104991";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

                c.startActivity(browserIntent);
            }
        });


    }

}
