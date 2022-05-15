package com.vangertorn.imagesapp.data.network.state

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import kotlinx.coroutines.channels.awaitClose

class NetworkStateImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkState {

    private val networkRequest =
        NetworkRequest.Builder().addCapability(NET_CAPABILITY_INTERNET).build()

    override val connectedFlow: Flow<Boolean>
        get() = callbackFlow {
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    trySend(isConnected())
                }

                override fun onLost(network: Network) {
                    trySend(isConnected())
                }
            }
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
        }

    override fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val netInfo = connectivityManager.getNetworkCapabilities(activeNetwork)
        return netInfo?.hasCapability(NET_CAPABILITY_INTERNET) ?: false
    }
}