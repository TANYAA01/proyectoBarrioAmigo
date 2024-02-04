package com.example.mibarrioamigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PublicacionesAdapter extends RecyclerView.Adapter<PublicacionesAdapter.ViewHolder> {
    private Context mContext;
    private List<Publicacion> mPublicacionesList;
    private TextView mTextViewUsuario;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Otros elementos de la vista, como el TextView de contenido
        public TextView textViewContenido;

        public ViewHolder(View view) {
            super(view);
            // Inicializa otros elementos de la vista
            textViewContenido = view.findViewById(R.id.textViewContenido);
            mTextViewUsuario = view.findViewById(R.id.textViewUsuario); // Configura el TextView de usuario
        }
    }


    public PublicacionesAdapter(Context context, List<Publicacion> publicacionesList) {
        mContext = context;
        mPublicacionesList = publicacionesList;
    }


    @NonNull
    @Override
    public PublicacionesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_publicacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionesAdapter.ViewHolder holder, int position) {
        Publicacion publicacion = mPublicacionesList.get(position);

        // Obtener el correo electrónico del usuario a partir del UID (esto depende de cómo lo almacenas en Firebase)
        String correoUsuario = obtenerCorreoUsuario(publicacion.getUsuarioId());

        // Establecer el correo electrónico del usuario en el TextView correspondiente
        mTextViewUsuario.setText(correoUsuario);

        // Establecer el contenido de la publicación en el TextView correspondiente
        holder.textViewContenido.setText(publicacion.getContenido());
    }

    @Override
    public int getItemCount() {
        return mPublicacionesList.size();

    }

    private String obtenerCorreoUsuario(String usuarioId) {
        // Lógica para obtener el correo electrónico del usuario a partir de su usuarioId en Firebase Realtime Database
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference().child("identificador").child(usuarioId);
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String correoUsuario = dataSnapshot.child("identificador").getValue(String.class);
                    if (correoUsuario != null) {
                        // Aquí puedes utilizar el correo electrónico obtenido
                        mTextViewUsuario.setText(correoUsuario);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos
            }
        });

        return null; // Este método debe ser asíncrono, por lo que no puedes devolver directamente el correo electrónico aquí
    }


}
