package hernandez.perez.uca.com.realmexample.api;

import java.util.List;

import hernandez.perez.uca.com.realmexample.models.Animal;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("Animales")
    Call<List<Animal>> getAnimals();

}
