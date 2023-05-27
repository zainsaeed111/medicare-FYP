package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.function.Function;

public class custom_bottom_menu<Unit> extends AppCompatActivity {

    private final int ID_home = 1;
    private final int ID_chat = 2;
    private final int ID_profile = 3;
    private final int ID_about = 4;

    TextView selected_page;
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_bottom_menu);
        getSupportActionBar().hide();
        bottomNavigation=findViewById(R.id.bottomNav);

     /*   bottomNavigation.add(new MeowBottomNavigation.Model(ID_home, R.drawable.custombottommenu_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_chat, R.drawable.custombottommenu_chat));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_about, R.drawable.custombottommenu_about));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_profile, R.drawable.custombottommenu_profile));*/
/*
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
*/
/*
            @SuppressLint("StringFormatInvalid")
*//*

            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                String name;
                Fragment fragment = null;
                switch (item.getId()){
                    case ID_home:
                        fragment=new home();
                        break;

                    case ID_chat:  new Inbox();
                        fragment=new Inbox();

                        break;
                    case ID_about: new about();
                        fragment=new about();

                        break;
                    case ID_profile: new profile();
                        fragment=new profile();
                        break;

                    default: new home();
                }

           replace(fragment);
            }


        });
*/

/*
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
*/

        bottomNavigation.show(ID_home,true);



    }
/*
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_Host,fragment,null);
        transaction.commit();
    }
*/
}






