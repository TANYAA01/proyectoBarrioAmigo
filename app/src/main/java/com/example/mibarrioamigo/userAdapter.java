package com.example.mibarrioamigo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.UserViewHolder> {

    private List<user> userList;
    private static Context context;
    private static OnItemClickListener onItemClickListener;

    public userAdapter(List<user> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        String getSelectedUserCedula(int position);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listusers, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.UserViewHolder holder, int position) {
        user user = userList.get(position);

        // Setear los datos del usuario en los elementos de la vista
        holder.txtNombres.setText(user.getNombres());
        holder.txtApellidos.setText(user.getApellidos());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombres, txtApellidos;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombres = itemView.findViewById(R.id.txtNombres);
            txtApellidos = itemView.findViewById(R.id.txtApellidos);
            // Agrega más inicializaciones según sea necesario para otros campos

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                            String selectedUserCedula = onItemClickListener.getSelectedUserCedula(position);

                            // Aquí puedes usar la cédula del usuario para cargar los detalles en la otra actividad
                            Intent intent = new Intent(context, cruduser.class);
                            intent.putExtra("selectedUserCedula", selectedUserCedula);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
