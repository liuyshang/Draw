package com.me.cdj.draw;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * author: Lance
 * time: 2015/12/31 16:00
 * e-mail: lance.cao@anarry.com
 */
public class TestDialog extends BlurDialog{

    private Context mContext;
    private Button btn;

    public TestDialog(Context context) {
        super(context);
        mContext = context;
    }

    public TestDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreateDialog() {
        setRlContent(R.layout.dialog_test);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"高斯模糊测试",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
