package com.iiiinfotech.sharewishes;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import android.content.Intent;  
import android.net.Uri;  
import java.net.URLEncoder;
import java.util.ArrayList;


public class Sharewishes extends CordovaPlugin {
    public Context context;
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        //checkPermissions();
        if (action.equals("open")) {
            context=this.cordova.getActivity().getApplicationContext();
            String name = data.getString(0);
            String mobile = data.getString(1);
            String email = data.getString(2);
            String message = data.getString(3);
            if(name.equalsIgnoreCase("whatsapp")){
              openWhatsApp(mobile,message);
            }else if(name.equalsIgnoreCase("mail")){
              composeEmail(email,"Best Wishes",message);
            }else if(name.equalsIgnoreCase("sms")){
              sendSMS(mobile,message);
            }

            //callbackContext.success(message);

            return true;

        } else {

            return false;

        }
    }

  public void sendSMS(String smsNumber,String msg ){
    try {
		if(smsNumber.isEmpty()){
			Toast.makeText(context, "Please pass the Mobile number", Toast.LENGTH_SHORT).show();
			return;
		}
		if(smsNumber.equalsIgnoreCase("null")){
			Toast.makeText(context, "Please pass the Mobile number", Toast.LENGTH_SHORT).show();
			return;
		}
		if(msg.isEmpty()){
			Toast.makeText(context, "Please pass the Message", Toast.LENGTH_SHORT).show();
			return;
		}
	    Uri sms_uri = Uri.parse("smsto:"+smsNumber);
	    Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
      sms_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	    sms_intent.putExtra("sms_body", msg);
	    context.startActivity(sms_intent);
    }catch(Exception e){
      Toast.makeText(context, "Error/n" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }
  public void composeEmail(String addresses, String subject,String msg) {
    try {
		if(addresses.isEmpty()){
			Toast.makeText(context, "Please pass the Email ID", Toast.LENGTH_SHORT).show();
			return;
		}
		if(addresses.equalsIgnoreCase("null")){
			Toast.makeText(context, "Please pass the Email ID", Toast.LENGTH_SHORT).show();
			return;
		}
		if(msg.isEmpty()){
			Toast.makeText(context, "Please pass the Message", Toast.LENGTH_SHORT).show();
			return;
		}
      Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setType("text/plain");
      intent.setData(Uri.parse("mailto:"+addresses)); // only email apps should handle this
      intent.putExtra(Intent.EXTRA_EMAIL, addresses);
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
      intent.putExtra(Intent.EXTRA_TEXT, msg);
      if (intent.resolveActivity(cordova.getActivity().getPackageManager()) != null) {
        context.startActivity(intent);
      }
    }catch(Exception e){
      Toast.makeText(context, "Error/n" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

  }
  private void openWhatsApp(String smsNumber,String msg ) {
    try {
		if(smsNumber.isEmpty()){
			Toast.makeText(context, "Please pass the Mobile number", Toast.LENGTH_SHORT).show();
			return;
		}
		if(smsNumber.equalsIgnoreCase("null")){
			Toast.makeText(context, "Please pass the Mobile number", Toast.LENGTH_SHORT).show();
			return;
		}
		if(msg.isEmpty()){
			Toast.makeText(context, "Please pass the Message", Toast.LENGTH_SHORT).show();
			return;
		}
      Intent sendIntent = new Intent(Intent.ACTION_VIEW);
      sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
      sendIntent.setType("text/plain");
      String url = "https://api.whatsapp.com/send?phone="+ smsNumber +"&text=" + URLEncoder.encode(msg, "UTF-8");
      sendIntent.setPackage("com.whatsapp");
      sendIntent.setData(Uri.parse(url));
      if (sendIntent.resolveActivity(cordova.getActivity().getPackageManager()) == null) {
        Toast.makeText(context, "Error" , Toast.LENGTH_SHORT).show();
        return;
      }
      context.startActivity(sendIntent);
    }catch(Exception e){
      Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }
/*
    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> listPermissionsNeeded = new ArrayList<String>();

            if (context.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                listPermissionsNeeded
                        .add(android.Manifest.permission.SEND_SMS);
            }
            if (context.checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED) {
                listPermissionsNeeded
                        .add(android.Manifest.permission.READ_SMS);
            }

            if (context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                cordova.requestPermissions((CordovaPlugin) this,111,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]));;
            }

        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

*/



}
