package hernandez.perez.uca.com.realmexample;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tumblr.remember.Remember;

import java.util.List;
import hernandez.perez.uca.com.realmexample.adapters.AnimalsAdapter;
import hernandez.perez.uca.com.realmexample.adapters.AnimalsFromDatabaseAdapter;
import hernandez.perez.uca.com.realmexample.api.Api;
import hernandez.perez.uca.com.realmexample.models.Animal;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String IS_FIRST_TIME = "is_first_time";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Only call fetchProducts on first time
        if (!isFirstTime()) {
            fetchAnimals();
            storeFirstTime();
        } else {
            getFromDataBase();
        }

    }

    private void storeFirstTime() {
        Remember.putBoolean(IS_FIRST_TIME, true);
    }

    private boolean isFirstTime() {
        return Remember.getBoolean(IS_FIRST_TIME, false);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAnimals();
            }
        });


    }

    private void fetchAnimals() {
        Call<List<Animal>> call = Api.instance().getAnimals();
        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                //mAdapter = new ProductsAdapter(response.body());
                //mRecyclerView.setAdapter(mAdapter);

                sync(response.body());
                getFromDataBase();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void sync(List<Animal> animals) {
        for(Animal animal : animals) {
            store(animal);
        }
    }

    private void store(Animal animalFromApi) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Animal animal = realm.createObject(Animal.class); // Create a new object

        animal.setNombre(animalFromApi.getNombre());
        animal.setRaza(animalFromApi.getRaza());
        animal.setEdad(animalFromApi.getEdad());
        animal.setSexo(animalFromApi.getSexo());

        realm.commitTransaction();
    }

    private void getFromDataBase() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Animal> query = realm.where(Animal.class);

        RealmResults<Animal> results = query.findAll();

        mAdapter = new AnimalsFromDatabaseAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
    }

}
