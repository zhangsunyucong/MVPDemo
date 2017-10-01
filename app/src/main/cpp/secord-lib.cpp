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