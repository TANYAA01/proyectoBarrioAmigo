package com.example.mibarrioamigo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebaseMessaing extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // Muestra la notificación
            mostrarNotificacion(title, body);
        }

        // Aquí puedes manejar los mensajes de datos si es necesario
    }

    private void mostrarNotificacion(String title, String body) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Comprobar si el dispositivo tiene Android Oreo (8.0) o superior, ya que requiere canales de notificación.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.simbolo) // Reemplaza con tu icono de notificación
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }
}
