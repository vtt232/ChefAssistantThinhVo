package com.HaiHa.ChefAssistant;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unity3d.player.R;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    private ArrayList<Food> foods ;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.rview);
        foods = new ArrayList<>();
        for (int i =0; i <20; i++)
        {
            Helper.GetRandomFood(getApplicationContext(), new Helper.VolleyCallBack()
            {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText( getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Food food) {
                    foods.add(food);
                    foodAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.action_bar);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Food name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String newText) {
        // creating a new array list to filter our data.
        ArrayList<Food> foods = new ArrayList<>();

        // running a for loop to compare elements.

        Helper.GetFood(getApplicationContext(), newText, new Helper.VolleyCallBack()
        {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText( getApplicationContext(),
                        "No Data Found..",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Food food) {
                foods.add(food);
                foodAdapter.notifyDataSetChanged();
            }
        });

    }

}
