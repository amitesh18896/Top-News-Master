package com.pallaw.topnews.util

/**
 * Created by Pallaw Pathak on 10/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED =
                NetworkState(
                    Status.SUCCESS,
                    "Success"
                )

            LOADING =
                NetworkState(
                    Status.RUNNING,
                    "Running"
                )

            ERROR =
                NetworkState(
                    Status.FAILED,
                    "Something went wrong"
                )

            ENDOFLIST =
                NetworkState(
                    Status.FAILED,
                    "You have reached the end"
                )
        }
    }
}