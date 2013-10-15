package com.supdo.lockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;

public class MainActivity extends Activity {

	private DevicePolicyManager devicePolicyManager = null;
	private static final int REQUEST_CODE_ADD_DEVICE_ADMIN = 10001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		if (devicePolicyManager.isAdminActive(SDDar.getCn(this))) {
			devicePolicyManager.lockNow();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid()); 
		} else {
			startAddDeviceAdminAty();
		}
	}

	private void startAddDeviceAdminAty() {
		Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, SDDar.getCn(this));
		i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "注册此组件后才能拥有锁屏功能");

		startActivityForResult(i, REQUEST_CODE_ADD_DEVICE_ADMIN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			devicePolicyManager.lockNow();
			finish();
		} else {
			startAddDeviceAdminAty();
		}
		android.os.Process.killProcess(android.os.Process.myPid()); 
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
