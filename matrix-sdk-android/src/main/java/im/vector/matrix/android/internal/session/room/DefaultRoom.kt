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

package im.vector.matrix.android.internal.session.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zhuinden.monarchy.Monarchy
import im.vector.matrix.android.api.session.room.Room
import im.vector.matrix.android.api.session.room.members.RoomMembersService
import im.vector.matrix.android.api.session.room.model.RoomSummary
import im.vector.matrix.android.api.session.room.read.ReadService
import im.vector.matrix.android.api.session.room.send.SendService
import im.vector.matrix.android.api.session.room.timeline.TimelineService
import im.vector.matrix.android.internal.database.mapper.asDomain
import im.vector.matrix.android.internal.database.model.RoomSummaryEntity
import im.vector.matrix.android.internal.database.model.RoomSummaryEntityFields
import im.vector.matrix.android.internal.database.query.where
import im.vector.matrix.android.internal.session.room.members.DefaultRoomMembersService
import im.vector.matrix.android.internal.session.room.read.DefaultReadService
import im.vector.matrix.android.internal.session.room.read.ReadServiceListeners
import im.vector.matrix.android.internal.session.room.send.DefaultSendService
import im.vector.matrix.android.internal.session.room.timeline.DefaultTimelineService

internal class DefaultRoom(
        override val roomId: String,
        private val monarchy: Monarchy,
        private val timelineService: DefaultTimelineService,
        private val sendService: DefaultSendService,
        private val readService: DefaultReadService,
        private val readServiceListeners: ReadServiceListeners,
        private val roomMembersService: DefaultRoomMembersService
) : Room,
    TimelineService by timelineService,
    SendService by sendService,
    ReadService by readService,
    RoomMembersService by roomMembersService {

    override val roomSummary: LiveData<RoomSummary> by lazy {
        val liveData = monarchy
                .findAllMappedWithChanges(
                        { realm -> RoomSummaryEntity.where(realm, roomId).isNotEmpty(RoomSummaryEntityFields.DISPLAY_NAME) },
                        { from -> from.asDomain() })

        Transformations.map(liveData) {
            it.first()
        }
    }

    override fun addListener(listener: Room.Listener) {
        readServiceListeners.addListener(listener)
    }

    override fun removeListener(listener: Room.Listener) {
        readServiceListeners.removeListener(listener)
    }

}