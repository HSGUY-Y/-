package util;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.entity.UserEntity;
import com.example.end.R;
import com.example.end.Set;
import com.example.end.UserSet;
import com.example.end.mainUI;

public class FragmentPerson extends Fragment {
    private UserEntity userEntity;

    public FragmentPerson(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    public FragmentPerson(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view,container,false);
        //使用getActivity获取上下文环境
        final mainUI activity =(mainUI) getActivity();
        //设置各个控件
        LinearLayout userset=view.findViewById(R.id.userset);
        LinearLayout set=view.findViewById(R.id.set);
        ImageView headshot=view.findViewById(R.id.headshot);
        TextView nickname= view.findViewById(R.id.nickname);
        final TextView account=view.findViewById(R.id.account);
        TextView sign=view.findViewById(R.id.sign);
        account.setText(userEntity.getAccount());
        nickname.setText(userEntity.getNickname());
        sign.setText(userEntity.getSign());
        String[] name = {
               "icon", "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
        };
        int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
                R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
        if(userEntity.getHeadshot()!=null){
            for(int i =0;i<name.length;i++){
                if(userEntity.getHeadshot().equals(name[i]))
                    headshot.setImageResource(pic[i]);
            }
        }
        userset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UserSet.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",userEntity);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(activity,"打开用户设置界面",Toast.LENGTH_SHORT).show();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Set.class);
                intent.putExtra("account",userEntity.getAccount());
                startActivity(intent);
                Toast.makeText(activity,"打开设置界面",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
