# Kotlin Pokedex App
Pokedex App that uses information from a PokéAPI v2: https://pokeapi.co

## Structure
### Login Activity
User can login to the app through Firebase Auth with Gmail or Facebook
### Main Activity
Implementes Bottom Navigation so User can move between two tabs: Regions and Teams.
### Regions Fragment
Displays a list of Pokemon Regions. User can click on a Region to go on to next screen.
### Pokedex Fragment
Displays a list of Pokedex available in the previously selected Region. User can click on a Pokedex to go on to next screen.
### Pokemons Fragment
Displays a list of Pokemon available in the previously selected Region and Pokedex. User must add at 
least 3 Pokemons and a maximum of 6 to create its Team and then click on the button on top that is only enabled when 3 Pokemons were added.
A Toast is displayed warning the User that no more Pokemons can be added to the Team when the User has already added 6.
### Create Team Fragment
User can create their Team on this screen with the previously selected Pokemons. User must write the Team's name, number and type before
clicking on the button that allows them to create the Team. After creating the Team, the User is taken to the Teams tab.
The Team's data is saved on Firebase Realtime Database.
### Teams Fragment
Displays a list of created Teams with the Team's information and its Pokemons. Data is retrieved from the Firebase Realtime Database. 
User can delete these Teams or modify them. 
If User chooses to modify a Team, they are taken to the Teams Detail Fragment.
#### Teams Detail Fragment
Retrieves data from the Firebase Database to get the previously selected Team's data in order for it to be modified. User can afterwards
save the modified data and the fragment will close and redirect back to the Teams Fragment with the modifications done.


## Libraries Used
 <ul>
 <li> <a href="https://firebase.google.com/docs/database/">Firebase Realtime Database</a> </li>
 <li> <a href="https://firebase.google.com/docs/auth/">Firebase Auth</a> </li>
 <li> <a href="https://dagger.dev/hilt/">Dagger Hilt</a> </li>
<li> <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a> </li>
 <li> <a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a> </li>
 <li> <a href="http://square.github.io/retrofit/">Retrofit</a> </li>
 <li> <a href="http://square.github.io/picasso/">Picasso</a> </li>
 <li> <a href="http://square.github.io/okhttp/">OkHttp3</a> </li>
 </ul>
