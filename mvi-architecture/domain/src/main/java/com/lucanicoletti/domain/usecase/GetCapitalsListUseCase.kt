package com.lucanicoletti.domain.usecase

import com.lucanicoletti.domain.model.Capital
import com.lucanicoletti.domain.model.LatLng
import javax.inject.Inject

class GetCapitalsListUseCase @Inject constructor() {

    private val capitalsWithLatLong: Map<String, LatLng> = mapOf(
        "Hargeisa" to LatLng(9.55f, 44.05f),
        "King Edward Point" to LatLng(-54.28f, -36.50f),
        "Port-aux-Français" to LatLng(-49.35f, 70.21f),
        "Jerusalem" to LatLng(31.76f, 35.23f),
        "Mariehamn" to LatLng(60.11f, 19.90f),
        "Yaren" to LatLng(-0.54f, 166.92f),
        "Marigot" to LatLng(18.07f, -63.08f),
        "Atafu" to LatLng(-9.16f, -171.83f),
        "El-Aaiún" to LatLng(27.15f, -13.20f),
        "Kabul" to LatLng(34.51f, 69.18f),
        "Tirana" to LatLng(41.31f, 19.81f),
        "Algiers" to LatLng(36.75f, 3.05f),
        "Pago Pago" to LatLng(-14.26f, -170.70f),
        "Andorra la Vella" to LatLng(42.5f, 1.51f),
        "Luanda" to LatLng(-8.83f, 13.21f),
        "The Valley" to LatLng(18.21f, -63.05f),
        "Saint John's" to LatLng(17.11f, -61.85f),
        "Buenos Aires" to LatLng(-34.58f, -58.66f),
        "Yerevan" to LatLng(40.16f, 44.50f),
        "Oranjestad" to LatLng(12.51f, -70.03f),
        "Canberra" to LatLng(-35.26f, 149.13f),
        "Vienna" to LatLng(48.2f, 16.36f),
        "Baku" to LatLng(40.38f, 49.86f),
        "Nassau" to LatLng(25.08f, -77.35f),
        "Manama" to LatLng(26.23f, 50.56f),
        "Dhaka" to LatLng(23.71f, 90.40f),
        "Bridgetown" to LatLng(13.1f, -59.61f),
        "Minsk" to LatLng(53.9f, 27.56f),
        "Brussels" to LatLng(50.83f, 4.33f),
        "Belmopan" to LatLng(17.25f, -88.76f),
        "Porto-Novo" to LatLng(6.48f, 2.61f),
        "Hamilton" to LatLng(32.28f, -64.78f),
        "Thimphu" to LatLng(27.46f, 89.63f),
        "La Paz" to LatLng(-16.5f, -68.15f),
        "Sarajevo" to LatLng(43.86f, 18.41f),
        "Gaborone" to LatLng(-24.63f, 25.90f),
        "Brasilia" to LatLng(-15.78f, -47.91f),
        "Road Town" to LatLng(18.41f, -64.61f),
        "Bandar Seri Begawan" to LatLng(4.88f, 114.93f),
        "Sofia" to LatLng(42.68f, 23.31f),
        "Ouagadougou" to LatLng(12.36f, -1.51f),
        "Rangoon" to LatLng(16.8f, 96.15f),
        "Bujumbura" to LatLng(-3.36f, 29.35f),
        "Phnom Penh" to LatLng(11.55f, 104.91f),
        "Yaounde" to LatLng(3.86f, 11.51f),
        "Ottawa" to LatLng(45.41f, -75.70f),
        "Praia" to LatLng(14.91f, -23.51f),
        "George Town" to LatLng(19.3f, -81.38f),
        "Bangui" to LatLng(4.36f, 18.58f),
        "N'Djamena" to LatLng(12.1f, 15.03f),
        "Santiago" to LatLng(-33.45f, -70.66f),
        "Beijing" to LatLng(39.91f, 116.38f),
        "The Settlement" to LatLng(-10.41f, 105.71f),
        "West Island" to LatLng(-12.16f, 96.83f),
        "Bogota" to LatLng(4.6f, -74.08f),
        "Moroni" to LatLng(-11.7f, 43.23f),
        "Kinshasa" to LatLng(-4.31f, 15.30f),
        "Brazzaville" to LatLng(-4.25f, 15.28f),
        "Avarua" to LatLng(-21.2f, -159.76f),
        "San Jose" to LatLng(9.93f, -84.08f),
        "Yamoussoukro" to LatLng(6.81f, -5.26f),
        "Zagreb" to LatLng(45.8f, 16.00f),
        "Havana" to LatLng(23.11f, -82.35f),
        "Willemstad" to LatLng(12.1f, -68.91f),
        "Nicosia" to LatLng(35.16f, 33.36f),
        "Prague" to LatLng(50.08f, 14.46f),
        "Copenhagen" to LatLng(55.66f, 12.58f),
        "Djibouti" to LatLng(11.58f, 43.15f),
        "Roseau" to LatLng(15.3f, -61.40f),
        "Santo Domingo" to LatLng(18.46f, -69.90f),
        "Quito" to LatLng(-0.21f, -78.50f),
        "Cairo" to LatLng(30.05f, 31.25f),
        "San Salvador" to LatLng(13.7f, -89.20f),
        "Malabo" to LatLng(3.75f, 8.78f),
        "Asmara" to LatLng(15.33f, 38.93f),
        "Tallinn" to LatLng(59.43f, 24.71f),
        "Addis Ababa" to LatLng(9.03f, 38.70f),
        "Stanley" to LatLng(-51.7f, -57.85f),
        "Torshavn" to LatLng(62f, -6.76f),
        "Suva" to LatLng(-18.13f, 178.41f),
        "Helsinki" to LatLng(60.16f, 24.93f),
        "Paris" to LatLng(48.86f, 2.33f),
        "Papeete" to LatLng(-17.53f, -149.56f),
        "Libreville" to LatLng(0.38f, 9.45f),
        "Banjul" to LatLng(13.45f, -16.56f),
        "Tbilisi" to LatLng(41.68f, 44.83f),
        "Berlin" to LatLng(52.51f, 13.40f),
        "Accra" to LatLng(5.55f, -0.21f),
        "Gibraltar" to LatLng(36.13f, -5.35f),
        "Athens" to LatLng(37.98f, 23.73f),
        "Nuuk" to LatLng(64.18f, -51.75f),
        "Saint George's" to LatLng(12.05f, -61.75f),
        "Hagatna" to LatLng(13.46f, 144.73f),
        "Guatemala City" to LatLng(14.61f, -90.51f),
        "Saint Peter Port" to LatLng(49.45f, -2.53f),
        "Conakry" to LatLng(9.5f, -13.70f),
        "Bissau" to LatLng(11.85f, -15.58f),
        "Georgetown" to LatLng(6.8f, -58.15f),
        "Port-au-Prince" to LatLng(18.53f, -72.33f),
        "Vatican City" to LatLng(41.9f, 12.45f),
        "Tegucigalpa" to LatLng(14.1f, -87.21f),
        "Budapest" to LatLng(47.5f, 19.08f),
        "Reykjavik" to LatLng(64.15f, -21.95f),
        "New Delhi" to LatLng(28.6f, 77.20f),
        "Jakarta" to LatLng(-6.16f, 106.81f),
        "Tehran" to LatLng(35.7f, 51.41f),
        "Baghdad" to LatLng(33.33f, 44.40f),
        "Dublin" to LatLng(53.31f, -6.23f),
        "Douglas" to LatLng(54.15f, -4.48f),
        "Jerusalem" to LatLng(31.76f, 35.23f),
        "Rome" to LatLng(41.9f, 12.48f),
        "Kingston" to LatLng(18f, -76.80f),
        "Tokyo" to LatLng(35.68f, 139.75f),
        "Saint Helier" to LatLng(49.18f, -2.10f),
        "Amman" to LatLng(31.95f, 35.93f),
        "Astana" to LatLng(51.16f, 71.41f),
        "Nairobi" to LatLng(-1.28f, 36.81f),
        "Tarawa" to LatLng(-0.88f, 169.53f),
        "Pyongyang" to LatLng(39.01f, 125.75f),
        "Seoul" to LatLng(37.55f, 126.98f),
        "Pristina" to LatLng(42.66f, 21.16f),
        "Kuwait City" to LatLng(29.36f, 47.96f),
        "Bishkek" to LatLng(42.86f, 74.60f),
        "Vientiane" to LatLng(17.96f, 102.60f),
        "Riga" to LatLng(56.95f, 24.10f),
        "Beirut" to LatLng(33.86f, 35.50f),
        "Maseru" to LatLng(-29.31f, 27.48f),
        "Monrovia" to LatLng(6.3f, -10.80f),
        "Tripoli" to LatLng(32.88f, 13.16f),
        "Vaduz" to LatLng(47.13f, 9.51f),
        "Vilnius" to LatLng(54.68f, 25.31f),
        "Luxembourg" to LatLng(49.6f, 6.11f),
        "Skopje" to LatLng(42f, 21.43f),
        "Antananarivo" to LatLng(-18.91f, 47.51f),
        "Lilongwe" to LatLng(-13.96f, 33.78f),
        "Kuala Lumpur" to LatLng(3.16f, 101.70f),
        "Male" to LatLng(4.16f, 73.50f),
        "Bamako" to LatLng(12.65f, -8.00f),
        "Valletta" to LatLng(35.88f, 14.50f),
        "Majuro" to LatLng(7.1f, 171.38f),
        "Nouakchott" to LatLng(18.06f, -15.96f),
        "Port Louis" to LatLng(-20.15f, 57.48f),
        "Mexico City" to LatLng(19.43f, -99.13f),
        "Palikir" to LatLng(6.91f, 158.15f),
        "Chisinau" to LatLng(47f, 28.85f),
        "Monaco" to LatLng(43.73f, 7.41f),
        "Ulaanbaatar" to LatLng(47.91f, 106.91f),
        "Podgorica" to LatLng(42.43f, 19.26f),
        "Plymouth" to LatLng(16.7f, -62.21f),
        "Rabat" to LatLng(34.01f, -6.81f),
        "Maputo" to LatLng(-25.95f, 32.58f),
        "Windhoek" to LatLng(-22.56f, 17.08f),
        "Kathmandu" to LatLng(27.71f, 85.31f),
        "Amsterdam" to LatLng(52.35f, 4.91f),
        "Noumea" to LatLng(-22.26f, 166.45f),
        "Wellington" to LatLng(-41.3f, 174.78f),
        "Managua" to LatLng(12.13f, -86.25f),
        "Niamey" to LatLng(13.51f, 2.11f),
        "Abuja" to LatLng(9.08f, 7.53f),
        "Alofi" to LatLng(-19.01f, -169.91f),
        "Kingston" to LatLng(-29.05f, 167.96f),
        "Saipan" to LatLng(15.2f, 145.75f),
        "Oslo" to LatLng(59.91f, 10.75f),
        "Muscat" to LatLng(23.61f, 58.58f),
        "Islamabad" to LatLng(33.68f, 73.05f),
        "Melekeok" to LatLng(7.48f, 134.63f),
        "Panama City" to LatLng(8.96f, -79.53f),
        "Port Moresby" to LatLng(-9.45f, 147.18f),
        "Asuncion" to LatLng(-25.26f, -57.66f),
        "Lima" to LatLng(-12.05f, -77.05f),
        "Manila" to LatLng(14.6f, 120.96f),
        "Adamstown" to LatLng(-25.06f, -130.08f),
        "Warsaw" to LatLng(52.25f, 21.00f),
        "Lisbon" to LatLng(38.71f, -9.13f),
        "San Juan" to LatLng(18.46f, -66.11f),
        "Doha" to LatLng(25.28f, 51.53f),
        "Bucharest" to LatLng(44.43f, 26.10f),
        "Moscow" to LatLng(55.75f, 37.60f),
        "Kigali" to LatLng(-1.95f, 30.05f),
        "Gustavia" to LatLng(17.88f, -62.85f),
        "Jamestown" to LatLng(-15.93f, -5.71f),
        "Basseterre" to LatLng(17.3f, -62.71f),
        "Castries" to LatLng(14f, -61.00f),
        "Saint-Pierre" to LatLng(46.76f, -56.18f),
        "Kingstown" to LatLng(13.13f, -61.21f),
        "Apia" to LatLng(-13.81f, -171.76f),
        "San Marino" to LatLng(43.93f, 12.41f),
        "Sao Tome" to LatLng(0.33f, 6.73f),
        "Riyadh" to LatLng(24.65f, 46.70f),
        "Dakar" to LatLng(14.73f, -17.63f),
        "Belgrade" to LatLng(44.83f, 20.50f),
        "Victoria" to LatLng(-4.61f, 55.45f),
        "Freetown" to LatLng(8.48f, -13.23f),
        "Singapore" to LatLng(1.28f, 103.85f),
        "Philipsburg" to LatLng(18.01f, -63.03f),
        "Bratislava" to LatLng(48.15f, 17.11f),
        "Ljubljana" to LatLng(46.05f, 14.51f),
        "Honiara" to LatLng(-9.43f, 159.95f),
        "Mogadishu" to LatLng(2.06f, 45.33f),
        "Pretoria" to LatLng(-25.7f, 28.21f),
        "Juba" to LatLng(4.85f, 31.61f),
        "Madrid" to LatLng(40.4f, -3.68f),
        "Colombo" to LatLng(6.91f, 79.83f),
        "Khartoum" to LatLng(15.6f, 32.53f),
        "Paramaribo" to LatLng(5.83f, -55.16f),
        "Longyearbyen" to LatLng(78.21f, 15.63f),
        "Mbabane" to LatLng(-26.31f, 31.13f),
        "Stockholm" to LatLng(59.33f, 18.05f),
        "Bern" to LatLng(46.91f, 7.46f),
        "Damascus" to LatLng(33.5f, 36.30f),
        "Taipei" to LatLng(25.03f, 121.51f),
        "Dushanbe" to LatLng(38.55f, 68.76f),
        "Dar es Salaam" to LatLng(-6.8f, 39.28f),
        "Bangkok" to LatLng(13.75f, 100.51f),
        "Dili" to LatLng(-8.58f, 125.60f),
        "Lome" to LatLng(6.11f, 1.21f),
        "Nuku'alofa" to LatLng(-21.13f, -175.20f),
        "Port of Spain" to LatLng(10.65f, -61.51f),
        "Tunis" to LatLng(36.8f, 10.18f),
        "Ankara" to LatLng(39.93f, 32.86f),
        "Ashgabat" to LatLng(37.95f, 58.38f),
        "Grand Turk" to LatLng(21.46f, -71.13f),
        "Funafuti" to LatLng(-8.51f, 179.21f),
        "Kampala" to LatLng(0.31f, 32.55f),
        "Kyiv" to LatLng(50.43f, 30.51f),
        "Abu Dhabi" to LatLng(24.46f, 54.36f),
        "London" to LatLng(51.5f, -0.08f),
        "Montevideo" to LatLng(-34.85f, -56.16f),
        "Tashkent" to LatLng(41.31f, 69.25f),
        "Port-Vila" to LatLng(-17.73f, 168.31f),
        "Caracas" to LatLng(10.48f, -66.86f),
        "Hanoi" to LatLng(21.03f, 105.85f),
        "Charlotte Amalie" to LatLng(18.35f, -64.93f),
        "Mata-Utu" to LatLng(-13.95f, -171.93f),
        "Sanaa" to LatLng(15.35f, 44.20f),
        "Lusaka" to LatLng(-15.41f, 28.28f),
        "Harare" to LatLng(-17.81f, 31.03f),
        "North Nicosia" to LatLng(35.18f, 33.36f),
        "Diego Garcia" to LatLng(-7.3f, 72.40f),
    )

    operator fun invoke() = capitalsWithLatLong.map { pair ->
        Capital(
            name = pair.key,
            latLng = pair.value
        )
    }.sortedBy { it.name }

}