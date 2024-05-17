

# NewsApp-Compose

NewsApp-Compose is a very basic app, built with [Jetpack Compose][compose]

### Overview
This project is implemented in a multi-modular app architecture, separating the UI, business logic, and data.   

Project uses:
- Retrofit
- Room
- Paging3
- Navigation
- Dark mode
- Hilt
- Material Design
- Coil

### Using News Api

This app uses the API from  [https://newsapi.org/](https://newsapi.org/).

To use News API you will need to get your API key https://newsapi.org/register 
and put it into project's Gradle local.properties:
``NEWS_API_KEY=<Your api key here>``


### Modules
| Name                               | Responsibilities                                                                                                         |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| app                                | Brings everything together required for the app to function correctly. This includes UI scaffolding and navigation.      |
| core:common                        | Common classes and functions shared between modules. It also contains common resources like drawables, strings.xml, etc. |
| core:domain                        | Contains the use cases for business logic.                                                                               |
| core:data                          | contains the data layer classes that manage the application's data handling and storage.                                 |
| core:designsystem                  | Defines app theme, colors, typography, and base UI components used in the app.                                           |
| feature:home, feature:details, ... | Functionality associated with specific features.                                                                         |

### Screenshots

<table>
  <tr>
    <td>

![screenshot_home](https://github.com/olehshyker/NewsApp-Compose/assets/83090160/dd76346c-717b-4ff8-9774-70f5f44964dd)

</td>

<td>

![screenshot_detail](https://github.com/olehshyker/NewsApp-Compose/assets/83090160/3773537b-e24f-462a-9d1d-4fd07ae182c2)

</td>

</tr>

</table>

> **Todo list**

1. Implement news search
2. Add bookmarks
3. Add video news playing support

[compose]: https://developer.android.com/jetpack/compose