package servier;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.end.R;

public class Mymuservier extends Service {
    private int type;
    private MediaPlayer player;
    private final IBinder iBinder = new MyBinder();

    public  class MyBinder extends Binder {
        Mymuservier getService(){
            return Mymuservier.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        type=intent.getIntExtra("type",0);
        if(type==1){
            player = MediaPlayer.create(this, R.raw.xiaoxi);
            player.setLooping(false);
            try{
                if(player !=null){
                    player.start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            player = MediaPlayer.create(this, R.raw.haoyou);
            player.setLooping(false);
            try{
                if(player !=null){
                    player.start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
