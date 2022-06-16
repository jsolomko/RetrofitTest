package com.example.mytestretrofit.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.utils.AppUtilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
                .withHasStableIds(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Логин").withIcon(R.drawable.ic_baseline_login_24).withIdentifier(1).withSelectable(false),
                        new SecondaryDrawerItem().withName("Платные услуги").withIcon(R.drawable.ic_baseline_payments_24).withIdentifier(4).withSelectable(false),
                        new SecondaryDrawerItem().withName("О приложении").withIcon(R.drawable.ic_baseline_info_24).withIdentifier(5).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Получить пациентов").withIcon(R.drawable.ic_baseline_person_24).withIdentifier(2).withSelectable(false),
                        new SecondaryDrawerItem().withName("Получить обращения").withIcon(R.drawable.ic_baseline_featured_play_list_24).withIdentifier(3).withSelectable(false),
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                Intent i = new Intent(BaseActivity.this, TreatmentActivity.class);
                                startActivity(i);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://гб4сочи.рф/%D0%BF%D0%BB%D0%B0%D1%82%D0%BD%D1%8B%D0%B5-%D1%83%D1%81%D0%BB%D1%83%D0%B3%D0%B8/%D0%BE%D1%84%D1%82%D0%B0%D0%BB%D1%8C%D0%BC%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D1%8F"));
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 2) {
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
