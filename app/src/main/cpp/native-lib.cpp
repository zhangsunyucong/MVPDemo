#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getBuglyAppId(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "43462d0d28";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getAppId(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "9943462d0d28";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getAppResScrect(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "2722ffbbd2584f10";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getRsaServerPublicKey(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "MIGfMA0GCSqGSIb3DQEQAB";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getRsaClientPrivateKey(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "MIICXgIBAAKBgQCQacK3XhQR3wckL4wQD0K9FysCk";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getRsaClientPublickey(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "43462d0d28";
    return env->NewStringUTF(appId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_zhangsunyucong_chanxa_testproject_manager_JNIManager_getChatScrect(
        JNIEnv* env,
        jobject /* this */) {

    std::string appId = "2769ffaaa2584d01";
    return env->NewStringUTF(appId.c_str());
}