import react_native_1, { NativeModules } from 'react-native';

const { RNAndroidContactHint } = NativeModules;

var AndroidContactHint = null;
if (react_native_1.Platform.OS === "android") {
	AndroidContactHint = {
		showToast: RNAndroidContactHint.show,
		fetchContactNo: RNAndroidContactHint.fetchContactNo
	};
} else {
	AndroidContactHint = {
		showToast: () => { console.info("AndroidContactHint Libreary is only for android") },
		fetchContactNo: () => { console.info("AndroidContactHint Libreary is only for android") }
	};
}

export default AndroidContactHint;
