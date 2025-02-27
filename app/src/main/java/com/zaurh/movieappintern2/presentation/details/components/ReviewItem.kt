package com.zaurh.movieappintern2.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaurh.movieappintern2.R
import com.zaurh.movieappintern2.data.models.firebase.reviews.Rate
import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData
import com.zaurh.movieappintern2.shared.LikeDislikeThumbs
import com.zaurh.movieappintern2.util.formatTimestamp

@Composable
fun ReviewItem(
    reviewData: ReviewData
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(0.1f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(8f)
        ) {
            Text(
                text = reviewData.author?.username ?: "Unknown user",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            var opinionIsExpanded by remember { mutableStateOf(false) }
            Text(
                text = reviewData.opinion.ifEmpty { "No review" },
                color = if (reviewData.opinion.isEmpty()) MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.5f
                ) else MaterialTheme.colorScheme.primary,
                maxLines = if (opinionIsExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable {
                    opinionIsExpanded = !opinionIsExpanded
                }
            )
            Text(
                text = formatTimestamp(reviewData.timestamp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
        LikeDislikeThumbs(
            modifier = Modifier.weight(2f),
            reviewData = reviewData
        )
    }
}