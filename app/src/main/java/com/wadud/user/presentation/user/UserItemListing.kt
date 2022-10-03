package com.wadud.user.presentation.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wadud.user.R
import com.wadud.user.domain.model.User

@Composable
fun UserItemListing(user: User) {
    Column {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.profileImage,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Inside,
                contentDescription = stringResource(R.string.user_image),
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(percent = 10)).border(
                    2.dp, Color.Gray,
                    RoundedCornerShape(10)
                ).padding(8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
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