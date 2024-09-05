package ifpe.mobile.lactgoGo.src.ui.theme.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Place(val name: String, var district: String, var workingHours: String)

@Composable
fun PlaceItem(
    place: Place,
    onPin: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Text(
                modifier = Modifier,
                text = place.name,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier,
                text = place.district,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier,
                text = place.workingHours,
                fontSize = 16.sp
            )

        }
        IconButton(onClick = onPin) {
            Icon(Icons.Filled.Place, contentDescription = "Close")
        }
    }
}

@Composable
fun ExplorePageComp(modifier: Modifier = Modifier) {
    var address by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        OutlinedTextField(
            value = address, onValueChange = { address = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Endereco") },
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        Spacer(modifier = modifier.size(50.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(30) { place ->
                PlaceItem(
                    place = Place(
                        name = "Estabelecimento ",
                        district = "Bairro",
                        workingHours = "9am - 5pm"
                    ),
                    onSelect = {
                        Toast.makeText(context, "Opening place", Toast.LENGTH_LONG).show()
                    },
                    onPin = {
                        Toast.makeText(context, "Place pinned", Toast.LENGTH_LONG).show()
                    },
                )
            }
        }
    }
}