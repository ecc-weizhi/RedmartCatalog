# RedmartCatalog
Android app that displays products from RedMart app. The purpose of this project is to practice and demonstrate my ability in Android development.

## The following ability were demonstrated:
- Fetching of data from an API url
- Performing network operation in non-ui thread
- Structuring Activity and Fragment such that each fragment is a modular and resuable component
- Follow MVP pattern for UI
- Working with RecyclerView
- Implementing a load as you scroll adapter for RecyclerView
- Junit test
- Ability to imitate RedMart UI
- Animations

## Possible improvement
The following features are possible improvement that I have considered but ultimately decided not to implement.

### Network change receiver
Using BroadcastReceiver to detect network change so that we can display an empty state when there are no network connectivity.

### Persist cart
Currently, cart information are not persisted. This means that if Android decides to kill the app, cart information will be lost. We can prevent this by persisting cart information. The simplest way to accomplish this will probably to save it in SharedPreference.

### Local cache
Product information are not stored locally on device. We can persist product information on to disk so that we can reduce network data consumption. We can probably either use a simple DiskLRUCache or a more sophisticated Sqlite database for persistence. The data will need to have a time-to-live value to prevent stale data.
