/*
 *
 *  * Copyright 2019 New Vector Ltd
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package im.vector.riotredesign.features.home.room.detail.timeline

import android.widget.ImageView
import android.widget.TextView
import im.vector.riotredesign.R
import im.vector.riotredesign.core.epoxy.KotlinModel

class ReadReceiptsItem() : KotlinModel(R.layout.item_timeline_read_receipts) {

    private val moreText by bind<TextView>(R.id.message_more_than_expected)
    private val avatarReceipt1 by bind<ImageView>(R.id.message_avatar_receipt_1)
    private val avatarReceipt2 by bind<ImageView>(R.id.message_avatar_receipt_1)
    private val avatarReceipt3 by bind<ImageView>(R.id.message_avatar_receipt_1)
    private val avatarReceipt4 by bind<ImageView>(R.id.message_avatar_receipt_1)
    private val avatarReceipt5 by bind<ImageView>(R.id.message_avatar_receipt_1)
    private val avatarReceipts = listOf(avatarReceipt1, avatarReceipt2, avatarReceipt3, avatarReceipt4, avatarReceipt5)

    override fun bind() {

    }
}