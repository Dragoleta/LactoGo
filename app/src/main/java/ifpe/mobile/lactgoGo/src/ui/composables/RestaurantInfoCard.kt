package ifpe.mobile.lactgoGo.src.ui.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ifpe.mobile.lactgoGo.R
import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel

@Composable
fun RestaurantInfoCard(
    modifier :Modifier,
    restaurant: RestaurantModel
) {
    Card(
        modifier
            .background(color = Color.Transparent)
            .widthIn(min = 350.dp)
            .heightIn(min = 100.dp)
            .padding(8.dp)
    ) {
        Row {

            Box(
                modifier
                    .background(color = Color.Red)
                    .widthIn(min = 50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sr2adb75e98031c),
                    contentDescription = stringResource(id = R.string.project_id),
                    modifier = modifier.width(90.dp).height(90.dp)
                )
            }

            Column (
                modifier
                    .padding(top = 6.dp, start = 10.dp)
                    .align(Alignment.CenterVertically)
            ) {

                Row {
                    for (i in restaurant.tags) Text(i)
                }

                Text(restaurant.name,
                    fontSize = 30.sp,
                    )

                Row(modifier.padding(bottom = 8.dp, top = 8.dp, start = 10.dp,end = 50.dp).align(
                    Alignment.CenterHorizontally).fillMaxWidth()) {
                    Text("TODO KM")
                    HorizontalDivider(Modifier.width(10.dp))
                    Text("${restaurant.openingTime} - ${restaurant.closingTime}")
                    HorizontalDivider(Modifier.width(10.dp))
                    Text("${restaurant.rating}")

                }

            }
        }

    }
}
