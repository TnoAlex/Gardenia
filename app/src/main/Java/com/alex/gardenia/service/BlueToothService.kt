package com.alex.gardenia.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.alex.gardenia.event.BusEventTarget
import com.alex.gardenia.event.BusEventWrapper
import com.elvishew.xlog.XLog
import com.polidea.rxandroidble3.RxBleClient
import com.polidea.rxandroidble3.RxBleConnection
import com.polidea.rxandroidble3.scan.ScanResult
import com.polidea.rxandroidble3.scan.ScanSettings
import com.polidea.rxandroidble3.scan.ScanSettings.CALLBACK_TYPE_FIRST_MATCH
import com.polidea.rxandroidble3.scan.ScanSettings.SCAN_MODE_BALANCED
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.UUID


class BlueToothService : Service() {

    private var rxBleClient: RxBleClient? = null
    private var scanDisposable: Disposable? = null
    private var disposable = CompositeDisposable()
    private var rxBleConnection: RxBleConnection? = null

    override fun onCreate() {
        super.onCreate()
        rxBleClient = RxBleClient.create(this)
        EventBus.getDefault().register(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    private fun scan() {
        val scanSettings = ScanSettings.Builder().setScanMode(SCAN_MODE_BALANCED)
            .setCallbackType(CALLBACK_TYPE_FIRST_MATCH).build()
        scanDisposable = rxBleClient!!.scanBleDevices(scanSettings).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.bleDevice.name != null && it.bleDevice.name!!.contains("TBike") }.take(1)
            .subscribe({ connection ->
                XLog.i("Try to connect ${connection.bleDevice.name}")
                connect(connection)
            }, { throwable ->
                XLog.e("BlueTooth Scan Error", throwable)
                throw throwable
            }, {
                XLog.i("BlueTooth Scan Complete")
            })

    }

    private fun connect(scanResult: ScanResult) {
        scanDisposable?.dispose()
        disposable.add(
            scanResult.bleDevice.establishConnection(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ rxBleConnection ->
                    this.rxBleConnection = rxBleConnection
                    XLog.i("BlueTooth connected")
                    setupNotification()
                }, { throwable ->
                    XLog.e("Can not connected device", throwable)
                    throw throwable
                })
        )
    }

    private fun setupNotification() {
        disposable.add(
            rxBleConnection!!.setupNotification(L2CAP_UUID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        disposable.add(it.subscribe { data -> postEvent(data) })
                    }, {
                        XLog.e("Receive data error", it)
                    })
        )
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    private fun send(data: BusEventWrapper) {
        val rawData = data.data(BusEventTarget.BLUETOOTH_SEND)
        rawData?.let {
            rxBleConnection!!.writeCharacteristic(
                L2CAP_UUID,
                (it as String).toByteArray()
            ).subscribe({ XLog.i("BlueTooth send success") },
                { e -> XLog.e("BlueTooth send Fail", e) })
        }
    }

    private fun postEvent(data: ByteArray) {
        EventBus.getDefault().post(BusEventWrapper(BusEventTarget.BLUETOOTH_RECEIVE, data))
    }

    override fun onDestroy() {
        scanDisposable?.dispose()
        disposable.dispose()
        scanDisposable = null
        EventBus.getDefault().unregister(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private val L2CAP_UUID = UUID.fromString("0000FFFF-0000-1000-8000-00805F9B34FB")
    }


}