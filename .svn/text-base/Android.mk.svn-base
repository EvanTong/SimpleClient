LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := user eng development


LOCAL_SRC_FILES := $(call all-java-files-under, src)
#LOCAL_SRC_FILES += $(call all-java-files-under, unit_test/src)

LOCAL_PACKAGE_NAME := MyClient
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)
#Unit test
#include $(call all-makefiles-under,$(LOCAL_PATH))
