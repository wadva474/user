package com.wadud.user.presentation.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wadud.user.domain.model.User

@Composable
fun UserItemListing(user: User) {
    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                Text(text = user.fullName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = user.email, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = user.address, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Divider()
    }

}