package hernandez.perez.uca.com.realmexample.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hernandez.perez.uca.com.realmexample.R;
import hernandez.perez.uca.com.realmexample.models.Animal;

/**
 * Created by LENOVO on 3/5/2018.
 */

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {
private List<Animal> mDataset;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView nombre;
    public TextView edad;
    public TextView raza;
    public TextView sexo;
    public ViewHolder(View view) {
        super(view);
        nombre = view.findViewById(R.id.nombre);
        raza = view.findViewById(R.id.raza);
        edad = view.findViewById(R.id.edad);
        sexo = view.findViewById(R.id.sexo);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnimalsAdapter(List<Animal> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnimalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Animal animal = mDataset.get(position);

        Log.i("nombre", animal.getNombre());
        Log.i("raza", animal.getRaza());
        Log.i("edad", String.valueOf(animal.getEdad()));
        Log.i("sexo", String.valueOf(animal.getSexo()));

        holder.nombre.setText(animal.getNombre());
        holder.raza.setText(animal.getRaza());
        holder.edad.setText(String.valueOf(animal.getEdad()));
        holder.sexo.setText(String.valueOf(animal.getSexo()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
