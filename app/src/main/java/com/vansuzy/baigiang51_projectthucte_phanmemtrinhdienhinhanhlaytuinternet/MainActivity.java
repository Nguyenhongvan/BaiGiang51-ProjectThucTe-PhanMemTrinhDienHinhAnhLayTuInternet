package com.vansuzy.baigiang51_projectthucte_phanmemtrinhdienhinhanhlaytuinternet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    CheckBox chkTuDong;
    ImageButton btnPrevious, btnNext;
    int currentPosition = -1;    // vị trí của hình ảnh chúng ta đang xem
    ArrayList<String> albums;

    Timer timer = null; // cho biết sau khoảng thời gian bao lâu thì phải làm TimerTask 1 lần
    TimerTask timerTask = null; // là 1 task định giờ chạy đa tiến trình

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXemHinhKeTiep();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXemHinhDangTruoc();
            }
        });
        chkTuDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    btnPrevious.setEnabled(false);
                    btnNext.setEnabled(false);

                    xuLyTuDongChayHinh();
                } else {
                    btnPrevious.setEnabled(true);
                    btnNext.setEnabled(true);
                    if (timer!=null)
                        timer.cancel();
                }
            }
        });
    }

    private void xuLyTuDongChayHinh() {
        // TimerTask cũng là 1 đa tiến trình nhưng đa tiến trình này có thể định giờ
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // runOnUiThread: cho phép chúng ta vẽ hình đó lên giao diện trong tiến trình
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentPosition++;
                        if (currentPosition==albums.size())
                            currentPosition = 0;
                        ImageTask task = new ImageTask();
                        task.execute(albums.get(currentPosition));
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,5000); // đối số 1 là TimerTask, đối số 2 là thời gian chờ để bắt đầu chạy tiến trình, đối số 3 là thời gian tự động lặp lại
    }

    private void xuLyXemHinhDangTruoc() {
        currentPosition--;
        // nếu đang ở hình đầu tiên (currentPosition = 0) mà chúng ta bấm xem hình đằng trước (currentPosition = -1) thì sẽ chuyển đến xem hình cuối cùng
        if (currentPosition==-1)
            currentPosition = albums.size()-1;
        ImageTask task = new ImageTask();
        task.execute(albums.get(currentPosition));
    }

    private void xuLyXemHinhKeTiep() {
        currentPosition++;
        if (currentPosition == albums.size())
            currentPosition = 0;
        ImageTask task = new ImageTask();
        task.execute(albums.get(currentPosition));
    }

    private void addControls() {
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        chkTuDong = (CheckBox) findViewById(R.id.chkTuDong);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnNext = (ImageButton) findViewById(R.id.btnNext);

        albums = new ArrayList<>();
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-0/p235x350/18952779_1336508633084920_168647802353392836_n.jpg?oh=823b4db7c407c8f0bb59a86abf956668&oe=59E0CF7B");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/18882017_1336304776438639_6381375268000369726_n.jpg?oh=44bce0b7dbd8c73e2e474062f6a24d9c&oe=599B8A86");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/19029477_1336304869771963_3035016029096659325_n.jpg?oh=7edb46a29aeca972e302b91c4c57a6fc&oe=59A16CCF");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/19029536_1336304876438629_8762683413158256680_n.jpg?oh=1500184bfbd92007119419c0a325753f&oe=59A5457F");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/19030283_1336304909771959_2112775470242171197_n.jpg?oh=810efd0d1be755bfa01ac31b2373a232&oe=59A20E02");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/19105493_1336304913105292_4228664044539725801_n.jpg?oh=8d9e3c7bc5d47e274bdf734050b35a2a&oe=59D771C5");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/18922111_1336305016438615_6433846034500703939_n.jpg?oh=bf3a5c1c8bbb756af95c3a04bd617ce6&oe=59D4737A");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/18952679_1336305059771944_7630018379272003645_n.jpg?oh=5131d75ed28b703231bbd45a6f67724e&oe=59D77858");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/18951058_1336305063105277_1630976523563229231_n.jpg?oh=18687aca70282cbe5c6a626b677d400d&oe=59DE5550");
        albums.add("https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/19029229_1336419576427159_6955411601773064160_n.jpg?oh=51ace0606deadeac9323d8a3080940e1&oe=59E9737F");
        currentPosition = 0;
        ImageTask task = new ImageTask();
        task.execute(albums.get(currentPosition));  // mặc định lấy hình đầu tiên
    }

    class ImageTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                String link = params[0];
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return bitmap;
            } catch (Exception ex) {
                Log.e("LOI", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgHinh.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
