package com.cml.second.app.baby.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cml.second.app.baby.R;
import com.cml.second.app.baby.fragment.MainFragment;
import com.cml.second.app.baby.fragment.PictureFragment;
import com.cml.second.app.baby.helper.menu.MenuHelper;
import com.cml.second.app.baby.utils.DialogUtils;
import com.cml.second.app.common.widget.menu.NavigationMenuView;
import com.socks.library.KLog;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MenuActivity extends BaseActivity {

    private static final int REQUEST_IMAGE = 2001;

    private DrawerLayout drawer;
    private NavigationMenuView menuView;
    private FloatingActionButton floatingActionButton;
    private MenuHelper menuHelper;
    private LinearLayout toolbarCustomLayout;//自定义toolbar背景界面
    public UMShareAPI umShareAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbarCustomLayout = (LinearLayout) findViewById(R.id.toolbar_custom_container);

        //设置titlebar
        initToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setCustomTitle(getTitle());

//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("am title");
//        collapsingToolbarLayout.setTitleEnabled(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtils.showPicker(MenuActivity.this, getString(R.string.title_picker), R.array.items_picker, new PickerClickListener());

                //选择图片
//                Crop.pickImage(MenuActivity.this);

//                AlertDialog dialog = new AlertDialog.Builder(MenuActivity.this).setSingleChoiceItems(new CharSequence[]{"相机", "食品"}, -1, null).setTitle("title").create();
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.show();

//                Bitmap[] bitmaps = new Bitmap[5];
//                bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//                bitmaps[1] = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_camera);
//                bitmaps[2] = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
//                bitmaps[3] = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_btn_speak_now);
//                bitmaps[4] = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_dialer);
//                try {
////                    String url1 = "http://www.bz55.com/uploads/allimg/150309/139-150309101A0.jpg";
////                    String url2 = "http://www.bz55.com/uploads/allimg/150309/139-150309101F2.jpg";
////                    bitmaps[0] = BitmapFactory.decodeStream(new FileInputStream(url1));
////                    bitmaps[1] = BitmapFactory.decodeStream(new FileInputStream(url2));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        menuHelper = new MenuHelper(this, drawer);

        menuView = (NavigationMenuView) findViewById(R.id.nav_view);

        List<NavigationMenuView.MenuItem> menus = new ArrayList<>();
        menus.add(new NavigationMenuView.MenuItem(R.string.title_main, android.R.drawable.ic_menu_camera));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_babies, android.R.drawable.ic_menu_gallery));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_indexable, android.R.drawable.ic_menu_call));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_recycler, android.R.drawable.ic_menu_gallery));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_main, android.R.drawable.ic_menu_camera));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_babies, android.R.drawable.ic_menu_gallery));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_main, android.R.drawable.ic_menu_camera));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_babies, android.R.drawable.ic_menu_gallery));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_main, android.R.drawable.ic_menu_camera));
        menus.add(new NavigationMenuView.MenuItem(R.string.title_babies, android.R.drawable.ic_menu_gallery));
        menuView.setMenus(menus);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_menu, null);
        menuView.setHeader(headerView);
        menuView.setMenuSelectedLisener(menuHelper);

//        TextView navigationView = (TextView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(menuHelper);

        //将默认的menu栏迁移到自定义的custom title容器上，以解决title居中问题
        setupCustomNavigation();

        menuHelper.showContainer(MainFragment.class);

        umShareAPI = UMShareAPI.get(this);
    }

    class PickerClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            switch (which) {
                case DialogUtils.INDEX_CAMERA:
                    ContainerActivity.startActivity(MenuActivity.this, PictureFragment.class, PictureFragment.getStartBundle(PictureFragment.TYPE_FROM_CAMERA, null));
                    break;
                case DialogUtils.INDEX_LOCAL_PICTURE:
                    Intent intent = new Intent(MenuActivity.this, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//                    intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
                    startActivityForResult(intent, REQUEST_IMAGE);
                    break;
                case DialogUtils.INDEX_VIDEO:
                    break;
                case DialogUtils.INDEX_LOCAL_VIDEO:
                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        item.setChecked(false);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Toast.makeText(this, "选择图片返回" + path, Toast.LENGTH_LONG).show();
                KLog.d(TAG, "照片选择返回", path);
            }
        } else {
            umShareAPI.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void hideFloatingBtn() {
        floatingActionButton.hide();
    }

    public void setFloatingBtnListener(View.OnClickListener listener) {
        floatingActionButton.setOnClickListener(listener);
    }

    public LinearLayout getToolbarCustomLayout() {
        return toolbarCustomLayout;
    }
}
