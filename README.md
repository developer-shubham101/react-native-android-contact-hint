
# react-native-android-contact-hint

## Getting started

`$ npm install react-native-android-contact-hint --save`

### Mostly automatic installation

`$ react-native link react-native-android-contact-hint`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import in.newdevpoint.hint.RNAndroidContactHintPackage;` to the imports at the top of the file
  - Add `new RNAndroidContactHintPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-contact-hint'
  	project(':react-native-android-contact-hint').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-contact-hint/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-contact-hint')
  	```


## Usage
```javascript
import AndroidContactHint from 'react-native-android-contact-hint';
...
componentDidMount() {
    //Use this code for when you want to get contact hint 
    AndroidContactHint.fetchContactNo((response) => {
    	console.log("AndroidContactHint Response", response);
    });
}
```
 # Native Link
 https://developers.google.com/identity/sms-retriever/overview