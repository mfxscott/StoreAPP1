package com.xianhao365.o2o.fragment.my.buyer.purchase;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.view.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xianhao365.o2o.utils.httpClient.AppClient.getSDPath;

/**
 * 供应商采购订单发货流程及相关参数填写
 */
public class CGOrderDeliveActivity extends BaseActivity {
    @BindView(R.id.cg_order_detail_take_btn)
    TextView cgOrderDetailTakeBtn;
    @BindView(R.id.delive_upload_img)
    ImageView deliveUploadImg;
    @BindView(R.id.cg_delive_gridv)
    MyGridView imgGirdv;
    @BindView(R.id.delive_pp_img_view)
    ImageView  ppImgV;
    @BindView(R.id.delive_pp_rely_view)
    RelativeLayout ppRely;
    private Handler hand;
    private Activity activity;
    private String purchaseCode, numberStr, skucode;
    private GetPicPopupWindow getpicpop;
    private List<File> imgList = new ArrayList<>();
    private ImageGridViewAdapter  imgGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgorder_delive);
        ButterKnife.bind(this);
        activity = this;
        purchaseCode = this.getIntent().getStringExtra("code");
        numberStr = this.getIntent().getStringExtra("num");
        skucode = this.getIntent().getStringExtra("skucode");
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("确认发货");
        //默认添加一个增加按钮图片
        imgList.add(new File("123456"));
        deliveUploadImg.setVisibility(View.GONE);
        imgGridAdapter = new ImageGridViewAdapter(imgList);
        imgGirdv.setAdapter(imgGridAdapter);
        imgGirdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(imgList.size()-1 == position){
                    getpicpop = new GetPicPopupWindow(activity, ShareOnclick, false);
                    //显示窗口
                    getpicpop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    Bitmap img = BitmapFactory.decodeFile(imgList.get(position).getPath(),options);
                    ppRely.setVisibility(View.VISIBLE);
                    ppImgV.setImageBitmap(img);
//                    imgList.remove(position);
//                    imgGridAdapter.notifyDataSetChanged();
                }
            }
        });
        ppImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppRely.setVisibility(View.GONE);
            }
        });
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        Logs.i(imgList.size()+"");
                        imgGridAdapter.notifyDataSetChanged();
                        break;
                    case 1001:
                        break;
                    case AppClient.ERRORCODE:
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                return true;
            }
        });
    }
    View.OnClickListener ShareOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (getpicpop != null)
                getpicpop.dismiss();
            switch (v.getId()) {
                case R.id.get_pic_photo:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, 100);
                    break;
                case R.id.get_pic_take:
//                    if(Utils.getInstance(activity).isCameraCanUse()){
                    Intent intent = null;
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra("return-data", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriTakePhoto());
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true);
                    startActivityForResult(intent, 101);
