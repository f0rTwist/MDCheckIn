package com.twist.navtest2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import androidx.appcompat.app.AlertDialog;

public class MapActivity extends Activity implements BaiduMap.OnMarkerClickListener {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private Marker marker;
    private Button sign;
    LatLng point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //显示图层
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        final LatLng cenpt = new LatLng(29.806651,121.606983);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(50)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        setMarkPoint(cenpt.latitude, cenpt.longitude);

//        mBaiduMap.setOnMarkerClickListener(this);

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        sign=findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlterDialog(cenpt.latitude, cenpt.longitude);
            }
        });
//        BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                Log.e(TAG, "onMapClick latitude: " + latLng.latitude);//经度
//                Log.e(TAG, "onMapClick longitude: " + latLng.longitude);//纬度
//                setMarkPoint(latLng.latitude, latLng.longitude);
//                showAlterDialog(latLng.latitude,latLng.longitude);
//            }
//
//            @Override
//            public void onMapPoiClick(MapPoi mapPoi) {
//
//            }
//
//        };//点击获取经纬度；
//        mBaiduMap.setOnMapClickListener(listener);
        mBaiduMap.setMyLocationEnabled(true);
    }
    private void setMarkPoint(double jingdu,double weidu) {

        //定义Maker坐标点
        mBaiduMap.clear();
        point = new LatLng(jingdu, weidu);
//定义Maker坐标点

//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_clear);

//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point) //必传参数
                .icon(bitmap) //必传参数
                .alpha(0.5f);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }
    /**
     * 普通dialog
     */
    private void showAlterDialog(Double jingdu, Double weidu){
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(MapActivity.this);
        alterDiaglog.setIcon(R.mipmap.icon_gcoding);//图标
        alterDiaglog.setTitle("签到位置");//文字
        alterDiaglog.setMessage("经度："+jingdu+"\n"+"纬度："+weidu);//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapActivity.this,"签到成功", Toast.LENGTH_SHORT).show();
                String name=getIntent().getStringExtra("chatname");
                Intent intent=new Intent();
                intent.putExtra("chatname",name);
                intent.setClass(MapActivity.this,Chat.class);
                finish();
                dialog.dismiss();
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //显示
        alterDiaglog.show();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        showAlterDialog(marker.getPosition().latitude, marker.getPosition().longitude);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }


}
