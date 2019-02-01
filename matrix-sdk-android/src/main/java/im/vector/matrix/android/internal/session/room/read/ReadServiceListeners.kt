/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.matrix.android.internal.session.room.read

import androidx.lifecycle.Observer
import com.zhuinden.monarchy.Monarchy
import im.vector.matrix.android.api.session.room.read.ReadService
import im.vector.matrix.android.internal.database.model.ReadReceiptEntity
import im.vector.matrix.android.internal.database.query.where

class ReadServiceListeners(private val roomId: String,
                           private val monarchy: Monarchy
) {

    private val listeners = ArrayList<ReadService.Listener>()
    private val readReceiptsLive by lazy {
        monarchy.findAllManagedWithChanges { realm -> ReadReceiptEntity.where(realm, roomId) }
    }
    private val readReceiptLiveObserver = Observer<Monarchy.ManagedChangeSet<ReadReceiptEntity>> { listeners.forEach { it.onReadReceiptsUpdated() } }

    fun addListener(listener: ReadService.Listener) {
        if (listeners.isEmpty()) {
            startObservingData()
        }
        listeners.add(listener)
    }

    fun removeListener(listener: ReadService.Listener) {
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            stopObservingData()
        }
    }

    private fun startObservingData() {
        readReceiptsLive.observeForever(readReceiptLiveObserver)
    }

    private fun stopObservingData() {
        readReceiptsLive.removeObserver(readReceiptLiveObserver)
    }


}