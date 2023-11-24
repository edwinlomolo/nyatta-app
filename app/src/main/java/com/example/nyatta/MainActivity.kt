package com.example.nyatta

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.nyatta.compose.components.AlertDialog
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AuthViewModel
import com.example.nyatta.viewmodels.NyattaViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationPriority: Int? = null
    private val authViewModel: AuthViewModel by viewModels { NyattaViewModelProvider.Factory }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            var hasLocationPermissions by remember { mutableStateOf(hasLocationPermissions())}
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            var currentPermissionStatus by remember {
                mutableStateOf(
                    decideLocationPermissionStatus(
                        hasLocationPermissions,
                        shouldShowPermissionRationale
                    )
                )
            }
            val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            val usePreciseLocation = locationPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)
            locationPriority = if (usePreciseLocation) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY
            val locationRequest = LocationRequest.Builder(locationPriority!!, TimeUnit.SECONDS.toMillis(3)).build()
            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }
            val locationPermissionLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    hasLocationPermissions = permissions.values.reduce { acc, isPermissionGranted ->
                        acc && isPermissionGranted
                    }
                    if (hasLocationPermissions) {
                        fusedLocationClient?.getCurrentLocation(
                            locationPriority!!,
                            CancellationTokenSource().token
                        )
                            ?.addOnSuccessListener { location: Location ->
                                authViewModel
                                    .setDeviceLocation(
                                        LatLng(
                                            location.latitude,
                                            location.longitude
                                        )
                                    )
                            }
                    }

                    if (!hasLocationPermissions) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                    }

                    shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !hasLocationPermissions
                    currentPermissionStatus = decideLocationPermissionStatus(
                        hasLocationPermissions,
                        shouldShowPermissionRationale
                    )
                }
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(hasLocationPermissions, lifecycleOwner) {
                val locationCallback: LocationCallback = object: LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        for (location in p0.locations) {
                            authViewModel
                                .setDeviceLocation(LatLng(location.latitude, location.longitude))
                        }
                    }
                }
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !hasLocationPermissions &&
                        !shouldShowPermissionRationale) {
                        locationPermissionLauncher.launch(locationPermissions)
                    } else if (event == Lifecycle.Event.ON_START && hasLocationPermissions) {
                        fusedLocationClient
                            ?.requestLocationUpdates(
                                locationRequest, locationCallback, Looper.getMainLooper()
                            )
                    } else if (hasLocationPermissions && event == Lifecycle.Event.ON_STOP) {
                        fusedLocationClient?.removeLocationUpdates(locationCallback)
                    } else if (hasLocationPermissions && event == Lifecycle.Event.ON_PAUSE) {
                        fusedLocationClient
                            ?.removeLocationUpdates(locationCallback)
                    }
                }

                lifecycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            NyattaTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (shouldShowPermissionRationale) {
                            AlertDialog(
                                onDismissRequest = { return@AlertDialog },
                                onConfirmation = {
                                    shouldShowPermissionRationale = false
                                    locationPermissionLauncher.launch(locationPermissions)
                                },
                                dialogTitle = getString(R.string.location_required),
                                dialogText = getString(R.string.gps_permission_headline),
                                icon = Icons.TwoTone.LocationOn,
                                confirmationText = "Approve"
                            )
                        }
                        if (shouldDirectUserToApplicationSettings) {
                            openApplicationSettings()
                        }
                        NyattaApplication()
                    }
                }
            }
        }
    }

    private fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", "com.example.nyatta", null)).also {
            startActivity(it)
        }
    }
    private fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun decideLocationPermissionStatus(
        hasLocationPermission: Boolean,
        shouldShowPermissionRationale: Boolean
    ): String {
        return if (hasLocationPermission) "Granted"
        else if (shouldShowPermissionRationale) "Rejected"
        else "Denied"
    }
}

@Preview(showBackground = true)
@Composable
fun NyattaPreview() {
    NyattaTheme {
        NyattaApp()
    }
}