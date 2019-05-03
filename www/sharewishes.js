/*global cordova, module*/

module.exports = {
    open: function (name,mobile,email,msg,successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Sharewishes", "open", [name,mobile,email,msg]);
    }
};
