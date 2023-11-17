package ma.ofppt.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import ma.ofppt.musicplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ArrayList<Track> tracks = new ArrayList<>();
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getTrack();
        binding.trackList.setText("" + tracks.size());
        String out="";
        for(Track track : tracks){
            out += track.Title +"\n";
        }
        binding.trackList.setText(out);
    }

    void getTrack(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = contentResolver.query(
                uri,
                null,
                selection,
                null,
                sortOrder
        );
        if(cursor != null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                Track track = new Track();
                track.setTitle(title);
                track.setPath(path);
               // track.setDuration(Integer.parseInt(duration));
                tracks.add(track);
            }
            cursor.close();
        }


    }
}