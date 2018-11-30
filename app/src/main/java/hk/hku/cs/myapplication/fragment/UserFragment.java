package hk.hku.cs.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.entity.MyUser;
import hk.hku.cs.myapplication.ui.LoginActivity;
import hk.hku.cs.myapplication.utils.L;
import hk.hku.cs.myapplication.utils.SharUtils;
import hk.hku.cs.myapplication.view.CustomDialog;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.fragment
 *  文件名：    UserFragment
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 14:08
 *  描述：      TODO
 */public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

//    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    //更新按钮
    private Button btn_update_ok;

    //圆形头像
    private CircleImageView profile_image;

    private CustomDialog dialog;

    //物流查询
    private TextView tv_courier;
    //归属地查询
    private TextView tv_phone;


    //更改后的用户
    MyUser newUser = new MyUser();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View v) {
        btn_exit_user = v.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = v.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username = v.findViewById(R.id.et_username);
        et_sex = v.findViewById(R.id.et_sex);
        et_age = v.findViewById(R.id.et_age);
        et_desc = v.findViewById(R.id.et_desc);

        btn_update_ok = v.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = v.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

//        tv_courier = v.findViewById(R.id.tv_courier);
//        tv_courier.setOnClickListener(this);

//        tv_phone = v.findViewById(R.id.tv_phone);
//        tv_phone.setOnClickListener(this);

        //1.拿到String
        String imgString = SharUtils.getString(getActivity(),"image_title","");
        if (!imgString.equals("")){
            //2.利用Base64将String转换
            byte [] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            profile_image.setImageBitmap(bitmap);
        }

        //初始化dialog
        dialog = new CustomDialog(getActivity(),0,0,
                R.layout.dialog_photo,R.style.pop_anim_style, Gravity.BOTTOM,0);
        //屏幕外点击无效
        dialog.setCancelable(false);
//        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
//        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //默认不可输入
        setEnabled(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_age.setText(userInfo.getAge() + "");
        et_desc.setText(userInfo.getDes());
        et_sex.setText(userInfo.getSex() ? "男" : "女");
    }

    //控制焦点(可不可编辑)
    public void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出登陆
            case R.id.btn_exit_user:
                //清除缓存用户对象
                MyUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑信息
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String name = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                //2.判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &!TextUtils.isEmpty(sex)){
                    //3.更新属性
                    newUser.setUsername(name);
                    newUser.setAge(Integer.parseInt(age));
                    if(sex.equals("男")){
                        newUser.setSex(true);
                    }else {
                        newUser.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(desc)){
                        newUser.setDes(desc);
                    }else {
                        newUser.setDes("这个人很懒，什么都没有留下");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if( e == null){
                                //修改成功
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新用户成功！", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "更新用户失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
//            case R.id.btn_camera:
//                toCamera();
//                break;
            case R.id.btn_picture:
                toPicture();
                break;
//            case R.id.tv_courier:
//                startActivity(new Intent(getActivity(), CourierActivity.class));
//                break;
//            case R.id.tv_phone:
//                startActivity(new Intent(getActivity(), PhoneActivity.class));
//                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        //给上一个活动传数据
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }
    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED){
            switch (requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;

                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;

                case RESULT_REQUEST_CODE:
                    //可能拿到图片后舍弃,故判断非空后再操作
                    if(data != null){
                        //拿到选中的图片
                        setImageToView(data);
                        //既然已经拿到了图片，就删除原先的
                        if(tempFile != null){
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri){
        if(uri == null){
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置裁剪图片质量(分辨率)
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if(bundle != null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    //利用SharedPreference保存选择的图片，写在onDestry里面
    @Override
    public void onDestroy() {
        super.onDestroy();
        //拿到图片
        BitmapDrawable drawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步：利用Base64将字节数组输出流转换成Stream
        byte [] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //第三步：将String 保存在shareUtils中
        SharUtils.putString(getActivity(),"image_title",imgString);

    }
}
