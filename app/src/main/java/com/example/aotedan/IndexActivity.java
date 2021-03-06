package com.example.aotedan;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // 隐藏AppCompatActivity的默认标题栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_person, R.id.navigation_gas,R.id.navigation_center)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // 动态设置顶部标题栏的文字
        NavigationUI.setupWithNavController(navView, navController);
        initView();
    }
    private void initView(){
//        init();
    }

}
