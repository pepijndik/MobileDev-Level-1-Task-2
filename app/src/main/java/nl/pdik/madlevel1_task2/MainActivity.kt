package nl.pdik.madlevel1_task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.pdik.madlevel1_task2.model.Place
import nl.pdik.madlevel1_task2.ui.theme.MADLevel1Task2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MADLevel1Task2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PlacesSearchScreen()
                }
            }
        }
    }
}

/**
 * Give back an arraylist with places. For now hardcoded. Later in the course we
 * are going to work with dynamic data.
 */
private fun generatePlaces(): List<Place> {
    return listOf(
        Place("Amsterdam", "Dam", R.drawable.amsterdam_dam),
        Place("Amsterdam", "Weesperplein", R.drawable.amsterdam_weesperplein),
        Place("Rotterdam", "Euromast", R.drawable.rotterdam_euromast),
        Place("Den Haag", "Binnenhof", R.drawable.den_haag_binnenhof),
        Place("Utrecht", "Dom", R.drawable.utrecht_dom),
        Place("Groningen", "Martinitoren", R.drawable.groningen_martinitoren),
        Place("Maastricht", "Vrijthof", R.drawable.maastricht_vrijthof),
        Place("New York", "Vrijheidsbeeld", R.drawable.new_york_vrijheidsbeeld),
        Place("San Francisco", "Golden Gate", R.drawable.san_francisco_golden_gate),
        Place("Yellowstone", "Old Faithful", R.drawable.yellowstone_old_faithful),
        Place("Yosemite", "Half Dome", R.drawable.yosemite_half_dome),
        Place("Washington", "White House", R.drawable.washington_white_house),
        Place("Ottawa", "Parliament Hill", R.drawable.ottawa_parliament_hill),
        Place("Londen", "Tower Bridge", R.drawable.london_tower_bridge),
        Place("Brussel", "Manneken Pis", R.drawable.brussel_manneken_pis),
        Place("Berlijn", "Reichstag", R.drawable.berlijn_reichstag),
        Place("Parijs", "Eiffeltoren", R.drawable.parijs_eiffeltoren),
        Place("Barcelona", "Sagrada Familia", R.drawable.barcelona_sagrada_familia),
        Place("Rome", "Colosseum", R.drawable.rome_colosseum),
        Place("Napels", "Pompeii", R.drawable.pompeii),
        Place("Kopenhagen", "", R.drawable.kopenhagen),
        Place("Oslo", "", R.drawable.oslo),
        Place("Stockholm", "", R.drawable.stockholm),
        Place("Helsinki", "", R.drawable.helsinki),
        Place("Moskou", "Rode Plein", R.drawable.moskou_rode_plein),
        Place("Beijing", "Verboden Stad", R.drawable.beijing_verboden_stad),
        Place("Kaapstad", "Tafelberg", R.drawable.kaapstad_tafelberg),
        Place("Rio de Janeiro", "Copacabana", R.drawable.rio_de_janeiro_copacabana),
        Place("Sydney", "Opera", R.drawable.sydney_opera),
        Place("Hawaii", "Honolulu", R.drawable.hawaii),
        Place("Alaska", "Denali", R.drawable.alaska_denali)
    )
}

@Composable
fun PlacesSearchScreen() {
    //we use rememberSaveable here to let the state survive process death, remember++ so to speak
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Scaffold(
        topBar = {
            TextField(
                value = searchQueryState.value,
                onValueChange = { value ->
                    searchQueryState.value = value
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_text),
                        color = Color.White
                    )
                },
                singleLine = true,
                shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        },
        content = { padding -> Places(Modifier.padding(padding), searchQueryState) }
    )

}

@Composable
fun Places(modifier: Modifier, searchQueryState: MutableState<TextFieldValue>) {

    val filterdPlaces = generatePlaces().filter {
        place -> place.place.contains(searchQueryState.value.text, true) || place.city.contains(searchQueryState.value.text,true);
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(8.dp)
        ) {
        items(items = filterdPlaces, itemContent = { place ->
            Row(Modifier.padding(8.dp)) {
                ItemLayout(place)
            }
        })
    }
}
@Composable
fun ItemLayout(place: Place){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
    ) {

            Image(
                painter = painterResource(id = place.image),
                contentDescription = "Place",
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentScale = ContentScale.Crop
            )
        Text(
            text = place.city,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = place.place,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MADLevel1Task2Theme {
        PlacesSearchScreen()
    }
}