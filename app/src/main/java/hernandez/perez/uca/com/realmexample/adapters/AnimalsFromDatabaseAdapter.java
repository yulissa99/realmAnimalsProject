package hernandez.perez.uca.com.realmexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import hernandez.perez.uca.com.realmexample.R;
import hernandez.perez.uca.com.realmexample.models.Animal;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by LENOVO on 3/5/2018.
 */

public class AnimalsFromDatabaseAdapter extends RecyclerView.Adapter<AnimalsFromDatabaseAdapter.ViewHolder> {
    private RealmResults<Animal> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombre;
        public TextView raza;
        public TextView edad;
        public TextView sexo;
        public LinearLayout item;
        public Context context;
        public ViewHolder(View view, Context _context) {
            super(view);
            nombre = view.findViewById(R.id.nombre);
            raza = view.findViewById(R.id.raza);
            edad = view.findViewById(R.id.edad);
            sexo = view.findViewById(R.id.sexo);
            item = view.findViewById(R.id.item);
            context = _context;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnimalsFromDatabaseAdapter(RealmResults<Animal> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnimalsFromDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new AnimalsFromDatabaseAdapter.ViewHolder(view, parent.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AnimalsFromDatabaseAdapter.ViewHolder holder, final int position) {
        final Animal animal = mDataset.get(position);

        Log.i("nombre", animal.getNombre());
        Log.i("raza", animal.getRaza());
        Log.i("edad", String.valueOf(animal.getEdad()));
        Log.i("sexo", String.valueOf(animal.getSexo()));

        holder.nombre.setText(animal.getNombre());
        holder.raza.setText(animal.getRaza());
        holder.edad.setText(String.valueOf(animal.getEdad()));
        holder.sexo.setText(String.valueOf(animal.getSexo()));


        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialDialog.Builder(holder.context)
                        .content("Desea borrar este registro.")
                        .positiveText("Borrar")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                remoteItemFromDatabase(mDataset.get(position));
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void remoteItemFromDatabase(Animal animal) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        animal.deleteFromRealm();
        realm.commitTransaction();
    }
}
