package pugman.com.simplelocationgetter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by pugman on 16.06.17.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */


public class SimpleLocationGetter{

	private static final String TAG = "SimpleLocationGetter";

	private final Activity                    mActivity;
	private       FusedLocationProviderClient mFusedLocationClient;
	private       OnLocationGetListener       mListener;
	private       Dialog                      mErrorDialog;

	public interface OnLocationGetListener{
		void onLocationReady(Location location);

		void onError(String error);
	}

	public SimpleLocationGetter(@NonNull Activity activity, @NonNull OnLocationGetListener listener){
		this.mActivity = activity;
		this.mListener = listener;
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
	}

	/**
	 * Start getting your location.
	 * WARNING Don't forget to check on ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION before calling this function
	 */
	@SuppressWarnings("MissingPermission")
	public void getLastLocation(){
		if(checkLocationPermissions()) {
			if(checkPlayServices()) {
				if(mListener != null) {
					mFusedLocationClient.getLastLocation().addOnCompleteListener(mActivity, new OnCompleteListener<Location>(){
						@Override
						public void onComplete(@NonNull Task<Location> task){
							if(task.isSuccessful() && task.getResult() != null) {
								Location lastLocation = task.getResult();
								mListener.onLocationReady(lastLocation);
							} else{
								mListener.onError("No location exists. Check if GPS enabled");
							}
						}
					});
				} else
					Log.w(TAG, "Location listener not attached");
			} else
				Log.w(TAG, "Google play services not installed or not enabled. Or version older than needed");
		} else
			Log.e(TAG, "You should grant ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION first");
	}

	private boolean checkLocationPermissions(){
		boolean coarseEnabled = mActivity.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
		boolean fineEnabled = mActivity.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
		return coarseEnabled && fineEnabled;
	}

	private boolean checkPlayServices(){
		GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(mActivity);
		if(resultCode != ConnectionResult.SUCCESS) {
			if(googleApiAvailability.isUserResolvableError(resultCode)) {
				if(mErrorDialog == null) {
					mErrorDialog = googleApiAvailability.getErrorDialog(mActivity, resultCode, 2404);
					mErrorDialog.setCancelable(false);
				}
				if(!mErrorDialog.isShowing())
					mErrorDialog.show();
			}
		}
		return resultCode == ConnectionResult.SUCCESS;
	}
}