//                    }else{
//                        Utils.getInstance(activity).ToastCenter(activity,"相机权限已被禁用，请在三峡付应用管理中启用相机权限。");
//                    }
                    break;
            }
        }
    };
    private String imgStr;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        SXUtils.getInstance(activity).deleteDir(GETPICPATH);
        switch (requestCode) {
            //相册
            case 100:
                if (data == null) {
                    break;
                }
//                Bitmap bitmap12 = ImageUtils.setPic(Utils.getRealFilePath(activity,data.getData()));
//                imgStr =   getImageString(uploadImg,getRealFilePath(this,data.getData()),2);
                imgStr = getRealFilePath(this, data.getData());
                Logs.i("相册=======图片路径"+imgStr);
                if (TextUtils.isEmpty(imgStr)) {
                    return;
                }
                File file = new File( imgStr ) ;
                imgList.add(0,file);
                hand.sendEmptyMessage(1000);
                break;
            //拍照
            case 101:
//                Bitmap bitmap31 = ImageUtils.setPic(Utils.getInstance(activity).getPathTakePhoto());
                imgStr = getPathTakePhoto();
                Logs.i("拍照=======图片路径"+imgStr);
//                if(bitmap31 == null){
//                    return;
//                }
                if (TextUtils.isEmpty(imgStr)) {
                    return;
                }
                File files = new File(imgStr) ;
                imgList.add(0,files);
                hand.sendEmptyMessage(1000);
//                Bitmap bitmap3;
//                if(bitmap31.getWidth() < 240){
//                    bitmap3 =  ImageUtils.zoomImg(bitmap31,bitmap31.getWidth(),bitmap31.getHeight());
//                }else{
//                    bitmap3 =  ImageUtils.zoomImg(bitmap31,bitmap31.getWidth()/3,bitmap31.getHeight()/3);
//                }
//                new StorePhotos(bitmap3, Utils.getInstance(activity).savePathTakePhoto("sxf"));
//                Bitmap bitmap4 = ImageUtils.getSmallBitmap(Utils.getInstance(activity).savePathTakePhoto("sxf"));
//                imgStr = ImageUtils.bitmapToString(bitmap4);
//                Utils.getInstance(activity).deleteFile(Utils.getInstance(activity).getPathTakePhoto());
                break;
            case 102:

//                ImgStr =   ImageUtils.bitmapToString(Utils.getPathTakePhoto());
//                try {
//                    bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(Utils.getUriTakePhoto()));
//                    imgStr =   Utils.bitmapToBase64(bitmap);
//                    Utils.deletePicPath("temp2");
//                    // 取得裁剪后的图片
//                    hand.sendEmptyMessage(20);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
            //相册裁剪完成后返回
            case 103:
//                try {
//                    bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(Utils.getUriPhoto()));
//                    imgStr =   Utils.bitmapToBase64(bitmap);
//                    Utils.deletePicPath("temp1");
//                    hand.sendEmptyMessage(20);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

                break;
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void clipPhoto(Uri uri, int type) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        /**
         * 此处做一个判断
         * １，相机取到的照片，我们把它做放到了定义的目录下。就是file
         * ２，相册取到的照片，这里注意了，因为相册照片本身有一个位置，我们进行了裁剪后，要给一个裁剪后的位置，
         * 　　不然onActivityResult方法中，data一直是null
         */
        if (type == 102) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 102);
        } else {
//            File path = Environment.getExternalStorageDirectory();
//            File file = new File(path + "/temp1.png");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriPhoto());
            startActivityForResult(intent, 103);
        }
    }

    /**
     * 获取拍照上传图片地址
     *
     * @return
     */
    public Uri getUriTakePhoto() {
        File file = CreateText(GETPICPATH, "/temp2.jpg");
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    public Uri getUriPhoto() {
        File file = CreateText(GETPICPATH, "/temp1.jpg");
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    public String getPathTakePhoto() {
        File file = CreateText(GETPICPATH, "/temp2.jpg");
        return file.getPath();
    }

    public String GETPICPATH = getSDPath() + "/sx/cacheImg";//图片上传，保存图片地址

    /**
     * 首页缓存写入数据
     *
     * @param path     创建目录
     * @param fielName 文件名
     */
    public File CreateText(String path, String fielName) {
        File file = new File(path);
        File f;
        if (!file.exists()) {
            // 若不存在，创建目录
            file.mkdirs();
        }
        //创建文件
        f = new File(path + fielName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;

    }

    /**
     * @param imgFilePath
     * @param ishead      1 上传头像缩小尺寸
     * @return
     */
    public String getImageString(ImageView imgview, String imgFilePath, int ishead) {
        String imageString = null;
        Bitmap mBitmap = BitmapFactory.decodeFile(imgFilePath);
        if (mBitmap != null) {
            Matrix matrix = new Matrix();
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            float scaleWidth;
            float scaleHeight;
            if (ishead == 1) {
                scaleWidth = (float) 150 / mWidth;
                scaleHeight = (float) 150 / mHeight;
            } else {
                if (mHeight > mWidth) {
                    scaleWidth = (float) 300 / mWidth;
                    scaleHeight = (float) 500 / mHeight;
                } else {
                    scaleWidth = (float) 500 / mWidth;
                    scaleHeight = (float) 300 / mHeight;
                }
            }
            Logs.i("scale", scaleWidth + "++++++++++++" + scaleHeight);
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            if (imgview != null)
                imgview.setImageBitmap(newBitmap);
            byte[] bytes = out.toByteArray();
//            imageString = new String(bytes);
//            try {
//                imageString = new String(bytes, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            imageString =  bytes.toString();
            imageString = Base64.encodeToString(bytes, Base64.NO_WRAP);
        }
        return imageString;
    }

    /**
     * uri转 path
     *
     * @param context
     * @param uri
     * @return
     */
    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获取供应商采购列表
     */
    public void GetGYSBillListHttp() {
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDateSuffix = dateformat.format(date);
        HttpParams params = new HttpParams();
        params.put("purchaseCode", purchaseCode);
        params.put("vehicleNo", "粤B1246");
        params.put("driverName", "123qwe");
        params.put("driverPhone", "18682136973");
        params.put("sendTime", sDateSuffix);
        params.put("sendAddr", "深圳市");
        params.put("senderPhone", "18682136973");
        params.put("senderName", "12qwe");

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("skuCode", skucode);
            jsonObject.put("actualNumber", numberStr + "");
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("linestr", jsonArray.toString());
        List<File> files = new ArrayList<>();
        File file = new File(imgStr);
        files.add(file);
//        params.putFileParams("driverFiles", files);
        params.putFileParams("attachFiles", files);
        HttpUtils.getInstance(activity).requestUploadImgPost(false, AppClient.GYS_CPURCHASE_DELIVER, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Log.i("上传成功=========", jsonObject.toString());
            }
            @Override
            public void onResponseError(String strError) {
                Log.i("2222222=========", strError.toString());
            }
        });
    }
    @OnClick({R.id.delive_upload_img, R.id.cg_order_detail_take_btn,R.id.delive_pp_rely_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delive_upload_img:
                getpicpop = new GetPicPopupWindow(this, ShareOnclick, false);
                //显示窗口
                getpicpop.showAtLocation(deliveUploadImg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.cg_order_detail_take_btn:
                GetGYSBillListHttp();
                break;
            case R.id.delive_pp_rely_view:
                break;
        }
    }
    class ImageGridViewAdapter extends BaseAdapter {
        private final LayoutInflater mLayoutInflater;
        public ImageGridViewAdapter( List<File> result) {
            mLayoutInflater = LayoutInflater.from(activity);
        }
        public int getCount() {
            return imgList.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LifeViewHolder vh;
            if (convertView == null) {
                vh = new LifeViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.img_item_layout, null);
                vh.imgv = (ImageView) convertView.findViewById(R.id.item_img);
                convertView.setTag(vh);
            } else {
                vh = (LifeViewHolder) convertView.getTag();
            }
            vh.imgv.setBackgroundResource(R.color.transparent);
            if(imgList.size()-1 == position){
                Glide.with(activity).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.addphoto).into(vh.imgv);
            }else{
                Glide.with(activity).load(imgList.get(position)).asBitmap().into(vh.imgv);
            }

            return convertView;
        }
        class LifeViewHolder{
            ImageView imgv;
        }
    }
}