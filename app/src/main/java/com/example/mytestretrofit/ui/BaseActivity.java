package com.example.mytestretrofit.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mytestretrofit.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class BaseActivity extends AppCompatActivity {
    private Context context;
    private Activity activity;

    private Drawer drawer = null;
    private AccountHeader header = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = BaseActivity.this;
        context = activity.getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void initDrawer() {

        final IProfile profile = new ProfileDrawerItem();
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .addProfiles(profile)
                .build();


        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Логин").withIcon(R.drawable.ic_launcher_background).withIdentifier(10).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Получить пациентов").withIcon(R.drawable.ic_launcher_background).withIdentifier(20).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Получить обращения").withIcon(R.drawable.ic_launcher_background).withIdentifier(32).withSelectable(false),
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 10) {
                                Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                            if (drawerItem.getIdentifier() == 32) {
                                Intent i = new Intent(BaseActivity.this, TreatmentActivity.class);
                                startActivity(i);
                            }
                            if (drawerItem.getIdentifier() == 33) {


                            } else if (drawerItem.getIdentifier() == 20) {
                                Intent i = new Intent(BaseActivity.this, MainActivity.class);
                                startActivity(i);
                            } else if (drawerItem.getIdentifier() == 21) {
                            } else if (drawerItem.getIdentifier() == 22) {
                            } else if (drawerItem.getIdentifier() == 23) {
                            } else if (drawerItem.getIdentifier() == 31) {
                            } else if (drawerItem.getIdentifier() == 32) {
                            } else if (drawerItem.getIdentifier() == 40) {

                            }
                        }

                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(false)
                .withShowDrawerUntilDraggedOpened(false)
                .build();

    }

}
